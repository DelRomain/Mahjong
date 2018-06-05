package mahjong.Type_Plateau;

import mahjong.Coup;
import mahjong.Tuile;

public abstract class PlateauGenerique 
{
    /**
     * Contient et applique la "physique" du plateau sur un plateau
     * @param plateau sur lequel la physique est appliqué
     */
    public abstract void traitementTerrainPostCoup(Tuile[][] plateau, Coup coup);
    public abstract void remonterCoup(Tuile[][] plateau, Coup coup);
}
