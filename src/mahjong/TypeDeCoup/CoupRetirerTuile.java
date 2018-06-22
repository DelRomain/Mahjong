package mahjong.TypeDeCoup;

import mahjong.GestionTuile.Tuile;

/**
 * Coup lors duquel deux tuiles sont retirées du plateau
 */
public class CoupRetirerTuile extends Coup
{
    private final Tuile[] tuiles;
    private int score; 

    public CoupRetirerTuile(Tuile tuile1, Tuile tuile2, int score) {
        this.tuiles = new Tuile[]{tuile1,tuile2};
        this.score = score;
    }
    
    public CoupRetirerTuile(Tuile tuile1, Tuile tuile2) {
        if (tuile1.getLigne() > tuile2.getLigne()) {
            this.tuiles = new Tuile[]{tuile2,tuile1};
        } else {
            this.tuiles = new Tuile[]{tuile1,tuile2};
        }
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
