package mahjong.coup;

import mahjong.Tuile;

public class CoupRetirerTuile extends Coup
{
    private Tuile[] tuiles;
    private int score; 

    public CoupRetirerTuile(Tuile[] tuiles, int score) {
        this.tuiles = tuiles;
        this.score = score;
    }
    
    public CoupRetirerTuile(Tuile[] tuiles) {
        this.tuiles = tuiles;
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
    
    @Override
    public String save()
    {
       return "CR"+tuiles[0].save()+","+tuiles[1].save()+","+score;
    }
}
