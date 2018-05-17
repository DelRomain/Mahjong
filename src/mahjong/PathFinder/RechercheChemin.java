/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mahjong.PathFinder;

import java.util.ArrayList;
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
        this.plateau = plateau;
    }

    public boolean rechercheChemin(Tuile depart, Tuile arrivee) {
        caseArrivee = new CaseRecherchee(null, arrivee.getCoordonnees()[0], arrivee.getCoordonnees()[1],null);
        return rechercheChemin(new CaseRecherchee(null, depart.getCoordonnees()[0], depart.getCoordonnees()[1],null));
    }

    private boolean rechercheChemin(CaseRecherchee caseRecherchee) {
        for (CaseAdjacente positionRelative : CaseAdjacente.values()) {
            int x = caseRecherchee.getX() + positionRelative.getX();
            int y = caseRecherchee.getY() + positionRelative.getY();
            Tuile tuile = plateau.getTuile(x, y);
            if (tuile == null) {
                CaseRecherchee caseObservee = new CaseRecherchee(caseRecherchee, x, y, positionRelative);
                if (caseObservee.getNombreAngleDroit() < 2) {
                    if (listeOuverte.contains(caseObservee)) {
                        //verif si chemin plus court 
                        //oui modif de chemin dans liste ouverte
                        //non on fait rien
                    } else {
                        listeOuverte.add(caseObservee);
                    }
                }
            }
        }
        return false;
    }

}
