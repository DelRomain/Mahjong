package mahjong.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import mahjong.Plateau;
import mahjong.joueur.Joueur;

public class InterfaceDeJeu extends JPanel{

    private Plateau plateau;
    private final PlateauGUI plateauGUI;
    private Joueur Joueur;
    
    private JLabel labelNomJoeur;
    private JLabel labelScoreJoeur;
    private JProgressBar temps;

    public InterfaceDeJeu() {
        super(new BorderLayout());
        plateauGUI = new PlateauGUI();
        this.init();
        temps.setValue(50);
        temps.setForeground(Color.orange);
        
    }

    private void init() 
    {
        this.add(plateauGUI,BorderLayout.CENTER);
        
        JPanel panelScore = new JPanel(new GridLayout(0, 1));
        panelScore.setBackground(Color.LIGHT_GRAY);
        labelNomJoeur = new JLabel("Test");
        panelScore.add(labelNomJoeur);
        labelScoreJoeur = new JLabel("Score : xxxx");
        panelScore.add(labelScoreJoeur);

        temps = new JProgressBar(JProgressBar.VERTICAL);
        
        JPanel panelContainer = new JPanel(new BorderLayout());
        panelContainer.add(panelScore,BorderLayout.CENTER);
        panelContainer.add(temps,BorderLayout.EAST);
        
        this.add(panelContainer, BorderLayout.EAST);
    }

    public void setPlateau(Plateau plateau) {
        plateauGUI.setPlateau(plateau);
    }

    public void changerBarTemps(int temp, Color color) {
        temps.setValue(temp);
        temps.setForeground(color);
    }
    
    
}
