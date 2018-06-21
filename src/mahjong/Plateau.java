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

/**
 * Classe gérant le plateau et les classes travaillant dessus.
 */
public class Plateau {

    //Parametre de base du plateau
    public static final int NOMBRE_LIGNE = 14;
    public static final int NOMBRE_COLONNE = 14;

    //Champ caractérisant le plateau de jeu
    private Tuile[][] plateau;
    private long seed;
    private TypePlateau typeDePlateau;
    private final ArrayList<Coup> coups;
    private final ArrayList<Tuile> tuileEnJeu;

    //Classe intervenant sur le plateau
    private Melangeur melangeur;
    private final Hint verificateurBlocage;
    private final RechercheChemin rechercheChemin;

    //Champ pour utilisation exterieur (GUI)
    private Tuile tuilesSelectionnee;
    private CoupRetirerTuile hint;

    public Plateau() {
        tuilesSelectionnee = null;
        coups = new ArrayList<>();
        tuileEnJeu = new ArrayList<>();
        rechercheChemin = new RechercheChemin(this);
        this.verificateurBlocage = new Hint(this);
        this.plateau = new Tuile[NOMBRE_LIGNE][NOMBRE_COLONNE];
        this.seed = 0;
    }

    /**
     * Génère un nouveau plateau de jeu de manière aléatoire
     *
     * @param seed : graine de génération du terrain
     * @param typeDePlateau : gestion de la "physique" du terrain
     */
    public void genererNouveauPlateau(long seed, TypePlateau typeDePlateau) {
        this.seed = seed;
        melangeur = new Melangeur(seed);
        plateau = melangeur.genererNouveauPlateau();
        regenererListeTuileEnJeu();
        this.typeDePlateau = typeDePlateau;
        verificateurBlocage.regenererListeCoupPossible();
    }

    /**
     * Génère un coup où le plateau est mélangé
     */
    public void melangerPlateau() {
        regenererListeTuileEnJeu();
        ArrayList<Tuile> copieDeTuileEnJeu = new ArrayList<>();
        for (Tuile tuile : tuileEnJeu) {
            copieDeTuileEnJeu.add(tuile.deepCopy());
        }

        Object[] result = melangeur.melangerPlateau(plateau, copieDeTuileEnJeu);
        plateau = (Tuile[][]) result[0];
        coups.add(new CoupMelangerPlateau((long) result[1]));
        verificateurBlocage.regenererListeCoupPossible();
    }

    /**
     * Génère un coup où une tuile sera retiré du plateau
     *
     * @param indexLigne de le tuile
     * @param indexColonne de le tuile
     * @return un coup si celui-ci était jouable, null sinon
     */
    public CoupRetirerTuile creeCoupRetraitTuile(int indexLigne, int indexColonne) {
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

    /**
     * Applique un coup où une tuile sera retiré du plateau
     *
     * @param coup qui sera joué
     * @param coupJoueur indique si c'est l'ordinateur qui effectue le coup ou
     * le joueur
     */
    public void jouerCoupRetrait(CoupRetirerTuile coup, boolean coupJoueur) {
        if (coupJoueur) {
            coups.add(coup);
        }
        tuileEnJeu.remove(coup.getTuiles()[0]);
        tuileEnJeu.remove(coup.getTuiles()[1]);
        plateau[coup.getTuiles()[0].getLigne()][coup.getTuiles()[0].getColonne()] = null;
        plateau[coup.getTuiles()[1].getLigne()][coup.getTuiles()[1].getColonne()] = null;
        typeDePlateau.getPhysiquePlateau().traitementTerrainPostCoup(plateau, coup);
        verificateurBlocage.regenererListeCoupPossible();
    }

    /**
     * Vérifie si les deux tuiles sont apairables et s'il existe un chemin
     * valide entre les deux tuiles selectionnées
     *
     * @param coup à vérifier
     * @return true si le coup est jouable, false sinon
     */
    public boolean verifierCoupJouable(CoupRetirerTuile coup) {
        boolean coupValide = coup.isValid();
        if (coupValide) {
            coupValide = rechercheChemin.rechercheChemin(coup.getTuiles()[0], coup.getTuiles()[1]);
        }
        return coupValide;
    }

    /**
     * Annule le dernier coup joué par le joueur
     *
     * @return le score qu'il faut retirer au joueur
     */
    public int annulerCoup() {
        int score = 0;
        if (!coups.isEmpty()) {
            Coup coup = coups.remove(coups.size() - 1);
            if (coup instanceof CoupRetirerTuile) {
                CoupRetirerTuile coupRetirer = (CoupRetirerTuile) coup;
                typeDePlateau.getPhysiquePlateau().annulerCoup(plateau, coupRetirer);
                tuileEnJeu.add(coupRetirer.getTuiles()[0]);
                tuileEnJeu.add(coupRetirer.getTuiles()[1]);
                score = coupRetirer.getScore();
            } else {
                plateau = melangeur.genererNouveauPlateau();
                regenererListeTuileEnJeu();
                for (int i = 0; i < coups.size(); i++) {
                    rejouerAncienCoup(coups.get(i));
                }

            }
        }
        verificateurBlocage.regenererListeCoupPossible();
        return score;
    }

    /**
     * Rejoue un coup
     *
     * @param coup à rejouer
     */
    public void rejouerAncienCoup(Coup coup) {
        if (coup instanceof CoupRetirerTuile) {
            jouerCoupRetrait((CoupRetirerTuile) coup, false);
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

    /**
     * Sauvegarde le plateau (type, seed, plateau, liste de coup)
     * @param fichier dans lequel il faut sauvegarder
     * @throws IOException
     */
    public void save(FileWriter fichier) throws IOException {
        fichier.write(typeDePlateau.toString() + "\n");
        fichier.write(this.seed + "\n");
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
    /**
     * Charge un plateau
     * @param fichier dans lequel est la sauvegarde du plateau
     * @throws IOException
     */
    public void charger(BufferedReader fichier) throws IOException {
        this.plateau = new Tuile[NOMBRE_LIGNE][NOMBRE_COLONNE];
        this.melangeur = new Melangeur(seed);
        typeDePlateau = TypePlateau.valueOf(fichier.readLine());
        this.seed = Long.parseLong(fichier.readLine());
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
        regenererListeTuileEnJeu();
        verificateurBlocage.regenererListeCoupPossible();
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

    /**
     * Actualise la liste de tuile en jeu. Sert à savoir quand la partie se
     * termine et au mélange du plateau.
     */
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

    /**
     * Recherche une tuile par ça position
     * @param indexLigne de la tuile
     * @param indexColonne de la tuile
     * @return une tuile ou null si la position est vide ou hors plateau
     */
    public Tuile getTuile(int indexLigne, int indexColonne) {
        Tuile tuile = null;
        if ((0 <= indexLigne && indexLigne < this.NOMBRE_COLONNE)
                && (0 <= indexColonne && indexColonne < this.NOMBRE_LIGNE)) {
            tuile = plateau[indexLigne][indexColonne];
        }
        return tuile;
    }
    
    public void afficherHint() {
        hint = verificateurBlocage.getUnCoupPossible();
    }
    
    public void effacerHint() {
        this.hint = null;
    }

    public CoupRetirerTuile getHint() {
        return hint;
    }
    
    public boolean partieTerminee() {
        return tuileEnJeu.isEmpty();
    }
    
    public boolean plateauJouable() {
        return verificateurBlocage.plateauJouable();
    }
    
    public void deselectionnerTuile() {
        this.tuilesSelectionnee = null;
    }
    
    public Tuile getTuilesSelectionnee() {
        return tuilesSelectionnee;
    }

    public ArrayList<Coup> getCoups() {
        return coups;
    }

    public CaseRecherchee getCheminLiaisonTuiles() {
        return rechercheChemin.getCheminEnCours();
    }

    public long getSeed() {
        return seed;
    }
    
    public TypePlateau getTypeDePlateau() {
        return typeDePlateau;
    }

}
