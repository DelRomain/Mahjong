package mahjong.partie;

import java.util.Timer;
import mahjong.GUI.InterfaceDeJeu;
import mahjong.Plateau;
import mahjong.Type_Plateau.PlateauTuileTombante;


public class Partie 
{
    private final Plateau plateau;
    private final Chrono chrono;
    private final Timer timer;

    public Partie(InterfaceDeJeu interfaceDeJeu) {
        
        timer = new Timer();
        plateau = new Plateau();
        chrono = new Chrono(interfaceDeJeu, 20);
        plateau.genererNouveauPlateau(0, new PlateauTuileTombante(), chrono);
        interfaceDeJeu.setPlateau(plateau);
        
        timer.scheduleAtFixedRate(chrono, 0, 10);
    }
    
    
}
