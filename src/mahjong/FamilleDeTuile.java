package mahjong;

public enum FamilleDeTuile 
{
    ROND(9,4),BAMBOU(9,4),CARACTERE(9,4),VENT(4,4),DRAGON(3,4),SAISON(4,1),FLEUR(4,1);
    
    private final int nombreTuileDifferente;
    private final int nombreExemplaireTuile;

    private FamilleDeTuile(int nombreTuileDifferente, int nombreExemplaireTuile) {
        this.nombreTuileDifferente = nombreTuileDifferente;
        this.nombreExemplaireTuile = nombreExemplaireTuile;
    }

    public int getNombreExemplaireTuile() {
        return nombreExemplaireTuile;
    }

    public int getNombreTuileDifferente() {
        return nombreTuileDifferente;
    }

    public String[] getNomFichier()
    {
        String[] nomFichierImages = new String[nombreTuileDifferente];
        for(int i = 1; i<=nombreTuileDifferente;i++)
        {
            nomFichierImages[i-1]=this.name().toLowerCase()+i+".png";
        }
        return nomFichierImages;
    }
    
    public static int getIDUniqueTuile(String identifiantTuile)
    {
        String nomFamille = identifiantTuile.substring(0, 1);
        int i = 0;
        int identifiant = 0;
        while(!nomFamille.equalsIgnoreCase(FamilleDeTuile.values()[i].toString().substring(0,1)))
        {
            i++;
            identifiant += FamilleDeTuile.values()[i].nombreTuileDifferente;
        }
        identifiant += Integer.parseInt(identifiantTuile.substring(1, 2));
        return identifiant;
    }
}
