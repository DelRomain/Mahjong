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

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    public void setColonne(int colonne) {
        this.colonne = colonne;
    }
    
    public Emplacement add(Emplacement toAdd)
    {
        return new Emplacement(this.ligne+toAdd.ligne,this.colonne+toAdd.colonne);
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
}
