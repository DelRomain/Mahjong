package mahjong;

/**
 * Classe stockant des coordonnées
 */
public class Emplacement {

    private int ligne;
    private int colonne;

    public Emplacement(int ligne, int colonne) {
        this.ligne = ligne;
        this.colonne = colonne;
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }
    
    public int getLigne() {
        return ligne;
    }
    
    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    public int getColonne() {
        return colonne;
    }

    public Emplacement copy()
    {
        return new Emplacement(ligne, colonne);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Emplacement other = (Emplacement) obj;
        if (this.ligne != other.ligne) {
            return false;
        }
        if (this.colonne != other.colonne) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return this.ligne+"/"+this.colonne; 
    }
    
    public static Emplacement getEmplacementAdjacent(Emplacement origine, Direction direction)
    {
        return new Emplacement(origine.ligne+direction.getLigne(), origine.colonne+direction.getColonne());
    }
    
    public static Emplacement add(Emplacement emplacement1, Emplacement emplacement2)
    {
        return new Emplacement(
                emplacement1.ligne+emplacement2.ligne,
                emplacement2.colonne+emplacement2.colonne);
    }
}
