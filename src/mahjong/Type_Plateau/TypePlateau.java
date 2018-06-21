package mahjong.Type_Plateau;

public enum TypePlateau 
{
    TUILE_TOMBANTE("Tuile tombante", new PlateauTuileTombante()),
    TUILE_MONTANTE("Tuile montante", new PlateauTuileMontante());
    
    private final PlateauGenerique typePlateau;
    private final String nom;

    private TypePlateau(String nom, PlateauGenerique typePlateau) {
        this.nom = nom;
        this.typePlateau = typePlateau;
    }

    public PlateauGenerique getPhysiquePlateau() {
        return typePlateau;
    }

    public String getNom() {
        return nom;
    }
    
    public static TypePlateau getTypeParNom(String nom)
    {
        TypePlateau type = null;
        int i = 0;
        while(type == null && i<TypePlateau.values().length)
        {
            if(nom.equalsIgnoreCase(TypePlateau.values()[i].nom))
                type = TypePlateau.values()[i];
            i++;
        }
        return type;
    }
}
