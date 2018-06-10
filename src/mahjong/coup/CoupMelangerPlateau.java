package mahjong.coup;

public class CoupMelangerPlateau extends Coup
{
    private final long seed;

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
