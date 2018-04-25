package mahjong;

public abstract class TypePlateau 
{
    /**
     * Contain et applique la "physique du plateau sur un plateau
     * @param plateau sur lequel la physique est appliqué
     */
    public abstract void traitementTerrainPostCoup(Tuile[][] plateau);
}
