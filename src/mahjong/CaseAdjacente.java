package mahjong;

public enum CaseAdjacente 
{
    HAUT(0,1),BAS(0,-1),GAUCHE(-1,0),DROITE(1,0);
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
