package mahjong.RechercheChemin;

import java.util.ArrayList;
import java.util.Collections;
import mahjong.GestionTuile.Coordonnees;
import mahjong.GestionPlateau.Plateau;
import mahjong.GestionTuile.Tuile;

/**
 * Classe permetant de savoir si un chemin valide existe entre deux tuiles
 */
public class RechercheChemin {

    private CaseRecherchee caseArrivee;
    private final ArrayList<CaseRecherchee> listeOuverte;
    private final Plateau plateau;

    public RechercheChemin(Plateau plateau) {
        this.listeOuverte = new ArrayList();
        this.plateau = plateau;
    }

    /**
     * Lance une recherche de chemin
     *
     * @param depart tuile d'où part la recherche
     * @param arrivee tuile où l'on veut aller
     * @return vrai s'il existe un chemin valide, faux sinon
     */
    public boolean rechercheChemin(Tuile depart, Tuile arrivee) {
        listeOuverte.clear();

        caseArrivee = new CaseRecherchee(null, arrivee.getCoordonnees(), null);
        return rechercheChemin(new CaseRecherchee(null, depart.getCoordonnees(), null));
    }

    /**
     * Fonction récurente permetant de tester tout les chemins partant de la
     * case indiqué
     *
     * @param caseRecherchee d'où part la recher
     * @return vrai s'il existe un chemin valide, faux sinon
     */
    private boolean rechercheChemin(CaseRecherchee caseRecherchee) {
        Coordonnees emplacement;
        boolean cheminTrouvee = false;
        for (Direction direction : Direction.values()) {
            if (direction != Direction.getOpposite(caseRecherchee.getDirection())) {
                emplacement = Coordonnees.getEmplacementAdjacent(caseRecherchee.getEmplacement(), direction);
                if (estSurPlateau(emplacement)) {
                    Tuile tuile = this.plateau.getTuile(emplacement.getLigne(), emplacement.getColonne());
                    CaseRecherchee nouvelleCase = new CaseRecherchee(caseRecherchee, emplacement, direction);
                    if (tuile == null) {
                        if (nouvelleCase.getNombreAngleDroit() <= 2) {
                            nouvelleCase.setDistance(getDistance(nouvelleCase, caseArrivee));
                            listeOuverte.add(nouvelleCase);
                        }
                    } else if (nouvelleCase.equals(caseArrivee)) {
                        if (nouvelleCase.getNombreAngleDroit() <= 2) {
                            listeOuverte.add(nouvelleCase);
                            cheminTrouvee = true;
                        }
                    }
                }
            }
        }
        if (!cheminTrouvee && !listeOuverte.isEmpty()) {
            CaseRecherchee prochaineExaminee = Collections.min(listeOuverte);
            listeOuverte.remove(prochaineExaminee);
            cheminTrouvee = rechercheChemin(prochaineExaminee);
        }
        return cheminTrouvee;
    }

    /**
     * Calcule la distance entre la case de réfenrence (arrivé) et la case à évaluer
     * @param caseAEvaluer
     * @param caseReference
     * @return le carré de la distance euclidienne entre les deux cases
     */
    private int getDistance(CaseRecherchee caseAEvaluer, CaseRecherchee caseReference) {
        return (int) Math.pow(caseAEvaluer.getLigne() - caseReference.getLigne(), 2)
                + (int) Math.pow(caseAEvaluer.getColonne() - caseReference.getColonne(), 2);
    }

    /**
     * Permet de savoir si un emplacement est sur le plateau
     * @param emplacement
     * @return vrai s'il est sur le plateau, faux sinon
     */
    private boolean estSurPlateau(Coordonnees emplacement) {
        return emplacement.getLigne() >= 0 && emplacement.getColonne() >= 0 && emplacement.getLigne() < Plateau.NOMBRE_COLONNE && emplacement.getColonne() < Plateau.NOMBRE_LIGNE;
    }

    /**
     * Renvoie la case contenant le dernier chemin valide
     * @return la case ayant le chemin valide
     */
    public CaseRecherchee getCheminEnCours() {
        int index = listeOuverte.indexOf(caseArrivee);
        return index != -1 ? listeOuverte.get(index) : null;
    }
}
