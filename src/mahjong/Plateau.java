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

public class Plateau {

    public static final int NOMBRE_LIGNE = 14;
    public static final int NOMBRE_COLONNE = 14;

    private Tuile[][] plateau;
    private TypePlateau typeDePlateau;
    private final ArrayList<Coup> coups;
    private final ArrayList<Tuile> tuileEnJeu;

    private Tuile tuilesSelectionnee;
    private final RechercheChemin rechercheChemin;
    private Melangeur melangeur;

    public Plateau() {
        tuilesSelectionnee = null;
        coups = new ArrayList<>();
        tuileEnJeu = new ArrayList<>();
        rechercheChemin = new RechercheChemin(this);
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

    public CoupRetirerTuile genererCoup(int indexLigne, int indexColonne) {
        CoupRetirerTuile coup = null;
        if (tuilesSelectionnee == null) {
            tuilesSelectionnee = getTuile(indexLigne, indexColonne);
        } else if (tuilesSelectionnee == plateau[indexLigne][indexColonne]) {
            tuilesSelectionnee = null;
        } else {
            Tuile tuile = getTuile(indexLigne, indexColonne);
            if (tuile != null) {
                coup = new CoupRetirerTuile(tuilesSelectionnee, tuile);
                if (!verifierCoupJouable(coup)) {
                    coup = null;
                } else {
                    tuilesSelectionnee = null;
                }
            }
        }
        return coup;
    }

    public void jouerCoup(CoupRetirerTuile coup, boolean regenerationTerrain) {
        if (!regenerationTerrain) {
            coups.add(coup);
        }
        tuileEnJeu.remove(coup.getTuiles()[0]);
        tuileEnJeu.remove(coup.getTuiles()[1]);
        plateau[coup.getTuiles()[0].getLigne()][coup.getTuiles()[0].getColonne()] = null;
        plateau[coup.getTuiles()[1].getLigne()][coup.getTuiles()[1].getColonne()] = null;
        typeDePlateau.getPhysiquePlateau().traitementTerrainPostCoup(plateau, coup);
    }

    /**
     * Verifier si un chemin valide est trouver entre les tuiles selectionnee
     *
     * @param coup
     * @return vrai si le coup est jouable, faux sinon
     */
    public boolean verifierCoupJouable(CoupRetirerTuile coup) {
        boolean coupValide = coup.isValid();
//        System.out.println("====");
//        System.out.println(coup.getTuiles()[0] + " : " + coup.getTuiles()[0].getCoordonnees());
//        System.out.println(coup.getTuiles()[1] + " : " + coup.getTuiles()[1].getCoordonnees());
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
        //regenererListeTuileEnJeu();
        ArrayList<Tuile> copieDeTuileEnJeu = new ArrayList<>();
        for (Tuile tuile : tuileEnJeu) {
            copieDeTuileEnJeu.add(tuile.deepCopy());
        }

        Object[] result = melangeur.melangerPlateau(plateau, copieDeTuileEnJeu);
        plateau = (Tuile[][]) result[0];
        coups.add(new CoupMelangerPlateau((long) result[1]));
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

    public void appliquerCoup(Coup coup) {
        if (coup instanceof CoupRetirerTuile) {
            jouerCoup((CoupRetirerTuile) coup, true);
        } else {
            ArrayList<Tuile> copieDeTuileEnJeu = new ArrayList<>();
            for (Tuile tuile : tuileEnJeu) {
                copieDeTuileEnJeu.add(tuile.deepCopy());
            }
            CoupMelangerPlateau coupMelange = (CoupMelangerPlateau) coup;
            Object[] result = melangeur.melangerPlateau(plateau, copieDeTuileEnJeu, coupMelange.getSeed());
            plateau = (Tuile[][]) result[0];
        }
    }

    public int annulerCoup() {
        int score = 0;
        if (!coups.isEmpty()) {
            Coup coup = coups.remove(coups.size() - 1);
            if (coup instanceof CoupRetirerTuile) {
                CoupRetirerTuile coupRetirer = (CoupRetirerTuile) coup;
                typeDePlateau.getPhysiquePlateau().annulerCoup(plateau, coupRetirer);
                tuileEnJeu.add(coupRetirer.getTuiles()[0]);
                tuileEnJeu.add(coupRetirer.getTuiles()[1]);
                score = coupRetirer.getScore() - 10;
            } else {
                plateau = melangeur.genererNouveauPlateau();
                regenererListeTuileEnJeu();
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
        for(Coup coup : coups) {
            savePlateau += coup.save() + ";";
        }
        fichier.write(savePlateau.substring(0, savePlateau.length() - 1));
    }

    public void charger(BufferedReader fichier) throws IOException {
        this.plateau = new Tuile[NOMBRE_LIGNE][NOMBRE_COLONNE];
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
        regenererListeTuileEnJeu();
        String sauvegardeCoups[] = fichier.readLine().split(";");
        for (String sauvegarde : sauvegardeCoups) {

            this.coups.add(Coup.loadSave(sauvegarde));
        }
    }

    /**
     * Affiche le plateau directement dans la console
     *
     * @param plateau
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
        tuileEnJeu.clear();
        for (int indexLigne = 1; indexLigne < NOMBRE_LIGNE - 1; indexLigne++) {
            for (int indexColonne = 1; indexColonne < NOMBRE_COLONNE - 1; indexColonne++) {
                Tuile tuile = plateau[indexLigne][indexColonne];
                if (tuile != null) {
                    tuileEnJeu.add(tuile);
                }
            }
        }
    }

    public TypePlateau getTypeDePlateau() {
        return typeDePlateau;
    }

    public CaseRecherchee getCheminLiaisonTuiles() {
        return rechercheChemin.getCheminEnCours();
    }

    public void deselectionnerTuile() {
        this.tuilesSelectionnee = null;
    }
}
