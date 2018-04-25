package mahjong;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Plateau {

    private Tuile[][] plateau;
    private final int NOMBRE_LIGNE = 12;
    private final int NOMBRE_COLONNE = 12;
    private TypePlateau typeDePlateau;
    private Tuile tuilesSelectionnee;
    private final ArrayList<Coup> coups;

    public Plateau() {
        tuilesSelectionnee = null;
        plateau = new Tuile[NOMBRE_LIGNE][NOMBRE_COLONNE];
        coups = new ArrayList<>();
    }

    /**
     * Genere un nouveau plateau de jeu de manière aléatoire
     *
     * @param seed : graine de génération du terrain
     * @param typeDePlateau : gestion de la "physique" du terrain
     */
    public void genererNouveauPlateau(long seed, TypePlateau typeDePlateau) {
        ArrayList<Tuile> tuiles = new ArrayList<>();
        for (FamilleDeTuile famille : FamilleDeTuile.values()) {
            for (int j = 0; j < famille.getNombreTuileDifferente(); j++) {
                for (int i = 0; i < famille.getNombreExemplaireTuile(); i++) {
                    tuiles.add(new Tuile(famille.toString().substring(0, 1) + (1 + j)));
                }
            }
        }
        Collections.shuffle(tuiles, new Random(seed));
        plateau = new Tuile[NOMBRE_LIGNE][NOMBRE_COLONNE];
        for (int indexLigne = 0; indexLigne < 12; indexLigne++) {
            for (int indexColonne = 0; indexColonne < 12; indexColonne++) {
                plateau[indexLigne][indexColonne] = tuiles.remove(0);
                plateau[indexLigne][indexColonne].setCoordonnees(indexLigne, indexColonne);
            }
        }
        this.typeDePlateau = typeDePlateau;
    }

    /**
     * Selectionne une tuile et tente de jouer un coup
     *
     * @param indexLigne : index de la tuile sur une ligne
     * @param indexColonne : index de la ligne sur une colonne
     */
    public void jouer(int indexLigne, int indexColonne) {
        if (tuilesSelectionnee == null) {
            tuilesSelectionnee = getTuile(indexLigne, indexColonne);
        } else if (tuilesSelectionnee == plateau[indexLigne][indexColonne]) {
            tuilesSelectionnee = null;
        } else {
            Tuile tuile = getTuile(indexLigne, indexColonne);
            if (tuile != null) {
                if (verifierCoupJouable()) {
                    Coup coup = new Coup(new Tuile[]{tuilesSelectionnee,tuile});
                    //On retire les references des objets de la selection et du plateau
                    tuilesSelectionnee = null;
                    plateau[coup.getTuiles()[0].getCoordonnees()[0]][coup.getTuiles()[0].getCoordonnees()[1]] = null;
                    plateau[coup.getTuiles()[1].getCoordonnees()[0]][coup.getTuiles()[1].getCoordonnees()[1]] = null;

                    coups.add(coup);

                    typeDePlateau.traitementTerrainPostCoup(plateau);
                }
            }
        }
    }

    /**
     * Verifier si un chemin valide est trouver entre les tuiles selectionnee
     *
     * @return vrai si le coup est jouable, faux sinon
     */
    public boolean verifierCoupJouable() {
        //TODO verifier via le path finding (Antoine)
        return true;
    }

    public boolean partieGagnee() {
        return 144 - 2 * coups.size() <= 0;
    }

    public Tuile getTuilesSelectionnee() {
        return tuilesSelectionnee;
    }

    public Tuile getTuile(int indexLigne, int indexColonne) {
        Tuile tuile = null;
        if ((0 <= indexLigne && indexLigne < this.NOMBRE_COLONNE)
                && (0 <= indexColonne && indexColonne < this.NOMBRE_LIGNE)) {
            tuile = plateau[indexLigne][indexColonne];
        }
        return tuile;
    }

    /**
     * Affiche le plateau directement dans la console
     */
    public void afficherTerrainSurConsole() {
        System.out.println("Mahjong");
        for (int indexLigne = 0; indexLigne < NOMBRE_LIGNE; indexLigne++) {
            for (int indexColonne = 0; indexColonne < NOMBRE_COLONNE; indexColonne++) {
                if (plateau[indexLigne][indexColonne] != null) {
                    System.out.print(plateau[indexLigne][indexColonne].toString() + " ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }

    }
}
