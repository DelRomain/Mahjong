package mahjong.partie;

import java.util.Timer;
import mahjong.GUI.InterfaceDeJeu;
import mahjong.Plateau;
import mahjong.Type_Plateau.PlateauTuileTombante;
import mahjong.joueur.Joueur;


public class Partie 
{
    private final Plateau plateau;
    private final Joueur joueur;
    private final Chrono chrono;
    private final InterfaceDeJeu interfaceDeJeu;
    private final Timer timer;

    public Partie(InterfaceDeJeu interfaceDeJeu) {
        
        timer = new Timer();
        joueur = new Joueur();
        plateau = new Plateau();
        
        this.interfaceDeJeu = interfaceDeJeu;
        
        chrono = new Chrono(this, 20);
        plateau.genererNouveauPlateau(0, new PlateauTuileTombante());
        plateau.setPartie(this);
        interfaceDeJeu.setPlateau(plateau);
        
        timer.scheduleAtFixedRate(chrono, 0, 10);
    }

    public void resetChrono() 
    {
        chrono.reset();
    }

    public InterfaceDeJeu getInterfaceDeJeu()
    {
        return interfaceDeJeu;
    }
    
    
}
