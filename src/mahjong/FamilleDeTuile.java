package mahjong;

public enum FamilleDeTuile {
    ROND(9, 2), BAMBOU(9, 2), CARACTERE(9, 2), VENT(4, 2), DRAGON(3, 2), SAISON(4, 0), FLEUR(4, 0);

    private final int nombreTuileDifferente;
    private final int nombrePairesTuile;
    public static final int[] indexBaseFammile = new int[]{0,9,18,27,31,34,38};

    private FamilleDeTuile(int nombreTuileDifferente, int nombrePairesTuile) {
        this.nombreTuileDifferente = nombreTuileDifferente;
        this.nombrePairesTuile = nombrePairesTuile;
    }

    public int getNombreTuileDifferente() {
        return nombreTuileDifferente;
    }

    public int getNombrePairesTuile() {
        return nombrePairesTuile;
    }

    public String[] getNomFichier() {
        String[] nomFichierImages = new String[nombreTuileDifferente];
        for (int i = 1; i <= nombreTuileDifferente; i++) {
            nomFichierImages[i - 1] = this.name().toLowerCase() + i + ".png";
        }
        return nomFichierImages;
    }
    
    public static FamilleDeTuile getFamilleParPremiereLettre(String nom)
    {
        boolean familleEnRecherche = true;
        int i = 0;
        while(familleEnRecherche && i<FamilleDeTuile.values().length)
        {
            if(nom.equalsIgnoreCase(FamilleDeTuile.values()[i].toString().substring(0,1)))
            {
                familleEnRecherche = false;
            }
            else
                i++;
        }
        return FamilleDeTuile.values()[i];
    }
}
