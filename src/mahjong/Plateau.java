package mahjong;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import mahjong.partie.Partie;

public class Plateau {

    private Tuile[][] plateau;
    public static final int NOMBRE_LIGNE = 14;
    public static final int NOMBRE_COLONNE = 14;
    private final int NOMBRE_TUILE_PAR_LIGNE = 12;
    private final int NOMBRE_TUILE_PAR_COLONNE = 12;
    private TypePlateau typeDePlateau;
    private Tuile tuilesSelectionnee;
    private final ArrayList<Coup> coups;
    private Partie partie;

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
        
        Random random = new Random(seed);
        ArrayList<Tuile[]> listeDePaires = genererTableDePaireDeTuile();
        Collections.shuffle(listeDePaires, random);

        ArrayList<int[]> emplacementPossible = new ArrayList<>();
        emplacementPossible.add(new int[]{
            1 + (int) (random.nextDouble() * NOMBRE_TUILE_PAR_LIGNE),
            1 + (int) (random.nextDouble() * NOMBRE_TUILE_PAR_COLONNE)});
        plateau = new Tuile[NOMBRE_LIGNE][NOMBRE_COLONNE];

        while (listeDePaires.size() > 0) {
            Tuile[] paires = listeDePaires.remove(0);
            ajouterTuile(emplacementPossible, null,
                    (int) (random.nextDouble() * emplacementPossible.size()),
                    paires[0]);

            ajouterTuile(emplacementPossible, null,
                    (int) (random.nextDouble() * emplacementPossible.size()),
                    paires[1]);
        }
        this.typeDePlateau = typeDePlateau;
    }

    public void ajouterTuile(ArrayList<int[]> emplacementPossible, ArrayList<int[]> listeRestrictive, int index, Tuile tuile) {
        int ligneTuile = emplacementPossible.get(index)[0];
        int colonneTuile = emplacementPossible.get(index)[1];

        plateau[ligneTuile][colonneTuile] = tuile;
        plateau[ligneTuile][colonneTuile].setCoordonnees(ligneTuile, colonneTuile);

        emplacementPossible.remove(index);
        for (CaseAdjacente emplacementRelatif : CaseAdjacente.values()) {
            if (emplacementDansTerrain(ligneTuile + emplacementRelatif.getX(), colonneTuile + emplacementRelatif.getY())) {
                
                int[] nouvelleEmplacement = new int[]{
                    ligneTuile + emplacementRelatif.getX(),
                    colonneTuile + emplacementRelatif.getY()};
                
                if ( plateau[nouvelleEmplacement[0]][nouvelleEmplacement[1]] == null 
                        && !emplacementInclus(emplacementPossible, nouvelleEmplacement)) {
                    if (listeRestrictive == null) {
                        emplacementPossible.add(nouvelleEmplacement);
                    } else {
                        if(emplacementInclus(listeRestrictive, nouvelleEmplacement)) {
                            emplacementPossible.add(nouvelleEmplacement);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * @param ligne
     * @param colonne
     * @return Retourne vrai si les coordonnees sont incluse dans le terrain, faux sinon
     */
    private boolean emplacementDansTerrain(int ligne, int colonne) {
        return ligne >= 1 && colonne >= 1 && ligne < NOMBRE_LIGNE - 1 && colonne < NOMBRE_COLONNE - 1;
    }

    /**
     * @param emplacementPossible
     * @param nouvelleEmplacement
     * @return renvoie vrai si {@param nouvelleEmplacement} est dans {@param emplacementPossible}, faux sinon
     */
    public boolean emplacementInclus(ArrayList<int[]> emplacementPossible, int[] nouvelleEmplacement) {
        boolean emplacementTrouver = false;
        int i = 0;
        while(!emplacementTrouver && i < emplacementPossible.size()) {
            emplacementTrouver
                    = emplacementPossible.get(i)[0] == nouvelleEmplacement[0]
                    && emplacementPossible.get(i)[1] == nouvelleEmplacement[1];
            i++;
        }
        return emplacementTrouver;
    }

    /**
     * Genere une liste de tuilles apairées afin d'etre placer sur le plateau
     *
     * @return liste de tuile apairés
     */
    public ArrayList<Tuile[]> genererTableDePaireDeTuile() {
        ArrayList<Tuile[]> listeDePaire = new ArrayList<>();
        for (FamilleDeTuile famille : FamilleDeTuile.values()) {
            if (famille.getNombrePairesTuile() != 0) {
                //Tuile != de fleur ou saison
                for (int idTuile = 0; idTuile < famille.getNombreTuileDifferente(); idTuile++) {
                    for (int i = 0; i < famille.getNombrePairesTuile(); i++) {
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
                Coup coup = new Coup(new Tuile[]{tuilesSelectionnee, tuile});
                if (verifierCoupJouable(coup)) {

                    //On retire les references des objets de la selection et du plateau
                    tuilesSelectionnee = null;
                    plateau[coup.getTuiles()[0].getCoordonnees()[0]][coup.getTuiles()[0].getCoordonnees()[1]] = null;
                    plateau[coup.getTuiles()[1].getCoordonnees()[0]][coup.getTuiles()[1].getCoordonnees()[1]] = null;

                    coups.add(coup);
                    partie.resetChrono();

                    typeDePlateau.traitementTerrainPostCoup(plateau, coup);
                }
            }
        }
    }

    /**
     * Verifier si un chemin valide est trouver entre les tuiles selectionnee
     *
     * @return vrai si le coup est jouable, faux sinon
     */
    public boolean verifierCoupJouable(Coup coup) {
        boolean coupValide = coup.isValid();
        if (coupValide) {
            //verifier le pathFinding
        }
        return coupValide;
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
                    System.out.print("   ");
                }
            }
            System.out.println();
        }

    }

    public void melangerPlateau() {     //Non fonctionnel
        ArrayList<Tuile[]> listeDePaires = new ArrayList<>();
        ArrayList<int[]> emplacementLibre = new ArrayList<>();
        int i = 0;
        int nombreTuileEnJeu = 144 - 2 * coups.size();
        while (nombreTuileEnJeu > 0) {
            if (plateau[i % NOMBRE_COLONNE][i / NOMBRE_COLONNE] != null) {
                int[] coordonneeTuileAppaire = rechercherTuile(i, plateau[i % NOMBRE_COLONNE][i / NOMBRE_COLONNE]);
                listeDePaires.add(new Tuile[]{
                    plateau[i % NOMBRE_COLONNE][i / NOMBRE_COLONNE],
                    plateau[coordonneeTuileAppaire[0]][coordonneeTuileAppaire[1]]
                });
                plateau[i % NOMBRE_COLONNE][i / NOMBRE_COLONNE] = null;
                plateau[coordonneeTuileAppaire[0]][coordonneeTuileAppaire[1]] = null;

                nombreTuileEnJeu -= 2;

                emplacementLibre.add(new int[]{i % NOMBRE_COLONNE, i / NOMBRE_COLONNE});
                emplacementLibre.add(coordonneeTuileAppaire);
            }
            i++;
        }

        //!\ Ne prend pas en compte le path finding, revoir en le rajoutant pour crée une solution jouable
        Random random = new Random(0);
        Collections.shuffle(listeDePaires, random);
        Collections.shuffle(emplacementLibre, random);

        plateau = new Tuile[NOMBRE_LIGNE][NOMBRE_COLONNE];
        ArrayList<int[]> emplacementPossible = new ArrayList<>();
        regenererEmplacementPossible(emplacementLibre, emplacementPossible);

        while (listeDePaires.size() > 0) {
            Tuile[] paires = listeDePaires.remove(0);
            ajouterTuile(emplacementPossible, emplacementLibre,
                    (int) (random.nextDouble() * emplacementPossible.size()),
                    paires[0]);

            if (emplacementPossible.isEmpty()) {
                regenererEmplacementPossible(emplacementLibre, emplacementPossible);
            }

            ajouterTuile(emplacementPossible, emplacementLibre,
                    (int) (random.nextDouble() * emplacementPossible.size()),
                    paires[1]);

            if (emplacementPossible.isEmpty()) {
                regenererEmplacementPossible(emplacementLibre, emplacementPossible);
            }
        }
    }

    private void regenererEmplacementPossible(ArrayList<int[]> emplacementLibre, ArrayList<int[]> emplacementPossible) {
        emplacementPossible.add(emplacementLibre.remove(0));
    }

    public int[] rechercherTuile(int indexDeBase, Tuile tuile) {
        int[] solution = null;
        final int indexMax = NOMBRE_LIGNE * NOMBRE_COLONNE;
        int i = indexDeBase + 1;
        boolean enRecherche = true;
        while (i < indexMax && enRecherche) {
            if (tuile.equals(plateau[i % NOMBRE_COLONNE][i / NOMBRE_COLONNE])) {
                enRecherche = false;
                solution = new int[]{i % NOMBRE_COLONNE, i / NOMBRE_COLONNE};
            }
            i++;
        }
        return solution;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }
}
