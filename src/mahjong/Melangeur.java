package mahjong;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import static mahjong.Plateau.NOMBRE_COLONNE;
import static mahjong.Plateau.NOMBRE_LIGNE;

/**
 * Classe servant à générer/mélanger le plateau de jeu
 */
public class Melangeur {

    private final long seed;

    public Melangeur(long seed) {
        this.seed = seed;
    }

    /**
     * Génere un nouveau plateau aléatoirement
     *
     * @return plateau généré aléatoirement
     */
    public Tuile[][] genererNouveauPlateau() {
        ArrayList<Emplacement> emplacementPossible = new ArrayList<>();
        ArrayList<Tuile[]> listeDePaires = genererTableDePaireDeTuile();

        for (int indexLigne = 1; indexLigne < Plateau.NOMBRE_LIGNE - 1; indexLigne++) {
            for (int indexColonne = 1; indexColonne < Plateau.NOMBRE_COLONNE - 1; indexColonne++) {
                if (emplacementDansTerrain(indexLigne, indexColonne)) {
                    emplacementPossible.add(new Emplacement(indexLigne, indexColonne));
                }
            }
        }
        return genererPlateau(emplacementPossible, listeDePaires);
    }

    /**
     * Mélange le plateau donné en argument selon la seed et avec les tuiles
     * encore en jeu
     *
     * @param plateau à mélanger
     * @param tuileEnJeu
     * @param seed
     * @return un plateau de même forme que le précedant mais mélangé
     */
    public Object[] melangerPlateau(Tuile[][] plateau, ArrayList<Tuile> tuileEnJeu, long seed) {
        ArrayList<Emplacement> emplacementPossible = genererTableauEmplacementPossible(plateau);
        ArrayList<Tuile[]> listeDePaires = genererTableDePaireDeTuile(tuileEnJeu);

        Collections.shuffle(listeDePaires, new Random(seed));

        return new Object[]{genererPlateau(emplacementPossible, listeDePaires), seed};
    }

    /**
     * Mélange le plateau donné en argument selon une seed aléatoire et avec les
     * tuiles encore en jeu
     *
     * @param plateau à mélanger
     * @param tuileEnJeu
     * @param seed
     * @return un plateau de même forme que le précedant mais mélangé
     */
    public Object[] melangerPlateau(Tuile[][] plateau, ArrayList<Tuile> tuileEnJeu) {
        long seed = new Random().nextLong();
        return melangerPlateau(plateau, tuileEnJeu, seed);
    }

    /**
     * Génère un plateau de tuile aléatoirement
     *
     * @param plateau la réference du plateau à modifier
     * @param emplacementPossible liste restrictive d'emplacement pour les
     * tuiles
     * @param listeDePaires liste de paires à placer
     */
    private Tuile[][] genererPlateau(ArrayList<Emplacement> emplacementPossible, ArrayList<Tuile[]> listeDePaires) {
        Random random = new Random(seed);
        ArrayList<Emplacement> emplacementDisponible = new ArrayList<>();
        Tuile[][] plateau = new Tuile[NOMBRE_LIGNE][NOMBRE_COLONNE];

        Collections.shuffle(listeDePaires, random);
        emplacementDisponible.add(emplacementPossible.remove(random.nextInt(emplacementPossible.size())));
        Emplacement coordonnees;
        while (!listeDePaires.isEmpty()) {
            Tuile[] paire = listeDePaires.remove(0);
            
            //System.out.println("nb possible : "+emplacementPossible.size());
            //System.out.println("nb dispo : "+emplacementDisponible.size());
            //System.out.println("nb paire : "+listeDePaires.size());
            
            coordonnees = emplacementDisponible.remove(random.nextInt(emplacementDisponible.size()));
            plateau[coordonnees.getLigne()][coordonnees.getColonne()] = paire[0];
            plateau[coordonnees.getLigne()][coordonnees.getColonne()].setCoordonnees(coordonnees.getLigne(), coordonnees.getColonne());
            mettreAJourEmplacementDisponible(coordonnees, random, emplacementDisponible, emplacementPossible);

            coordonnees = emplacementDisponible.remove(random.nextInt(emplacementDisponible.size()));
            plateau[coordonnees.getLigne()][coordonnees.getColonne()] = paire[1];
            plateau[coordonnees.getLigne()][coordonnees.getColonne()].setCoordonnees(coordonnees.getLigne(), coordonnees.getColonne());
            mettreAJourEmplacementDisponible(coordonnees, random, emplacementDisponible, emplacementPossible);
            //Plateau.afficherTerrainSurConsole(plateau);
        }

        return plateau;
    }

    /**
     * Génère une liste d'emplacement pouvant acceuilir des tuiles selon la
     * forme d'un plateau existant
     *
     * @param plateau dont la forme est à copier
     * @return une liste d'emplacement possible
     */
    private ArrayList<Emplacement> genererTableauEmplacementPossible(Tuile[][] plateau) {
        ArrayList<Emplacement> emplacementPossible = new ArrayList<>();
        for (int indexLigne = 0; indexLigne < Plateau.NOMBRE_LIGNE; indexLigne++) {
            for (int indexColonne = 0; indexColonne < Plateau.NOMBRE_COLONNE; indexColonne++) {
                if (plateau[indexLigne][indexColonne] != null) {
                    emplacementPossible.add(new Emplacement(indexLigne, indexColonne));
                }
            }
        }
        return emplacementPossible;
    }

    /**
     * Génère une liste de paires contenant uniquement les tuiles encore en jeu
     *
     * @param tuileEnJeu
     * @return une liste de paire de tuiles
     */
    private ArrayList<Tuile[]> genererTableDePaireDeTuile(ArrayList<Tuile> tuileEnJeu) {
        ArrayList<Tuile[]> listeDePaire = new ArrayList<>();
        while (!tuileEnJeu.isEmpty()) {
            Tuile tuile1 = tuileEnJeu.remove(0);
            int indexTuile2 = tuileEnJeu.indexOf(tuile1);
            Tuile tuile2 = tuileEnJeu.remove(indexTuile2);

            listeDePaire.add(new Tuile[]{tuile1, tuile2});
        }
        return listeDePaire;
    }

    /**
     * Génère une liste de paires contenant toutes les tuiles disponibles dans le jeu
     *
     * @return une liste de paire de tuiles
     */
    private ArrayList<Tuile[]> genererTableDePaireDeTuile() {
        ArrayList<Tuile[]> listeDePaire = new ArrayList<>();
        for (FamilleDeTuile famille : FamilleDeTuile.values()) {
            if (famille.estAAppairageStrict()) {
                //Tuile != de fleur ou saison
                for (int idTuile = 0; idTuile < famille.getNombreTuileDifferente(); idTuile++) {
                    for (int i = 0; i < 2; i++) {
                        listeDePaire.add(new Tuile[]{new Tuile(famille, idTuile), new Tuile(famille, idTuile)});
                    }
                }
            } else {
                listeDePaire.add(new Tuile[]{new Tuile(famille, 0), new Tuile(famille, 1)});
                listeDePaire.add(new Tuile[]{new Tuile(famille, 2), new Tuile(famille, 3)});
            }
        }
        return listeDePaire;
    }

    /**
     * Vérifie si une coordonnée est bien sur le terrain
     * @param ligne 
     * @param colonne
     * @return vrai si sur le terrain, faux sinon
     */
    public boolean emplacementDansTerrain(int ligne, int colonne) {
        return ligne >= 1 && colonne >= 1 && ligne < NOMBRE_LIGNE - 1 && colonne < NOMBRE_COLONNE - 1;
    }

    /**
     * Mets à jour les emplacements disponible pour recevoir une tuile.
     * Seul les emplacements contenue dans {emplacementPossible} seront ajoutés.
     * Si à l'issue du place autour d'un point la liste {emplacementDisponible} est vide
     * alors un emplacement aléatoire de {emplacementPossible} sera choisie.
     * @param coordonnee autour de laquelle les nouveaux emplacements seront ajoutés
     * @param random permtet de choisir une coordonnée aléatoire si {emplacementDisponible} est vide
     * @param emplacementDisponible : liste d'emplacement sur lesquels on peux placer une tuile
     * @param emplacementPossible : liste d'emplacement sur lesquels on pourra placer une tuile
     */
    public void mettreAJourEmplacementDisponible(Emplacement coordonnee, Random random,
            ArrayList<Emplacement> emplacementDisponible,
            ArrayList<Emplacement> emplacementPossible) {
        if (!emplacementPossible.isEmpty()) {

            for (CaseAdjacente caseAdjacente : CaseAdjacente.values()) {
                Emplacement nouvelleCoordonnee = new Emplacement(
                        coordonnee.getLigne() + caseAdjacente.getLigne(),
                        coordonnee.getColonne() + caseAdjacente.getColonne());

                if (emplacementPossible.contains(nouvelleCoordonnee) && !emplacementDisponible.contains(nouvelleCoordonnee)) {
                    emplacementDisponible.add(nouvelleCoordonnee);
                    emplacementPossible.remove(nouvelleCoordonnee);
                }
            }
            if (emplacementDisponible.isEmpty()) {
                emplacementDisponible.add(emplacementPossible.remove(random.nextInt(emplacementPossible.size())));
            }
        }
    }
}
