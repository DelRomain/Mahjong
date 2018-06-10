package mahjong;

import mahjong.coup.CoupRetirerTuile;

public abstract class TypePlateau 
{
    /**
     * Contient et applique la "physique" du plateau sur un plateau
     * @param plateau sur lequel la physique est appliqué
     */
    public abstract void traitementTerrainPostCoup(Tuile[][] plateau, CoupRetirerTuile coup);
    public abstract void remonterCoup(Tuile[][] plateau, CoupRetirerTuile coup);
}
