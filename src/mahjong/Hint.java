package mahjong;

import java.util.ArrayList;
import java.util.Collections;
import mahjong.PathFinder.RechercheChemin;
import static mahjong.Plateau.NOMBRE_COLONNE;
import static mahjong.Plateau.NOMBRE_LIGNE;
import mahjong.coup.CoupRetirerTuile;

/**
 * Classe gérant la recherche de coup possible.
 * Elle permet de générer des indications de coups possibles et de vérifier si le plateau n'est pas bloqué.
 */
public class Hint {

    private final ArrayList<CoupRetirerTuile> coupPossible;
    private final RechercheChemin rechercheChemin;
    private final Plateau plateau;

    public Hint(Plateau plateau) {
        coupPossible = new ArrayList<>();
        rechercheChemin = new RechercheChemin(plateau);
        this.plateau = plateau;
    }

    /**
     * Fonction regénérant la liste de coup possible sur le plateau.
     */
    public void regenererListeCoupPossible() {
        coupPossible.clear();
        Emplacement emplacement;
        int indexRecherche;
        for (int index = 15; index < (NOMBRE_LIGNE - 1) * NOMBRE_COLONNE; index++) {
            if (plateau.getTuile(index % NOMBRE_COLONNE, index / NOMBRE_COLONNE) != null) {
                indexRecherche = index;
                while (indexRecherche < (NOMBRE_LIGNE - 1) * NOMBRE_COLONNE) {
                    if (plateau.getTuile(indexRecherche % NOMBRE_COLONNE, indexRecherche / NOMBRE_COLONNE) != null) {
                        emplacement = rechercherTuileAccessible(indexRecherche);
                        if (emplacement != null) {
                            CoupRetirerTuile coup = new CoupRetirerTuile(
                                    plateau.getTuile(index % NOMBRE_COLONNE, index / NOMBRE_COLONNE),
                                    plateau.getTuile(emplacement.getLigne(), emplacement.getColonne()));
                            if (plateau.verifierCoupJouable(coup)) {
                                coupPossible.add(coup);
                            }
                            indexRecherche = emplacement.getLigne()+ emplacement.getColonne() * 14 ;
                        } else {
                            indexRecherche = 1234;
                        }
                    }
                    else
                        indexRecherche++;
                }
            }
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
                while (j < Direction.values().length && enRecherche) {
                    emplacement = Emplacement.add(
                            new Emplacement(x, y),
                            new Emplacement(
                                    Direction.values()[j].getLigne(),
                                    Direction.values()[j].getColonne())
                    );
                    
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

    public boolean plateauJouable() {
        return coupPossible.size()>0;
    }
    
    /**
     * Renvoie un coup possible sur le plateau
     * @return un coup possible
     */
    public CoupRetirerTuile getUnCoupPossible() {
        CoupRetirerTuile coup = null;
        if (!coupPossible.isEmpty()) {
            Collections.shuffle(coupPossible);
            coup = coupPossible.get(0);
        }
        return coup;
    }
}
