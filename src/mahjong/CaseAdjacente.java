package mahjong;

/**
 * Enumeration donnant les positions relatives des tuiles situé au-dessus, en dessous, à gauche et à droite d'une tuile;
 */
public enum CaseAdjacente 
{
    HAUT(-1,0),BAS(1,0),GAUCHE(0,-1),DROITE(0,1);
    private final int ligne;
    private final int colonne;

    private CaseAdjacente(int ligne, int colonne) 
    {
        this.ligne = ligne;
        this.colonne = colonne;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }
    
}
