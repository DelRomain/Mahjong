package mahjong.partie;

import java.util.Timer;
import mahjong.GUI.InterfaceDeJeu;
import mahjong.Plateau;
import mahjong.Type_Plateau.PlateauTuileTombante;
import mahjong.joueur.Joueur;


public class Partie 
{
    private boolean enPause;
    private final Plateau plateau;
    private final Joueur joueur;
    private Chrono chrono;
    private final InterfaceDeJeu interfaceDeJeu;
    private final Timer timer;
    private int score = 0;
    private int tempCoupChronoPause, tempTotalChronoPause;
    private int tempCoup;

    public Partie(InterfaceDeJeu interfaceDeJeu) {
        
        timer = new Timer();
        joueur = new Joueur();
        plateau = new Plateau();
        tempCoup = 20;
        
        this.interfaceDeJeu = interfaceDeJeu;
        
        chrono = new Chrono(this, tempCoup);
        plateau.genererNouveauPlateau(0, new PlateauTuileTombante());
        plateau.setPartie(this);
        interfaceDeJeu.setPartie(this);
        
        timer.scheduleAtFixedRate(chrono, 0, 10);
    }

    public void resetChrono() 
    {
        score +=chrono.getScoreTemp();
        interfaceDeJeu.setScore(score);
        chrono.reset();
    }

    public InterfaceDeJeu getInterfaceDeJeu()
    {
        return interfaceDeJeu;
    }
    
    public Plateau getPlateau()
    {
        return plateau;
    }

    public boolean estEnPaused() 
    {
        return enPause;
    }

    public void changePause() {
        this.enPause = !enPause;
        if(enPause)
        {
            tempCoupChronoPause = chrono.getTemp();
            tempTotalChronoPause = (int) chrono.getTempsTotalDeJeu();
            chrono.cancel();
            interfaceDeJeu.bloquerPlateau(true);
        }
        else
        {
            chrono = new Chrono(this, tempCoup, tempCoupChronoPause, tempTotalChronoPause);
            timer.scheduleAtFixedRate(chrono, 0, 10);
            interfaceDeJeu.bloquerPlateau(false);
        }
    }

    public void melangerPlateau() 
    {
        plateau.melangerPlateau();
    }
    
    
    
    
}
