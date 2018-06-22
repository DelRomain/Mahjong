package mahjong.TypeDeCoup;

/**
 * Coup lors duquel le plateau est mélangé
 * @author axelp
 */
public class CoupMelangerPlateau extends Coup
{
    private final long seed;

    /**
     * Crée un coup de type mélange du plateau
     * @param seed utilisé par le mélangeur pour effectuer le mélange
     */
    public CoupMelangerPlateau(long seed) {
        this.seed = seed;
        
    }

    public long getSeed() {
        return seed;
    }

    @Override
    public String save() {
        return "CM"+seed;
    }
    
}
