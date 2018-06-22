package mahjong.GestionTuile;

/**
 * Classe représentant une tuile
 */
public class Tuile {

    private final FamilleDeTuile typeTuile;
    private final int id;
    private final Coordonnees coordonnees;

    /**
     * Crée une tuile à partir sa famille et son numéro
     * @param typeTuile : famille de la tuile
     * @param id : numéro de la tuile au sein de sa famille
     */
    public Tuile(FamilleDeTuile typeTuile, int id) {
        this.typeTuile = typeTuile;
        this.id = id;
        coordonnees = new Coordonnees(-1, -1);
    }

    /**
     * Crée une tuile à partir d'un fichier de sauvegarde
     * @param chaineCaractereSauvegarde : chaine contenant les informations de la tuile sous le format de sauvegarde
     */
    public Tuile(String chaineCaractereSauvegarde) 
    {
        String values[] = chaineCaractereSauvegarde.split("/");
        typeTuile = FamilleDeTuile.valueOf(values[0]);
        this.id = Integer.parseInt(values[1]);
        this.coordonnees = new Coordonnees(Integer.parseInt(values[2]),Integer.parseInt(values[3]));
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

        
    @Override
    public String toString() {
        return typeTuile.toString().substring(0, 1) + id;
    }

    public String save()
    {
        return typeTuile.toString()+"/"+id+"/"+coordonnees.getLigne()+"/"+coordonnees.getColonne();
    }
    
    public Tuile deepCopy()
    {
        return new Tuile(typeTuile,id);
    }
        
    public int getImageID()
    {
        return FamilleDeTuile.indexBaseFammile[typeTuile.ordinal()]+id;
    }
    
    public void setCoordonnees(int coordonneeX, int coordonneeY) {
        this.coordonnees.setLigne(coordonneeX);
        this.coordonnees.setColonne(coordonneeY);
    }

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }
    
    public int getLigne()
    {
        return coordonnees.getLigne();
    }
    
    public int getColonne()
    {
        return coordonnees.getColonne();
    }

    public FamilleDeTuile getTypeTuile() {
        return typeTuile;
    }

    public int getID() {
        return id;
    }
}
