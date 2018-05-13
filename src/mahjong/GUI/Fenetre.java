package mahjong.GUI;

import java.awt.Dimension;
import javax.swing.JFrame;
import mahjong.Plateau;
import mahjong.partie.Partie;

public class Fenetre extends JFrame
{
    private final InterfaceDeJeu interfaceDeJeu;
    private final Partie partie;
    
    public Fenetre(){
        super("Mahjong");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        interfaceDeJeu = new InterfaceDeJeu();
        partie = new Partie(interfaceDeJeu);
        this.add(interfaceDeJeu);
        this.setMinimumSize(new Dimension(PlateauGUI.LARGEUR_TUILE*18,PlateauGUI.HAUTEUR_TUILE*14));
        this.setVisible(true);
    }
    
}
