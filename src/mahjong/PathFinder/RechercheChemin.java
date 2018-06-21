package mahjong.PathFinder;

import java.util.ArrayList;
import java.util.Collections;
import mahjong.Direction;
import mahjong.Emplacement;
import mahjong.Plateau;
import mahjong.Tuile;

public class RechercheChemin {

    private CaseRecherchee caseArrivee;
    private final ArrayList<CaseRecherchee> listeOuverte;
    private final Plateau plateau;

    public RechercheChemin(Plateau plateau) {
        this.listeOuverte = new ArrayList();
        this.plateau = plateau;
    }

    public boolean rechercheChemin(Tuile depart, Tuile arrivee) {
        listeOuverte.clear();

        caseArrivee = new CaseRecherchee(null, arrivee.getCoordonnees(), null);
        return rechercheChemin(new CaseRecherchee(null, depart.getCoordonnees(), null));
    }

    private boolean rechercheChemin(CaseRecherchee caseRecherchee) {
        Emplacement emplacement;
        boolean cheminTrouvee = false;
        for (Direction direction : Direction.values()) {
            if(direction != Direction.getOpposite(caseRecherchee.getDirection()))
            {
                emplacement = Emplacement.getEmplacementAdjacent(caseRecherchee.getEmplacement(), direction);
                if(estSurPlateau(emplacement))
                {
                    Tuile tuile = this.plateau.getTuile(emplacement.getLigne(), emplacement.getColonne());
                    CaseRecherchee nouvelleCase = new CaseRecherchee(caseRecherchee, emplacement, direction);
                    if(tuile == null)
                    {
                        if(nouvelleCase.getNombreAngleDroit()<=2)
                        {
                            nouvelleCase.setDistance(getDistance(nouvelleCase, caseArrivee));
                            listeOuverte.add(nouvelleCase);
                        }
                    }
                    else if(nouvelleCase.equals(caseArrivee))
                    {
                        if(nouvelleCase.getNombreAngleDroit()<=2)
                        {
                            listeOuverte.add(nouvelleCase);
                            cheminTrouvee = true;
                        }
                    }
                }
            }
        }
        if(!cheminTrouvee && !listeOuverte.isEmpty())
        {
            CaseRecherchee prochaineExaminee = Collections.min(listeOuverte);
            listeOuverte.remove(prochaineExaminee);
            cheminTrouvee = rechercheChemin(prochaineExaminee);
        }
        return cheminTrouvee;
    }

    private int getDistance(CaseRecherchee caseAEvaluer, CaseRecherchee caseReference) {
        return (int) Math.pow(caseAEvaluer.getLigne() - caseReference.getLigne(), 2)
                + (int) Math.pow(caseAEvaluer.getColonne() - caseReference.getColonne(), 2);
    }

    private boolean estSurPlateau(Emplacement emplacement) {
        return emplacement.getLigne() >= 0 && emplacement.getColonne() >= 0 && emplacement.getLigne() < Plateau.NOMBRE_COLONNE && emplacement.getColonne() < Plateau.NOMBRE_LIGNE;
    }

    public CaseRecherchee getCheminEnCours() {
        int index = listeOuverte.indexOf(caseArrivee);
        return index != -1 ? listeOuverte.get(index) : null;
    }
}
