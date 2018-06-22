package mahjong.TypeDePlateau;

import mahjong.TypeDeCoup.CoupRetirerTuile;
import mahjong.GestionTuile.Tuile;

/**
 * Plateau dans lequel les tuiles ont une gravité descendante
 */
public class PlateauTuileTombante extends PlateauGenerique {

    @Override
    public void traitementTerrainPostCoup(Tuile[][] plateau, CoupRetirerTuile coup) {

        for (int i = coup.getTuiles()[0].getLigne(); i > 0; i--) {
            plateau[i][coup.getTuiles()[0].getColonne()] = plateau[i - 1][coup.getTuiles()[0].getColonne()];
            plateau[i - 1][coup.getTuiles()[0].getColonne()] = null;
            if (plateau[i][coup.getTuiles()[0].getColonne()] != null) {
                plateau[i][coup.getTuiles()[0].getColonne()].setCoordonnees(i, coup.getTuiles()[0].getColonne());
            }

        }
        for (int i = coup.getTuiles()[1].getLigne(); i > 0; i--) {
            plateau[i][coup.getTuiles()[1].getColonne()] = plateau[i - 1][coup.getTuiles()[1].getColonne()];
            plateau[i - 1][coup.getTuiles()[1].getColonne()] = null;
            if (plateau[i][coup.getTuiles()[1].getColonne()] != null) {
                plateau[i][coup.getTuiles()[1].getColonne()].setCoordonnees(i, coup.getTuiles()[1].getColonne());
            }

        }
    }

    @Override
    public void annulerCoup(Tuile[][] plateau, CoupRetirerTuile coup) {

        for (int i = 0; i < coup.getTuiles()[1].getLigne(); i++) {
            plateau[i][coup.getTuiles()[1].getColonne()] = plateau[i + 1][coup.getTuiles()[1].getColonne()];
            plateau[i + 1][coup.getTuiles()[1].getColonne()] = null;

            if (plateau[i][coup.getTuiles()[1].getColonne()] != null) {
                plateau[i][coup.getTuiles()[1].getColonne()].setCoordonnees(i, coup.getTuiles()[1].getColonne());
            }
        }
        plateau[coup.getTuiles()[1].getLigne()][coup.getTuiles()[1].getColonne()] = coup.getTuiles()[1];

        for (int i = 0; i < coup.getTuiles()[0].getLigne(); i++) {
            plateau[i][coup.getTuiles()[0].getColonne()] = plateau[i + 1][coup.getTuiles()[0].getColonne()];
            plateau[i + 1][coup.getTuiles()[0].getColonne()] = null;

            if (plateau[i][coup.getTuiles()[0].getColonne()] != null) {
                plateau[i][coup.getTuiles()[0].getColonne()].setCoordonnees(i, coup.getTuiles()[0].getColonne());
            }
        }
        plateau[coup.getTuiles()[0].getLigne()][coup.getTuiles()[0].getColonne()] = coup.getTuiles()[0];
    }
}
