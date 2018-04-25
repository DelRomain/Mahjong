package mahjong;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import mahjong.GUI.Fenetre;
import mahjong.GUI.PlateauGUI;
import mahjong.Type_Plateau.PlateauTuileTombante;

public class Mahjong {

    public static void main(String[] args) {
        Plateau plateau = new Plateau();
        //* TEST DU PLATEAU
        plateau.genererNouveauPlateau(0, new PlateauTuileTombante());
        plateau.afficherTerrainSurConsole();
        System.out.println("La partie est-elle gagnée? " + plateau.partieGagnee());
        //*/
        
        //* TEST DE FAMILLE DE TUILE
        System.out.println("Identifiant : "+FamilleDeTuile.getIDUniqueTuile("F3"));
        //*/
        
        PlateauGUI gui = new PlateauGUI(plateau);
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Fenetre(gui);
            }
        });

    }

}
