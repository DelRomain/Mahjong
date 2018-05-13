package mahjong.partie;

import java.util.Timer;
import mahjong.GUI.InterfaceDeJeu;
import mahjong.Plateau;
import mahjong.Type_Plateau.PlateauTuileTombante;


public class Partie 
{
    private Plateau plateau;
    private final Timer timer;

    public Partie(InterfaceDeJeu interfaceDeJeu) {
        timer = new Timer();
        plateau = new Plateau();
        plateau.genererNouveauPlateau(0, new PlateauTuileTombante());
        interfaceDeJeu.setPlateau(plateau);
        timer.scheduleAtFixedRate(new Chrono(interfaceDeJeu), 0, 1000);
    }
    
    
}
