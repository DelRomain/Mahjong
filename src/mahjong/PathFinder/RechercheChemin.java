/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mahjong.PathFinder;

import java.util.ArrayList;
import java.util.Collections;
import mahjong.CaseAdjacente;
import mahjong.Plateau;
import mahjong.Tuile;

/**
 *
 * @author alafitte
 */
public class RechercheChemin {

    private CaseRecherchee caseArrivee;
    private ArrayList<CaseRecherchee> listeOuverte;
    private ArrayList<CaseRecherchee> listeFermee;
    private final Plateau plateau;

    public RechercheChemin(Plateau plateau) {
        this.listeOuverte = new ArrayList();
        this.listeFermee = new ArrayList();
        this.plateau = plateau;
    }

    public boolean rechercheChemin(Tuile depart, Tuile arrivee) {
        listeOuverte.clear();
        listeFermee.clear();

        caseArrivee = new CaseRecherchee(null, arrivee.getCoordonnees()[0], arrivee.getCoordonnees()[1], null);
        return rechercheChemin(new CaseRecherchee(null, depart.getCoordonnees()[0], depart.getCoordonnees()[1], null));
    }

    private boolean rechercheChemin(CaseRecherchee caseRecherchee) {
        for (CaseAdjacente positionRelative : CaseAdjacente.values()) {
            int x = caseRecherchee.getLigne() + positionRelative.getLigne();
            int y = caseRecherchee.getColonne() + positionRelative.getColonne();
            if (estSurPlateau(x, y)) {
                Tuile tuile = plateau.getTuile(x, y);

                if (tuile == null || (caseArrivee.getLigne() == x && caseArrivee.getColonne() == y)) {
                    CaseRecherchee caseObservee = new CaseRecherchee(caseRecherchee, x, y, positionRelative);
                    caseObservee.setDistance(getDistance(caseArrivee, caseObservee));
                    if (caseObservee.getNombreAngleDroit() <= 2) {
                        if (listeOuverte.contains(caseObservee)) {
                            int index = listeOuverte.indexOf(caseObservee);
                            int distanceArrivee = getDistance(caseObservee, caseArrivee);

                            if (listeOuverte.get(index).getTotal() > distanceArrivee) {
                                listeOuverte.get(index).setTotal(distanceArrivee);
                                listeOuverte.get(index).setParent(caseObservee.getParent());
                            }
                        } else {
                            listeOuverte.add(caseObservee);
                        }
                    }
                }
            }
        }

        boolean aFini = listeOuverte.contains(caseArrivee);
        if (!listeOuverte.isEmpty() && !aFini) {
            CaseRecherchee caseCourante = Collections.min(listeOuverte);
            listeFermee.add(caseCourante);
            listeOuverte.remove(caseCourante);
            aFini = rechercheChemin(caseCourante);
        }
        return aFini;
    }

    private int getDistance(CaseRecherchee caseAEvaluer, CaseRecherchee caseReference) {
        return (int) Math.pow(caseAEvaluer.getLigne() - caseReference.getLigne(), 2)
                + (int) Math.pow(caseAEvaluer.getColonne() - caseReference.getColonne(), 2);
    }

    private boolean estSurPlateau(int x, int y) {
        return x >= 0 && y >= 0 && x < Plateau.NOMBRE_COLONNE && y < Plateau.NOMBRE_LIGNE;
    }

    public CaseRecherchee getCheminEnCours() {
        return listeOuverte.get(listeOuverte.indexOf(caseArrivee));
    }
}
