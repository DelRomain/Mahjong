/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mahjong.TestDeClasses;

import java.util.Arrays;
import mahjong.*;
/**
 *
 * @author alafitte
 */
public class TestTuile {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        
        Tuile tuile1 = new Tuile(FamilleDeTuile.ROND,5);
        Tuile tuile2 = new Tuile(FamilleDeTuile.FLEUR,4);
        System.out.println(tuile1);
        System.out.println(tuile1.getID());
        System.out.println(tuile1.getTypeTuile());
        
        System.out.println(tuile1.equals(tuile1));
        System.out.println(tuile1.equals(tuile2));
        
        tuile1.setCoordonnees(1, 2);
        System.out.println(tuile1.getCoordonnees());
        System.out.println(tuile1.toString());
        
        System.out.println(tuile2.getImageID());
        
        
    }
    
}
