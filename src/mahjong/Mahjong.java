package mahjong;

import javax.swing.SwingUtilities;
import mahjong.GUI.Fenetre;

public class Mahjong {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Fenetre();
            }
        });
    }
}
