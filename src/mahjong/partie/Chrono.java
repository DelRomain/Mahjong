
package mahjong.partie;

import java.awt.Color;
import java.util.TimerTask;
import mahjong.GUI.InterfaceDeJeu;

public class Chrono extends TimerTask{

    private InterfaceDeJeu interfaceDeJeu;
    private int temp;
    
    public Chrono(InterfaceDeJeu interfaceDeJeu) {
        this.interfaceDeJeu = interfaceDeJeu;
        temp = 100;
    }

    
    
    @Override
    public void run() 
    {
        Color color;
        temp -=5;
        if(temp <0)
            temp = 100;
        if(temp>75)
            color = Color.GREEN;
        else if(temp>25)
            color = Color.ORANGE;
        else
            color = Color.RED;
        interfaceDeJeu.changerBarTemps(temp,color);
    }
    
}
