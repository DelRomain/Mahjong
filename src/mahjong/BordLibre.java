package mahjong;

public enum BordLibre 
{
    HAUT,BAS,GAUCHE,DROITE;

    public final static BordLibre opposes[] = new BordLibre[]{BAS,HAUT,DROITE,GAUCHE};      //voir si utile
}
