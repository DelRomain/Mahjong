package mahjong.GestionPlateau;

import mahjong.RechercheChemin.Direction;
import java.util.ArrayList;
import java.util.Collections;
import mahjong.GestionTuile.Coordonnees;
import mahjong.RechercheChemin.RechercheChemin;
import mahjong.GestionTuile.Tuile;
import static mahjong.GestionPlateau.Plateau.NOMBRE_COLONNE;
import static mahjong.GestionPlateau.Plateau.NOMBRE_LIGNE;
import mahjong.TypeDeCoup.CoupRetirerTuile;

/**
 * Classe gérant la recherche de coup possible.
 * Elle permet de générer des indications de coups possibles et de vérifier si le plateau n'est pas bloqué.
 */
public class Hint {

    private final ArrayList<CoupRetirerTuile> coupPossible;
    private final Plateau plateau;

    public Hint(Plateau plateau) {
        coupPossible = new ArrayList<>();
        this.plateau = plateau;
    }

    /**
     * Fonction regénérant la liste de coup possible sur le plateau.
     */
    public void regenererListeCoupPossible() {
        coupPossible.clear();
        Coordonnees emplacement;
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
    public Coordonnees rechercherTuileAccessible(int index) {
        Coordonnees solution = null;
        final int indexMax = NOMBRE_LIGNE * NOMBRE_COLONNE;
        Tuile tuile = plateau.getTuile(index % NOMBRE_COLONNE, index / NOMBRE_COLONNE);
        int i = index + 1;
        boolean enRecherche = true;
        Coordonnees emplacement;
        int x, y, j;
        Tuile tuile2;
        while (i < indexMax && enRecherche) {
            x = i % NOMBRE_COLONNE;
            y = i / NOMBRE_COLONNE;
            if (tuile.equals(plateau.getTuile(x, y))) {
                j = 0;
                while (j < Direction.values().length && enRecherche) {
                    emplacement = Coordonnees.getEmplacementAdjacent(
                            new Coordonnees(x, y),
                            Direction.values()[j]
                    );
                    
                    tuile2 = plateau.getTuile(emplacement.getLigne(), emplacement.getColonne());
                    if (tuile2 == null || tuile2.equals(tuile)) {
                        enRecherche = false;
                        solution = new Coordonnees(x, y);
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
