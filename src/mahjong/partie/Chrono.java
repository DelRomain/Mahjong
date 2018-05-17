
package mahjong.partie;

import java.awt.Color;
import java.util.TimerTask;
import mahjong.GUI.InterfaceDeJeu;

public class Chrono extends TimerTask{

    private final InterfaceDeJeu interfaceDeJeu;
    private int temp;
    private final int TEMP_COUP;
    private final int TEMP_COUP_RETRAIT_POINT;
    private final int TEMP_COUP_FIN_CHRONO;
    
    public Chrono(InterfaceDeJeu interfaceDeJeu, int tempCoup) {
        this.interfaceDeJeu = interfaceDeJeu;
        this.TEMP_COUP = tempCoup*100;
        this.temp = TEMP_COUP;
        this.interfaceDeJeu.setTailleChronometre(TEMP_COUP);
        this.TEMP_COUP_RETRAIT_POINT = (int) (0.75*TEMP_COUP);
        this.TEMP_COUP_FIN_CHRONO = (int) (0.25*TEMP_COUP);
    }

    @Override
    public void run() 
    {
        Color color;
        
        temp -=1;
        //System.out.println(temp);
        /*/
        if(temp <0)
            reset();
        //*/
        if(temp>TEMP_COUP_RETRAIT_POINT)
            color = Color.GREEN;
        else if(temp>TEMP_COUP_FIN_CHRONO)
            color = Color.ORANGE;
        else
            color = Color.RED;
        interfaceDeJeu.changerBarTemps(temp,color);
    }

    public void reset() 
    {
        temp = TEMP_COUP;
    }
    
}
