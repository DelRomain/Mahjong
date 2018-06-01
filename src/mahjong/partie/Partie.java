package mahjong.partie;

import java.util.Timer;
import javax.swing.JOptionPane;
import mahjong.GUI.InterfaceDeJeu;
import mahjong.Plateau;
import mahjong.TypePlateau;
import mahjong.Type_Plateau.PlateauTuileTombante;
import static mahjong.partie.Chrono.getTempsFormate;


public class Partie 
{
    private boolean enPause;
    private final Plateau plateau;
    private transient Chrono chrono;
    private transient final InterfaceDeJeu interfaceDeJeu;
    private transient final Timer timer;
    private int score = 0;
    private long tempCoupChronoPause, tempTotalChronoPause, tempsChronoAffichagePause;
    private int tempCoup;
    
    public Partie(InterfaceDeJeu interfaceDeJeu) {
        this(interfaceDeJeu,(long)0, new PlateauTuileTombante());
    }

    public Partie(InterfaceDeJeu interfaceDeJeu, long seed, TypePlateau typePlateau) {
        timer = new Timer();
        plateau = new Plateau();
        tempCoup = 20;
        
        this.interfaceDeJeu = interfaceDeJeu;
        
        chrono = new Chrono(this, tempCoup);
        plateau.genererNouveauPlateau(seed, typePlateau);
        plateau.setPartie(this);
        interfaceDeJeu.setPartie(this);
        
        timer.scheduleAtFixedRate(chrono, 0, 10);
    }

    public int resetChrono() 
    {
        int scoreAjouter = chrono.getScoreTemp();
        score += scoreAjouter;
        interfaceDeJeu.debloquerBoutonRetourCoup();
        interfaceDeJeu.setScore(score);
        chrono.resetChronoCoup();
        chrono.resetChronoAffichageChemin();
        verrifierVictoire();
        return scoreAjouter;
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
            tempTotalChronoPause = chrono.getTempsTotalDeJeu();
            tempsChronoAffichagePause = chrono.getTempsAffichage();
            chrono.cancel();
            interfaceDeJeu.bloquerPlateau(true);
        }
        else
        {
            chrono = new Chrono(this, tempCoup, tempCoupChronoPause, tempTotalChronoPause, tempsChronoAffichagePause);
            timer.scheduleAtFixedRate(chrono, 0, 10);
            interfaceDeJeu.bloquerPlateau(false);
        }
    }

    public void melangerPlateau() 
    {
        plateau.melangerPlateau();
        interfaceDeJeu.repaint();
    }

    public void retourCoup() 
    {
        chrono.resetChronoCoup();
        score -=plateau.retourCoup()+10;
        interfaceDeJeu.setScore(score);
        interfaceDeJeu.repaint();    
        if(plateau.getCoups().isEmpty())
            interfaceDeJeu.bloquerBoutonRetourCoup();
    }

    public int getScore() {
        return score;
    }

    public long getTempTotalChronoPause() {
        return tempTotalChronoPause;
    }
    
    public void verrifierVictoire()
    {
        if(plateau.partieGagnee())
        {
            tempCoupChronoPause = chrono.getTemp();
            tempTotalChronoPause = chrono.getTempsTotalDeJeu();
            tempsChronoAffichagePause = chrono.getTempsAffichage();
            chrono.cancel();
            interfaceDeJeu.bloquerPlateau(true);
            JOptionPane.showMessageDialog(null, "Vous avez gagné !", "Victoire", JOptionPane.INFORMATION_MESSAGE);
            interfaceDeJeu.victoire();
            interfaceDeJeu.afficherMenuPrincipal();
        }
    }
}
