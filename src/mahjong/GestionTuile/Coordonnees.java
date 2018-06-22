package mahjong.GestionTuile;

import mahjong.RechercheChemin.Direction;

/**
 * Classe stockant des coordonnées
 */
public class Coordonnees {

    private int ligne;
    private int colonne;

    public Coordonnees(int ligne, int colonne) {
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

    public Coordonnees copy() {
        return new Coordonnees(ligne, colonne);
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
        final Coordonnees other = (Coordonnees) obj;
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
        return this.ligne + "/" + this.colonne;
    }

    /**
     * Fonction permettant de créer un nouveau jeu de coordonnées à partir de
     * coordonnées et d'une direction
     *
     * @param origine : coordonnées de base autour de laquelle sera généré le nouveau jeu de coordonnées
     * @param direction dans laquelle la nouvelle coordonnées sera créé
     * @return la nouvelle coordonnées
     */
    public static Coordonnees getEmplacementAdjacent(Coordonnees origine, Direction direction) {
        return new Coordonnees(origine.ligne + direction.getLigne(), origine.colonne + direction.getColonne());
    }

}
