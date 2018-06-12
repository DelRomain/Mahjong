package mahjong;

public class Tuile {

    private final FamilleDeTuile typeTuile;
    private final int id;
    private final Emplacement coordonnees;

    public Tuile(FamilleDeTuile typeTuile, int id) {
        this.typeTuile = typeTuile;
        this.id = id;
        coordonnees = new Emplacement(-1, -1);
    }

    public Tuile(String chaineCaractereSauvegarde) 
    {
        String values[] = chaineCaractereSauvegarde.split("/");
        typeTuile = FamilleDeTuile.valueOf(values[0]);
        this.id = Integer.parseInt(values[1]);
        this.coordonnees = new Emplacement(Integer.parseInt(values[2]),Integer.parseInt(values[3]));
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
            if (this.typeTuile.estAAppairageStrict()) {
                return tuile.typeTuile == this.typeTuile && tuile.id == this.id;
            } else {
                return tuile.typeTuile == this.typeTuile;
            }
        }
        return false;
    }

    public void setCoordonnees(int coordonneeX, int coordonneeY) {
        this.coordonnees.setLigne(coordonneeX);
        this.coordonnees.setColonne(coordonneeY);
    }

    public int getLigne()
    {
        return coordonnees.getLigne();
    }
    
    public int getColonne()
    {
        return coordonnees.getColonne();
    }

    public Emplacement getCoordonnees() {
        return coordonnees;
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
        return typeTuile.toString()+"/"+id+"/"+coordonnees.getLigne()+"/"+coordonnees.getColonne();
    }
    
    public Tuile deepCopy()
    {
        return new Tuile(typeTuile,id);
    }
}
