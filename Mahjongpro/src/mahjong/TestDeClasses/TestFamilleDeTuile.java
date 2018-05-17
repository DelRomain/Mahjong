/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mahjong.TestDeClasses;

import java.util.Arrays;
import mahjong.FamilleDeTuile;
/**
 *
 * @author alafitte
 */
public class TestFamilleDeTuile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(FamilleDeTuile.ROND.getNombrePairesTuile());
        System.out.println(FamilleDeTuile.ROND.getNombreTuileDifferente());
        System.out.println(Arrays.toString(FamilleDeTuile.ROND.getNomFichier()));
        
    }
    
}
