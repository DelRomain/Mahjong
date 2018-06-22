package mahjong.GestionTuile;

/**
 * Enumeration contenant toutes les propriétés des tuiles du mahjong:
 * Le nombre de tuile par famille
 * Si l'appairage est strict, c'est-à-dire si on peut former une paire avec deux tuiles d'ID differentes ou pas
 */
public enum FamilleDeTuile {
    ROND(9, true), BAMBOU(9, true), CARACTERE(9, true), VENT(4, true), DRAGON(3, true), SAISON(4, false), FLEUR(4, false);

    private final int nombreTuileDifferente;
    private final boolean appairageStrict;
    public static final int[] indexBaseFammile = new int[]{0,9,18,27,31,34,38};

    private FamilleDeTuile(int nombreTuileDifferente, boolean appairageStrict) {
        this.nombreTuileDifferente = nombreTuileDifferente;
        this.appairageStrict = appairageStrict;
    }

    public int getNombreTuileDifferente() {
        return nombreTuileDifferente;
    }

    public boolean estAAppairageStrict() {
        return appairageStrict;
    }

    /**
     * Genere une liste de nom de fichier contenant les images du jeu
     * @return liste de fichier contenant les images 
     */
    public String[] getNomFichier() {
        String[] nomFichierImages = new String[nombreTuileDifferente];
        for (int i = 1; i <= nombreTuileDifferente; i++) {
            nomFichierImages[i - 1] = this.name().toLowerCase() + i + ".png";
        }
        return nomFichierImages;
    }
}
