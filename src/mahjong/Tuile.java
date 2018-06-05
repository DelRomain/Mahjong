package mahjong;

public class Tuile {

    private final FamilleDeTuile typeTuile;
    private final int id;
    private int coordonneeX, coordonneeY;

    public Tuile(FamilleDeTuile typeTuile, int id) {
        this.typeTuile = typeTuile;
        this.id = id;
    }

    public Tuile(String chaineCaractereSauvegarde) 
    {
        String values[] = chaineCaractereSauvegarde.split("/");
        typeTuile = FamilleDeTuile.valueOf(values[0]);
        this.id = Integer.parseInt(values[1]);
        this.coordonneeX = Integer.parseInt(values[2]);
        this.coordonneeY = Integer.parseInt(values[3]);
    }

    public FamilleDeTuile getTypeTuile() {
        return typeTuile;
    }

    public int getID() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tuile) {
            Tuile tuile = ((Tuile) obj);
            if (this.typeTuile.getNombrePairesTuile() == 0) {
                return tuile.typeTuile == this.typeTuile;
            } else {
                return tuile.typeTuile == this.typeTuile && tuile.id == this.id;
            }
        }
        return false;
    }

    public void setCoordonnees(int coordonneeX, int coordonneeY) {
        this.coordonneeX = coordonneeX;
        this.coordonneeY = coordonneeY;
    }

    public int[] getCoordonnees() {
        return new int[]{coordonneeX, coordonneeY};
    }

    @Override
    public String toString() {
        return typeTuile.toString().substring(0, 1) + id;
    }
    
    public int getImageID()
    {
        return FamilleDeTuile.indexBaseFammile[typeTuile.ordinal()]+id;
    }
    
    public String save()
    {
        return typeTuile.toString()+"/"+id+"/"+coordonneeX+"/"+coordonneeY;
    }
}
