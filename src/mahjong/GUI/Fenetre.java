package mahjong.GUI;

import javax.swing.JFrame;

public class Fenetre extends JFrame
{

    public Fenetre(PlateauGUI gui){
        super("Mahjong");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.add(gui);
        this.setSize(455, 598);
        this.setVisible(true);
        gui.repaint();
    }
    
}
