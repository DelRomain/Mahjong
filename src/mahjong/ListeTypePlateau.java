package mahjong;

import mahjong.Type_Plateau.PlateauTuileTombante;

public enum ListeTypePlateau 
{
    TUILE_TOMBANTE("Tuile tombante", new PlateauTuileTombante());
    
    private final TypePlateau typePlateau;
    private final String nom;

    private ListeTypePlateau(String nom, TypePlateau typePlateau) {
        this.nom = nom;
        this.typePlateau = typePlateau;
    }

    public TypePlateau getTypePlateau() {
        return typePlateau;
    }

    public String getNom() {
        return nom;
    }
    
    public static ListeTypePlateau getTypeParNom(String nom)
    {
        ListeTypePlateau type = null;
        int i = 0;
        while(type == null && i<ListeTypePlateau.values().length)
        {
            if(nom.equals(ListeTypePlateau.values()[i].nom))
                type = ListeTypePlateau.values()[i];
        }
        return type;
    }
}
