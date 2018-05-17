
package mahjong.partie;

import java.awt.Color;
import java.util.TimerTask;

public class Chrono extends TimerTask{

    private int temp;
    private long tempsTotalDeJeu;
    private final int TEMP_COUP;
    private final int TEMP_COUP_RETRAIT_POINT;
    private final int TEMP_COUP_FIN_CHRONO;
    private final Partie partie;
    
    public Chrono(Partie partie, int tempCoup, int tempCoupRestant, int tempTotal) {
       this(partie,tempCoup);
       this.tempsTotalDeJeu = tempTotal;
       this.temp = tempCoupRestant;
    }
    
    
    public Chrono(Partie partie, int tempCoup) {
        this.tempsTotalDeJeu = 0;
        this.TEMP_COUP = tempCoup*100;
        this.temp = TEMP_COUP;
        
        this.TEMP_COUP_RETRAIT_POINT = (int) (0.75*TEMP_COUP);
        this.TEMP_COUP_FIN_CHRONO = (int) (0.25*TEMP_COUP);
        this.partie = partie;
        this.partie.getInterfaceDeJeu().setTailleChronometre(TEMP_COUP);
    }

    @Override
    public void run() 
    {
        Color color; 
        this.temp --;
        this.tempsTotalDeJeu++;
        this.partie.getInterfaceDeJeu().updateTempJeu(getTempPartie());
        
        if(temp>TEMP_COUP_RETRAIT_POINT)
            color = Color.GREEN;
        else if(temp>TEMP_COUP_FIN_CHRONO)
            color = Color.ORANGE;
        else
            color = Color.RED;
        this.partie.getInterfaceDeJeu().changerBarTemps(temp,color);
     
    }

    public void reset() 
    {
        temp = TEMP_COUP;
    }
    
    private String getTempPartie()
    {
        long tempTotalSeconde = tempsTotalDeJeu/100;
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
            pointGagnee = temp/100;      
        return pointGagnee;
    }

    public int getTemp() {
        return temp;
    }

    public long getTempsTotalDeJeu() {
        return tempsTotalDeJeu;
    }
}
