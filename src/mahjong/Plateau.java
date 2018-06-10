package mahjong;

import mahjong.coup.CoupRetirerTuile;
import mahjong.Type_Plateau.TypePlateau;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import mahjong.PathFinder.CaseRecherchee;
import mahjong.PathFinder.RechercheChemin;
import mahjong.coup.Coup;
import mahjong.coup.CoupMelangerPlateau;
import mahjong.partie.Partie;

public class Plateau {

    private Tuile[][] plateau;
    public static final int NOMBRE_LIGNE = 14;
    public static final int NOMBRE_COLONNE = 14;
    private TypePlateau typeDePlateau;
    private Tuile tuilesSelectionnee;
    private final ArrayList<Coup> coups;
    private final ArrayList<Tuile> tuileEnJeu;
    private Partie partie;
    private final RechercheChemin rechercheChemin;
    private boolean afficherChemin;
    private Melangeur melangeur;

    public Plateau() {
        tuilesSelectionnee = null;
        plateau = new Tuile[NOMBRE_LIGNE][NOMBRE_COLONNE];
        coups = new ArrayList<>();
        tuileEnJeu = new ArrayList<>();
        rechercheChemin = new RechercheChemin(this);
        afficherChemin = false;

    }

    /**
     * Genere un nouveau plateau de jeu de manière aléatoire
     *
     * @param seed : graine de génération du terrain
     * @param typeDePlateau : gestion de la "physique" du terrain
     */
    public void genererNouveauPlateau(long seed, TypePlateau typeDePlateau) {

        melangeur = new Melangeur(seed);
        plateau = melangeur.genererNouveauPlateau();
        regenererListeTuileEnJeu();
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
                CoupRetirerTuile coup = new CoupRetirerTuile(new Tuile[]{tuilesSelectionnee, tuile});
                if (verifierCoupJouable(coup)) {

                    //On retire les references des objets de la selection et du plateau
                    tuilesSelectionnee = null;
                    tuileEnJeu.remove(coup.getTuiles()[0]);
                    tuileEnJeu.remove(coup.getTuiles()[1]);
                    coups.add(coup);
                    //XXX POUR LES TESTS
                    if (partie != null) {
                        coup.setScore(partie.resetChrono());
                    }
                    this.partie.getInterfaceDeJeu().bloquerPlateau(true);

                    afficherChemin = true;

                }
            }
        }
    }

    /**
     * Verifier si un chemin valide est trouver entre les tuiles selectionnee
     *
     * @param coup
     * @return vrai si le coup est jouable, faux sinon
     */
    public boolean verifierCoupJouable(CoupRetirerTuile coup) {
        boolean coupValide = coup.isValid();
        if (coupValide) {
            coupValide = rechercheChemin.rechercheChemin(coup.getTuiles()[0], coup.getTuiles()[1]);
        }
        return coupValide;
    }

    public boolean partieGagnee() {
        return tuileEnJeu.isEmpty();
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

    public void melangerPlateau() {
        ArrayList<Tuile> copieDeTuileEnJeu = new ArrayList<>();
        for(Tuile tuile : tuileEnJeu)
        {
            copieDeTuileEnJeu.add(tuile.deepCopy());
        }
        
        Object[] result = melangeur.melangerPlateau(plateau, copieDeTuileEnJeu);
        plateau = (Tuile[][]) result[0];
        coups.add(new CoupMelangerPlateau((long)result[1]));
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

    public CaseRecherchee getCheminTuile() {
        return afficherChemin ? rechercheChemin.getCheminEnCours() : null;
    }

    public void setAfficherChemin(boolean afficherChemin) {
        this.partie.getInterfaceDeJeu().bloquerPlateau(false);
        appliquerCoup();
        this.afficherChemin = afficherChemin;
    }

    public void appliquerCoup(Coup coup) {
        if (coup instanceof CoupRetirerTuile) {
            CoupRetirerTuile coupRetrait = (CoupRetirerTuile) coup;
            System.out.println(coupRetrait.save());
            plateau[coupRetrait.getTuiles()[0].getCoordonnees()[0]][coupRetrait.getTuiles()[0].getCoordonnees()[1]] = null;
            plateau[coupRetrait.getTuiles()[1].getCoordonnees()[0]][coupRetrait.getTuiles()[1].getCoordonnees()[1]] = null;
            typeDePlateau.getPhysiquePlateau().traitementTerrainPostCoup(plateau, coupRetrait);
        } else {
            regenererListeTuileEnJeu();
            CoupMelangerPlateau coupMelange = (CoupMelangerPlateau)coup;
            melangeur.melangerPlateau(plateau, tuileEnJeu, coupMelange.getSeed());
        }
    }

    public void appliquerCoup() {
        if (!coups.isEmpty() && coups.get(coups.size() - 1) instanceof CoupRetirerTuile) {
            CoupRetirerTuile coup = (CoupRetirerTuile) coups.get(coups.size() - 1);
            plateau[coup.getTuiles()[0].getCoordonnees()[0]][coup.getTuiles()[0].getCoordonnees()[1]] = null;
            plateau[coup.getTuiles()[1].getCoordonnees()[0]][coup.getTuiles()[1].getCoordonnees()[1]] = null;
            typeDePlateau.getPhysiquePlateau().traitementTerrainPostCoup(plateau, coup);
            System.out.println(coup.save());        
        }
    }

    public int retourCoup() {
        int score = 0;
        if (!coups.isEmpty() && !afficherChemin) {
            Coup coup = coups.remove(coups.size() - 1);
            if (coup instanceof CoupRetirerTuile) {
                CoupRetirerTuile coupRetirer = (CoupRetirerTuile) coup;
                typeDePlateau.getPhysiquePlateau().remonterCoup(plateau, coupRetirer);
                score = coupRetirer.getScore();
            } else {
                plateau = melangeur.genererNouveauPlateau();
                for (int i = 0; i < coups.size(); i++) {
                    appliquerCoup(coups.get(i));
                }

            }
        }
        return score;
    }

    public ArrayList<Coup> getCoups() {
        return coups;
    }

    public void save(FileWriter fichier) throws IOException {
        fichier.write(typeDePlateau.toString() + "\n");
        String savePlateau = "";
        for (int indexLigne = 0; indexLigne < NOMBRE_LIGNE; indexLigne++) {
            for (int indexColonne = 0; indexColonne < NOMBRE_COLONNE; indexColonne++) {
                if (plateau[indexLigne][indexColonne] != null) {
                    savePlateau += plateau[indexLigne][indexColonne].save();
                }
                savePlateau += ":";
            }
            savePlateau += ";";
        }
        fichier.write(savePlateau + "\n");
        savePlateau = "";
        for (Coup coup : coups) {
            savePlateau += coup.save() + ";";
        }
        fichier.write(savePlateau.substring(0, savePlateau.length() - 1));
    }

    public void charger(BufferedReader fichier) throws IOException {
        typeDePlateau = TypePlateau.valueOf(fichier.readLine());
        String lignes[] = fichier.readLine().split(";");
        for (int indexLigne = 0; indexLigne < NOMBRE_LIGNE; indexLigne++) {
            String colonnes[] = lignes[indexLigne].split(":");

            for (int indexColonne = 0; indexColonne < NOMBRE_COLONNE; indexColonne++) {
                if (indexColonne < colonnes.length && !colonnes[indexColonne].equals("")) {
                    plateau[indexLigne][indexColonne] = new Tuile(colonnes[indexColonne]);
                }
            }
        }

        String sauvegardeCoups[] = fichier.readLine().split(";");
        for (String sauvegarde : sauvegardeCoups) {

            this.coups.add(Coup.loadSave(sauvegarde));
        }
    }

    /**
     * Affiche le plateau directement dans la console
     */
    public static void afficherTerrainSurConsole(Tuile[][] plateau) {
        for (int indexLigne = -1; indexLigne < NOMBRE_LIGNE + 1; indexLigne++) {
            for (int indexColonne = -1; indexColonne < NOMBRE_COLONNE + 1; indexColonne++) {
                if (indexLigne == -1 || indexLigne == NOMBRE_LIGNE) {
                    if (indexColonne == -1) {
                        System.out.print("   ");
                    }
                    if (indexColonne >= 0 && indexColonne < 10) {
                        System.out.print(" " + indexColonne + " ");
                    } else if (indexColonne >= 10 && indexColonne < 14) {
                        System.out.print(indexColonne + " ");
                    }
                } else if (indexColonne == -1 || indexColonne == NOMBRE_COLONNE) {
                    if (indexLigne >= 0 && indexLigne < 10) {
                        System.out.print(" " + indexLigne + " ");
                    } else if (indexLigne >= 10) {
                        System.out.print(indexLigne + " ");
                    }

                } else {
                    if (plateau[indexLigne][indexColonne] != null) {
                        System.out.print(plateau[indexLigne][indexColonne].toString() + " ");
                    } else {
                        System.out.print("   ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println("==");
    }

    private void regenererListeTuileEnJeu() {
        for (int indexLigne = 1; indexLigne < NOMBRE_LIGNE - 1; indexLigne++) {
            for (int indexColonne = 1; indexColonne < NOMBRE_COLONNE - 1; indexColonne++) {
                Tuile tuile = plateau[indexLigne][indexColonne];
                if (tuile != null) {
                    tuileEnJeu.add(tuile);
                }
            }
        }
    }
}
