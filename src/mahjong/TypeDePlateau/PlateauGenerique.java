package mahjong.TypeDePlateau;

import mahjong.TypeDeCoup.CoupRetirerTuile;
import mahjong.GestionTuile.Tuile;

public abstract class PlateauGenerique {

    /**
     * Contient et applique la "physique" du plateau sur un plateau
     *
     * @param plateau sur lequel la physique est appliqué
     * @param coup est le coup qui vient d'être joué
     */
    public abstract void traitementTerrainPostCoup(Tuile[][] plateau, CoupRetirerTuile coup);

    public abstract void annulerCoup(Tuile[][] plateau, CoupRetirerTuile coup);
}
