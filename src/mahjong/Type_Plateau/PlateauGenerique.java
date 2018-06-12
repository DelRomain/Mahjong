package mahjong.Type_Plateau;

import mahjong.coup.CoupRetirerTuile;
import mahjong.Tuile;

public abstract class PlateauGenerique 
{
    /**
     * Contient et applique la "physique" du plateau sur un plateau
     * @param plateau sur lequel la physique est appliqué
     */
    public abstract void traitementTerrainPostCoup(Tuile[][] plateau, CoupRetirerTuile coup);
    public abstract void annulerCoup(Tuile[][] plateau, CoupRetirerTuile coup);
}
