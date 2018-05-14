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
public class TestCoup {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Tuile tuile1 = new Tuile(FamilleDeTuile.FLEUR,1);
        Tuile tuile2 = new Tuile(FamilleDeTuile.FLEUR,2);
        Tuile tuile[]=new Tuile[]{tuile1,tuile2};
        
        Coup coup = new Coup(tuile);
        
        System.out.println(Arrays.toString(coup.getTuiles()));
        
        System.out.println(coup.isValid());
        
     
      
    }

    
}
