package mahjong.Type_Plateau;

import mahjong.Coup;
import mahjong.Tuile;

public class PlateauTuileTombante extends PlateauGenerique {

    @Override
    public void traitementTerrainPostCoup(Tuile[][] plateau, Coup coup) {

        int[] premiereTuile;
        int[] deuxiemeTuile;
        if (coup.getTuiles()[0].getCoordonnees()[0] < coup.getTuiles()[1].getCoordonnees()[0]) {
            premiereTuile = coup.getTuiles()[0].getCoordonnees();
            deuxiemeTuile = coup.getTuiles()[1].getCoordonnees();
        } else {
            premiereTuile = coup.getTuiles()[1].getCoordonnees();
            deuxiemeTuile = coup.getTuiles()[0].getCoordonnees();
        }
        for (int i = premiereTuile[0]; i > 0; i--) {
            plateau[i][premiereTuile[1]] = plateau[i - 1][premiereTuile[1]];
            plateau[i - 1][premiereTuile[1]] = null;
            if (plateau[i][premiereTuile[1]] != null) {
                plateau[i][premiereTuile[1]].setCoordonnees(i, premiereTuile[1]);
            }

        }
        for (int i = deuxiemeTuile[0]; i > 0; i--) {
            plateau[i][deuxiemeTuile[1]] = plateau[i - 1][deuxiemeTuile[1]];
            plateau[i - 1][deuxiemeTuile[1]] = null;
            if (plateau[i][deuxiemeTuile[1]] != null) {
                plateau[i][deuxiemeTuile[1]].setCoordonnees(i, deuxiemeTuile[1]);
            }

        }
    }

    @Override
    public void remonterCoup(Tuile[][] plateau, Coup coup) {
        int[] premiereTuile;
        int[] deuxiemeTuile;
        if (coup.getTuiles()[0].getCoordonnees()[0] > coup.getTuiles()[1].getCoordonnees()[0]) {
            premiereTuile = coup.getTuiles()[0].getCoordonnees();
            deuxiemeTuile = coup.getTuiles()[1].getCoordonnees();
        } else {
            premiereTuile = coup.getTuiles()[1].getCoordonnees();
            deuxiemeTuile = coup.getTuiles()[0].getCoordonnees();
        }
        for (int i = 0; i < premiereTuile[0]; i++) {
            plateau[i][premiereTuile[1]] = plateau[i + 1][premiereTuile[1]];
            plateau[i + 1][premiereTuile[1]] = null;
            
            if(plateau[i][premiereTuile[1]] != null)
                plateau[i][premiereTuile[1]].setCoordonnees(i, premiereTuile[1]);
        }
        plateau[premiereTuile[0]][premiereTuile[1]]=coup.getTuiles()[0];
        
       for (int i = 0; i < deuxiemeTuile[0]; i++) {
            plateau[i][deuxiemeTuile[1]] = plateau[i + 1][deuxiemeTuile[1]];
            plateau[i + 1][deuxiemeTuile[1]] = null;
            
            if(plateau[i][deuxiemeTuile[1]] != null)
                plateau[i][deuxiemeTuile[1]].setCoordonnees(i, deuxiemeTuile[1]);    
        }
        plateau[deuxiemeTuile[0]][deuxiemeTuile[1]]=coup.getTuiles()[1];
    }
}
