package mahjong.GUI;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import mahjong.FamilleDeTuile;
import mahjong.Plateau;
import mahjong.Tuile;

public class PlateauGUI extends JPanel implements MouseListener {

    private BufferedImage[] images;
    private final int LARGEUR_TUILE;
    private final int HAUTEUR_TUILE;
    private final Plateau plateau;

    public PlateauGUI(Plateau plateau) {
        super();
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
        this.LARGEUR_TUILE = 35;
        this.HAUTEUR_TUILE = 46;
        this.plateau = plateau;
        this.addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Tuile tuile;
        for (int indexLigne = 0; indexLigne < 12; indexLigne++) {
            for (int indexColonne = 0; indexColonne < 12; indexColonne++) {
                
                tuile = this.plateau.getTuile(indexLigne, indexColonne);
                if (tuile != null) {
                    if (this.plateau.getTuilesSelectionnee() == tuile) {
                        BufferedImageOp op = new RescaleOp(new float[]{0.8f, 1.2f, 0.8f, 1.0f}, new float[4], null);
                        g.drawImage(
                                op.filter(this.images[tuile.getID()], null),
                                indexColonne * LARGEUR_TUILE,
                                indexLigne * HAUTEUR_TUILE,
                                LARGEUR_TUILE, HAUTEUR_TUILE, this);
                    } else {
                        g.drawImage(
                                this.images[tuile.getID()],
                                indexColonne * LARGEUR_TUILE,
                                indexLigne * HAUTEUR_TUILE,
                                LARGEUR_TUILE, HAUTEUR_TUILE, this);
                    }
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
        int ligneTuile;
        int colonneTuile;
        final int curseurX = e.getX();
        final int curseurY = e.getY();

        colonneTuile = curseurX / LARGEUR_TUILE;
        ligneTuile = curseurY / HAUTEUR_TUILE;
        this.plateau.jouer(ligneTuile, colonneTuile);

        if (this.plateau.partieGagnee()) {
            JOptionPane.showMessageDialog(null, "Vous avez gagné !", "Victoire", JOptionPane.INFORMATION_MESSAGE);
        }
        this.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
