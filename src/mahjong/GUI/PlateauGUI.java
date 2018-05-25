package mahjong.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import mahjong.FamilleDeTuile;
import mahjong.PathFinder.CaseRecherchee;
import mahjong.Plateau;
import mahjong.Tuile;

public class PlateauGUI extends JPanel implements MouseListener, MouseMotionListener {

    private BufferedImage[] images;
    public static final int LARGEUR_TUILE = 35;
    public static int HAUTEUR_TUILE = 46;
    private Plateau plateau;
    private boolean estBloquee;
    private Fenetre fenetre;

    public PlateauGUI() {
        super();
        estBloquee = false;
        images = new BufferedImage[42];
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

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (plateau != null) {
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
            CaseRecherchee caseRechercheChemin = plateau.getCheminTuile();
            if(caseRechercheChemin != null)
            {
                g.setColor(Color.red);
                CaseRecherchee parent = caseRechercheChemin.getParent();
                while(parent != null)
                {
                    g.drawLine(
                            caseRechercheChemin.getY()*LARGEUR_TUILE+LARGEUR_TUILE/2,
                            caseRechercheChemin.getX()*HAUTEUR_TUILE+HAUTEUR_TUILE/2,
                            parent.getY()*LARGEUR_TUILE+LARGEUR_TUILE/2,
                            parent.getX()*HAUTEUR_TUILE+HAUTEUR_TUILE/2);
                    
                    caseRechercheChemin = parent;
                    parent = caseRechercheChemin.getParent();
                }
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
        if(!estBloquee)
        {
            int ligneTuile;
            int colonneTuile;
            final int curseurX = e.getX();
            final int curseurY = e.getY();

            colonneTuile = curseurX / LARGEUR_TUILE;
            ligneTuile = curseurY / HAUTEUR_TUILE;
            this.plateau.jouer(ligneTuile, colonneTuile);

            if (this.plateau.partieGagnee()) {
                JOptionPane.showMessageDialog(null, "Vous avez gagné !", "Victoire", JOptionPane.INFORMATION_MESSAGE);
                fenetre.afficherMenuPrincipal();
            }
            this.repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    public void bloquerPlateau(boolean estBloquee)
    {
        this.estBloquee = estBloquee;
    } 

    @Override
    public void mouseMoved(MouseEvent e){   
    }

    public void setFenetre(Fenetre fenetre) {
        this.fenetre = fenetre;
    }
    
    
}

