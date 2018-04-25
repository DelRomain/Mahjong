package mahjong;

public class Coup 
{
    private final Tuile[] tuiles;

    public Coup(Tuile[] tuiles) {
        this.tuiles = tuiles;
    }

    public Tuile[] getTuiles() {
        return tuiles;
    }
}
