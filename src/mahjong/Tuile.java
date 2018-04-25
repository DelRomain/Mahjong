package mahjong;

public class Tuile 
{
    private String typeTuile;
    private int coordonneeX, coordonneeY;

    public Tuile(String typeTuile) {
        this.typeTuile = typeTuile;
    }

    public String getTypeTuile() {
        return typeTuile;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if(obj instanceof Tuile)
            return ((Tuile)obj).typeTuile.equals(typeTuile);
        return false;
    }

    public void setCoordonnees(int coordonneeX, int coordonneeY) {
        this.coordonneeX = coordonneeX;
        this.coordonneeY = coordonneeY;
    }

    public int[] getCoordonnees() {
        return new int[]{coordonneeX,coordonneeY};
    }   

    @Override
    public String toString() {
        return typeTuile;
    }
    
    
}
