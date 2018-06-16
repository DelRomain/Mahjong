package mahjong.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;
import mahjong.FamilleDeTuile;
import mahjong.Listener.PlateauListener;
import mahjong.PathFinder.CaseRecherchee;
import mahjong.Plateau;
import mahjong.Tuile;
import mahjong.coup.CoupRetirerTuile;

/**
 * Panel servant à afficher le plateau et récuperer les actions du joueur
 * vis-à-vis de celui-ci.
 */
public class PlateauGUI extends JPanel implements MouseListener {

    private BufferedImage imagePause;
    private BufferedImage[] images;
    
    public static final int LARGEUR_TUILE = 35;
    public static final int HAUTEUR_TUILE = 46;
    
    private Plateau plateau;
    private CoupRetirerTuile dernierCoup;
    private boolean enPause;

    private final EventListenerList listeners = new EventListenerList();

    public PlateauGUI() {
        super();
        images = new BufferedImage[42];
        dernierCoup = null;
        enPause = false;

        int i = 0;
        for (FamilleDeTuile famille : FamilleDeTuile.values()) {
            for (String nom : famille.getNomFichier()) {
                try {
                    images[i] = ImageIO.read(new File("tuiles/" + nom));
                } catch (IOException ex) {
                    Logger.getLogger(PlateauGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                i++;
            }
        }
        try {
            imagePause = ImageIO.read(new File("tuiles/pause.png"));
        } catch (IOException ex) {
            Logger.getLogger(PlateauGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        i++;
        this.addMouseListener(this);
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (plateau != null) {
            if (!enPause) {
                Tuile tuile;
                for (int indexLigne = 0; indexLigne < Plateau.NOMBRE_LIGNE; indexLigne++) {
                    for (int indexColonne = 0; indexColonne < Plateau.NOMBRE_COLONNE; indexColonne++) {

                        tuile = this.plateau.getTuile(indexLigne, indexColonne);
                        if (tuile != null) {
                            if (this.plateau.getTuilesSelectionnee() == tuile) {
                                BufferedImageOp op = new RescaleOp(new float[]{0.8f, 1.2f, 0.8f, 1.0f}, new float[4], null);
                                g.drawImage(
                                        op.filter(this.images[tuile.getImageID()], null),
                                        indexColonne * LARGEUR_TUILE,
                                        indexLigne * HAUTEUR_TUILE,
                                        LARGEUR_TUILE, HAUTEUR_TUILE, this);
                            } else if (plateau.getHint() != null
                                    && (plateau.getHint().getTuiles()[0] == tuile
                                    || plateau.getHint().getTuiles()[1] == tuile)) {
                                BufferedImageOp op = new RescaleOp(new float[]{1.2f, 1.2f, 0.8f, 1.0f}, new float[4], null);
                                g.drawImage(
                                        op.filter(this.images[tuile.getImageID()], null),
                                        indexColonne * LARGEUR_TUILE,
                                        indexLigne * HAUTEUR_TUILE,
                                        LARGEUR_TUILE, HAUTEUR_TUILE, this);
                            } else {
                                g.drawImage(
                                        this.images[tuile.getImageID()],
                                        indexColonne * LARGEUR_TUILE,
                                        indexLigne * HAUTEUR_TUILE,
                                        LARGEUR_TUILE, HAUTEUR_TUILE, this);
                            }
                        }
                    }
                }

                CaseRecherchee caseRechercheChemin = plateau.getCheminLiaisonTuiles();
                if (caseRechercheChemin != null && dernierCoup != null) {
                    g.setColor(Color.red);
                    CaseRecherchee parent = caseRechercheChemin.getParent();
                    while (parent != null) {
                        g.drawLine(
                                caseRechercheChemin.getColonne() * LARGEUR_TUILE + LARGEUR_TUILE / 2,
                                caseRechercheChemin.getLigne() * HAUTEUR_TUILE + HAUTEUR_TUILE / 2,
                                parent.getColonne() * LARGEUR_TUILE + LARGEUR_TUILE / 2,
                                parent.getLigne() * HAUTEUR_TUILE + HAUTEUR_TUILE / 2);

                        caseRechercheChemin = parent;
                        parent = caseRechercheChemin.getParent();
                    }
                }
            } else {
                g.drawImage(imagePause, 0, 0, this);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!enPause) {
            if (this.dernierCoup != null) {
                effacerCheminLiaisonTuiles();
            }
            if (e.getButton() == MouseEvent.BUTTON1) {
                int ligneTuile;
                int colonneTuile;
                final int curseurX = e.getX();
                final int curseurY = e.getY();

                colonneTuile = curseurX / PlateauGUI.LARGEUR_TUILE;
                ligneTuile = curseurY / PlateauGUI.HAUTEUR_TUILE;
                this.dernierCoup = this.plateau.creeCoupRetraitTuile(ligneTuile, colonneTuile);
                if (dernierCoup != null) {
                    fireGenererCoup(dernierCoup);
                }
                this.repaint();
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                this.plateau.deselectionnerTuile();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void setPause(boolean enPause) {
        this.enPause = enPause;
        this.repaint();
    }

    public void effacerCheminLiaisonTuiles() {
        if (dernierCoup != null) {
            this.plateau.jouerCoupRetrait(this.dernierCoup, true);
            fireApplicationCoup(dernierCoup);
        }
        this.dernierCoup = null;
        this.repaint();
    }

    public void addPlateauListener(PlateauListener listener) {
        listeners.add(PlateauListener.class, listener);
    }

    public void removePlateauListener(PlateauListener listener) {
        listeners.remove(PlateauListener.class, listener);
    }

    public void fireGenererCoup(CoupRetirerTuile coup) {
        for (PlateauListener listener : listeners.getListeners(PlateauListener.class)) {
            listener.genererCoup(coup);
        }
    }

    private void fireApplicationCoup(CoupRetirerTuile coup) {
        for (PlateauListener listener : listeners.getListeners(PlateauListener.class)) {
            listener.applicationCoup(coup);
        }
    }
}
