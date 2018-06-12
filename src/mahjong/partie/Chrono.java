
package mahjong.partie;

import java.awt.Color;
import java.util.TimerTask;
import javax.swing.event.EventListenerList;
import mahjong.Listener.ChronoListener;

public class Chrono extends TimerTask
{
    private long temp;
    private long tempsTotalDeJeu;
    private long tempsAffichageChemin;
    private final int TEMP_COUP;
    private final int TEMP_COUP_RETRAIT_POINT;
    private final int TEMP_COUP_FIN_CHRONO;
    private final Partie partie;
    
    private final EventListenerList listeners = new EventListenerList();
    
    public Chrono(Partie partie, int tempCoup, long tempCoupRestant, long tempTotal) {
       this(partie,tempCoup);
       this.tempsTotalDeJeu = tempTotal;
       this.temp = tempCoupRestant;
       this.tempsAffichageChemin = 21;
    }
    
    
    public Chrono(Partie partie, int tempCoup) {
        this.tempsTotalDeJeu = 0;
        this.TEMP_COUP = tempCoup*100;
        this.temp = TEMP_COUP;
        
        this.TEMP_COUP_RETRAIT_POINT = (int) (0.75*TEMP_COUP);
        this.TEMP_COUP_FIN_CHRONO = (int) (0.25*TEMP_COUP);
        this.partie = partie;
        this.tempsAffichageChemin = 0;
    }

    public Chrono(Chrono chrono) 
    {
       this.partie = chrono.partie;
       this.tempsTotalDeJeu = chrono.tempsTotalDeJeu;
       this.temp = chrono.temp;
       this.tempsAffichageChemin = chrono.tempsAffichageChemin;
       this.TEMP_COUP = chrono.TEMP_COUP;
       this.TEMP_COUP_FIN_CHRONO = chrono.TEMP_COUP_FIN_CHRONO;
       this.TEMP_COUP_RETRAIT_POINT = chrono.TEMP_COUP_RETRAIT_POINT;
    }

    @Override
    public void run() 
    {
        Color color; 
        this.temp --;
        this.tempsTotalDeJeu++;
        
        if(tempsAffichageChemin <= 20)
            this.tempsAffichageChemin++;
        
        if(tempsAffichageChemin == 20)
        {
            fireEffacerCheminLiaisonTuiles();
        }
        
        fireMettreAJourChronometreDeJeu(tempsTotalDeJeu);
        
        if(temp>TEMP_COUP_RETRAIT_POINT)
            color = Color.GREEN;
        else if(temp>TEMP_COUP_FIN_CHRONO)
            color = Color.ORANGE;
        else
            color = Color.RED;
        
        fireMettreAJourChronometreDeCoup(temp,color);
    }

    public void resetChronoCoup() 
    {
        this.temp = this.TEMP_COUP;
    }
    
    public void resetChronoAffichageChemin() 
    {
        this.tempsAffichageChemin = 0;
    }
    
    public static String getTempsFormate(long temps)
    {
        long tempTotalSeconde = temps/100;
        long nombreSeconde = tempTotalSeconde%60;
        long nombreMinute = ((tempTotalSeconde-nombreSeconde)/60)%60;
        long nombreHeure = (tempTotalSeconde - nombreMinute*60 - nombreSeconde)/3600;
        
        return String.format("%02d", nombreHeure)+":"+String.format("%02d", nombreMinute)+":"+String.format("%02d", nombreSeconde);
    }
    
    public int getScoreTemp()
    {
        int pointGagnee = 0;
        if(temp>TEMP_COUP_RETRAIT_POINT)
            pointGagnee = 20;
        else 
            pointGagnee = (int)temp/100;      
        return pointGagnee;
    }

    public long getTemp() {
        return temp;
    }

    public long getTempsTotalDeJeu() {
        return tempsTotalDeJeu;
    }

    public long getTempsAffichage() {
        return tempsAffichageChemin;
    }
    
    public void fireEffacerCheminLiaisonTuiles()
    {
        for(ChronoListener listener : listeners.getListeners(ChronoListener.class)) {
            listener.effacerCheminLiaisonTuiles();
        }
    }
    
    public void fireMettreAJourChronometreDeCoup(long temps, Color color)
    {
        for(ChronoListener listener : listeners.getListeners(ChronoListener.class)) {
            listener.mettreAJourChronometreDeCoup(temps, color);
        }
    }
    
    public void fireMettreAJourChronometreDeJeu(long temps)
    {
        for(ChronoListener listener : listeners.getListeners(ChronoListener.class)) {
            listener.mettreAJourChronometreDeJeu(temps);
        }
    }

    public void addChronoListener(ChronoListener listener) {
        listeners.add(ChronoListener.class, listener);
    }
    
    public void removeChronoListener(ChronoListener listener) {
        listeners.remove(ChronoListener.class, listener);
    }
}
