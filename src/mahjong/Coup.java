package mahjong;

public class Coup 
{
    private final Tuile[] tuiles;
    private int score; 

    public Coup(Tuile[] tuiles) {
        this.tuiles = tuiles;
        this.score = score;
    }

    public Tuile[] getTuiles() {
        return tuiles;
    }

    public boolean isValid() 
    {
       return tuiles[0].equals(tuiles[1]);
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public int getScore() {
        return score;
    }
}
