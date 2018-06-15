/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mahjong;

import java.util.ArrayList;
import java.util.Collections;
import mahjong.PathFinder.RechercheChemin;
import static mahjong.Plateau.NOMBRE_COLONNE;
import static mahjong.Plateau.NOMBRE_LIGNE;
import mahjong.coup.CoupRetirerTuile;

public class Hint {

    private final ArrayList<CoupRetirerTuile> coupPossible;
    private final RechercheChemin rechercheChemin;
    private final Plateau plateau;

    public Hint(Plateau plateau) {
        coupPossible = new ArrayList<>();
        rechercheChemin = new RechercheChemin(plateau);
        this.plateau = plateau;
    }

    public void regenererListeCoupPossible() {
        coupPossible.clear();
        Emplacement emplacement;
        int indexRecherche;
        for (int index = 15; index < (NOMBRE_LIGNE - 1) * NOMBRE_COLONNE; index++) {
            System.out.println("recher sur index : " + index);
            if (plateau.getTuile(index % NOMBRE_COLONNE, index / NOMBRE_COLONNE) != null) {
                indexRecherche = index;
                System.out.println("Tuile trouvée ");
                while (indexRecherche < (NOMBRE_LIGNE - 1) * NOMBRE_COLONNE) {
                    if (plateau.getTuile(indexRecherche % NOMBRE_COLONNE, indexRecherche / NOMBRE_COLONNE) != null) {
                        emplacement = rechercherTuileAccessible(indexRecherche);
                        if (emplacement != null) {
                            System.out.println("Tuile apairable trouvée " + indexRecherche);
                            CoupRetirerTuile coup = new CoupRetirerTuile(
                                    plateau.getTuile(index % NOMBRE_COLONNE, index / NOMBRE_COLONNE),
                                    plateau.getTuile(emplacement.getLigne(), emplacement.getColonne()));
                            if (plateau.verifierCoupJouable(coup)) {
                                System.out.println("chemin OK");
                                coupPossible.add(coup);
                            }
                            System.out.println(emplacement.getLigne() + emplacement.getColonne() * 14 );
                            indexRecherche = emplacement.getLigne()+ emplacement.getColonne() * 14 ;
                        } else {
                            indexRecherche = 1234;
                        }
                    }
                    else
                        indexRecherche++;
                }
            }
            System.out.println("");
        }
    }

    /**
     * recherche l'emplacement de la prochaine tuile identique
     *
     * @param index index de la tuile qui lance la recherche (index =
     * colonne+14*ligne)
     * @return un emplacement si la tuile est trouvée et accesible, null sinon
     */
    public Emplacement rechercherTuileAccessible(int index) {
        Emplacement solution = null;
        final int indexMax = NOMBRE_LIGNE * NOMBRE_COLONNE;
        Tuile tuile = plateau.getTuile(index % NOMBRE_COLONNE, index / NOMBRE_COLONNE);
        int i = index + 1;
        boolean enRecherche = true;
        Emplacement emplacement;
        int x, y, j;
        Tuile tuile2;
        while (i < indexMax && enRecherche) {
            x = i % NOMBRE_COLONNE;
            y = i / NOMBRE_COLONNE;
            if (tuile.equals(plateau.getTuile(x, y))) {
                j = 0;
                while (j < CaseAdjacente.values().length && enRecherche) {
                    emplacement = new Emplacement(x, y).add(
                            new Emplacement(
                                    CaseAdjacente.values()[j].getLigne(),
                                    CaseAdjacente.values()[j].getColonne()));
                    tuile2 = plateau.getTuile(emplacement.getLigne(), emplacement.getColonne());
                    if (tuile2 == null || tuile2.equals(tuile)) {
                        enRecherche = false;
                        solution = new Emplacement(x, y);
                    }
                    j++;
                }
            }
            i++;
        }
        return solution;
    }

    public boolean aCoupValide() {
        return coupPossible.size()>0;
    }

    public CoupRetirerTuile getUnCoupPossible() {
        CoupRetirerTuile coup = null;
        if (!coupPossible.isEmpty()) {
            Collections.shuffle(coupPossible);
            coup = coupPossible.get(0);
        }
        return coup;
    }

}
