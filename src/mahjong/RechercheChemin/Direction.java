package mahjong.RechercheChemin;

/**
 * Enumeration définisant les directions atteignable depuis une tuile donnée et
 * donne une coordonnée relative de celle-ci
 */
public enum Direction {
    HAUT(-1, 0), BAS(1, 0), GAUCHE(0, -1), DROITE(0, 1);
    private final int ligne;
    private final int colonne;

    private Direction(int ligne, int colonne) {
        this.ligne = ligne;
        this.colonne = colonne;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    public static Direction getOpposite(Direction direction) {
        Direction oppose = null;
        if (direction != null) {
            switch (direction) {
                case HAUT:
                    oppose = BAS;
                    break;
                case BAS:
                    oppose = HAUT;
                    break;
                case GAUCHE:
                    oppose = DROITE;
                    break;
                case DROITE:
                    oppose = GAUCHE;
                    break;
            }
        }
        return oppose;
    }

}
