package mahjong;

public class Coup 
{
    private final Tuile[] tuiles;
    private int score; 

    public Coup(Tuile[] tuiles) {
        this.tuiles = tuiles;
    }
    
    public Coup(Tuile[] tuiles, int score) {
        this.tuiles = tuiles;
        this.score = score;
    }

    public Coup(String chaineCaractereSauvegarde) 
    {
        String values[] = chaineCaractereSauvegarde.split(",");
        tuiles = new Tuile[]{new Tuile(values[0]),new Tuile(values[1])};
        score = Integer.parseInt(values[2]);
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
    
    public String save()
    {
       return tuiles[0].save()+","+tuiles[1].save()+","+score;
    }
}
