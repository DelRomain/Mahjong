package mahjong;

public enum FamilleDeTuile {
    ROND(9, 2), BAMBOU(9, 2), CARACTERE(9, 2), VENT(4, 2), DRAGON(3, 2), SAISON(4, 0), FLEUR(4, 0);

    private final int nombreTuileDifferente;
    private final int nombrePairesTuile;

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
}
