package mahjong.Type_Plateau;

import mahjong.Plateau;
import mahjong.coup.CoupRetirerTuile;
import mahjong.Tuile;

public class PlateauTuileMontante extends PlateauGenerique {

    @Override
    public void traitementTerrainPostCoup(Tuile[][] plateau, CoupRetirerTuile coup) {

        for (int i = coup.getTuiles()[1].getLigne(); i < Plateau.NOMBRE_LIGNE - 1; i++) {
            plateau[i][coup.getTuiles()[1].getColonne()] = plateau[i + 1][coup.getTuiles()[1].getColonne()];
            plateau[i + 1][coup.getTuiles()[1].getColonne()] = null;
            if (plateau[i][coup.getTuiles()[1].getColonne()] != null) {
                plateau[i][coup.getTuiles()[1].getColonne()].setCoordonnees(i, coup.getTuiles()[1].getColonne());
            }

        }

        for (int i = coup.getTuiles()[0].getLigne(); i < Plateau.NOMBRE_LIGNE - 1; i++) {
            plateau[i][coup.getTuiles()[0].getColonne()] = plateau[i + 1][coup.getTuiles()[0].getColonne()];
            plateau[i + 1][coup.getTuiles()[0].getColonne()] = null;
            if (plateau[i][coup.getTuiles()[0].getColonne()] != null) {
                plateau[i][coup.getTuiles()[0].getColonne()].setCoordonnees(i, coup.getTuiles()[0].getColonne());
            }

        }
    }

    @Override
    public void annulerCoup(Tuile[][] plateau, CoupRetirerTuile coup) {
        for (int i = Plateau.NOMBRE_LIGNE-1; i > coup.getTuiles()[0].getLigne(); i--) {
            plateau[i][coup.getTuiles()[0].getColonne()] = plateau[i - 1][coup.getTuiles()[0].getColonne()];
            plateau[i - 1][coup.getTuiles()[0].getColonne()] = null;

            if (plateau[i][coup.getTuiles()[0].getColonne()] != null) {
                plateau[i][coup.getTuiles()[0].getColonne()].setCoordonnees(i, coup.getTuiles()[0].getColonne());
            }
        }
        plateau[coup.getTuiles()[0].getLigne()][coup.getTuiles()[0].getColonne()] = coup.getTuiles()[0];
        
        for (int i = Plateau.NOMBRE_LIGNE-1; i > coup.getTuiles()[1].getLigne(); i--) {
            plateau[i][coup.getTuiles()[1].getColonne()] = plateau[i - 1][coup.getTuiles()[1].getColonne()];
            plateau[i - 1][coup.getTuiles()[1].getColonne()] = null;

            if (plateau[i][coup.getTuiles()[1].getColonne()] != null) {
                plateau[i][coup.getTuiles()[1].getColonne()].setCoordonnees(i, coup.getTuiles()[1].getColonne());
            }
        }
        plateau[coup.getTuiles()[1].getLigne()][coup.getTuiles()[1].getColonne()] = coup.getTuiles()[1];
    }
}
