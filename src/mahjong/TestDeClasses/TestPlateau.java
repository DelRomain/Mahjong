/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mahjong.TestDeClasses;

import java.awt.Dimension;
import javax.swing.JFrame;
import mahjong.GUI.AfficheurDePlateau;
import mahjong.GestionPlateau.Plateau;
import mahjong.TypeDePlateau.TypePlateau;

/**
 *
 * @author aschneid
 */
public class TestPlateau 
{
    public static void main(String[] args) {
        JFrame jframe = new JFrame("test plateau");
        
        
        AfficheurDePlateau gui = new AfficheurDePlateau();
        Plateau plateau = new Plateau();
        plateau.genererNouveauPlateau(0, TypePlateau.TUILE_TOMBANTE);
        
        gui.setPlateau(plateau);
        
        jframe.add(gui);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setMinimumSize(new Dimension(AfficheurDePlateau.LARGEUR_TUILE * 15 + 150, AfficheurDePlateau.HAUTEUR_TUILE * 15));
        jframe.setVisible(true);
    }
    
}
