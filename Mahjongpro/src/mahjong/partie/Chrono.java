
package mahjong.partie;

import java.awt.Color;
import java.util.TimerTask;
import mahjong.GUI.InterfaceDeJeu;

public class Chrono extends TimerTask{

    private int temp;
    private int tempsTotalDeJeu;
    private final int TEMP_COUP;
    private final int TEMP_COUP_RETRAIT_POINT;
    private final int TEMP_COUP_FIN_CHRONO;
    private final Partie partie;
    
    public Chrono(Partie partie, int tempCoup) {
        this.tempsTotalDeJeu = 4000;
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
        int tempTotalSeconde = tempsTotalDeJeu/100;
        int nombreDeSeconde = tempTotalSeconde%60;
        int nombreDeMinute = (tempTotalSeconde-nombreDeSeconde)/60;
        return nombreDeMinute+":"+nombreDeSeconde;
    }
}
