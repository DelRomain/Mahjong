package mahjong;

public enum CaseAdjacente 
{
    HAUT(-1,0),BAS(1,0),GAUCHE(0,-1),DROITE(0,1);
    private final int x;
    private final int y;

    private CaseAdjacente(int x, int y) 
    {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
}
