package mahjong.GestionJoueur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mahjong.GestionPartie.Partie;

/**
 * Classe gérant la liste de joueurs, le joueurs qui joue et la sauvegarde et le
 * chargement des joueurs
 */
public class GestionnaireJoueur {

    private final ArrayList<Joueur> listeJoueurs;
    private Joueur joueurActuel;

    public GestionnaireJoueur() {
        this.listeJoueurs = new ArrayList<>();
    }

    public ArrayList<Joueur> getListeJoueurs() {
        return listeJoueurs;
    }

    public Joueur getJoueur() {
        return joueurActuel;
    }

    /**
     * Change le joueur qui jouera les parties
     *
     * @param joueur qui jouera les parties
     */
    public void setJoueur(Joueur joueur) {
        if (!listeJoueurs.contains(joueur)) {
            listeJoueurs.add(joueur);
        } else {
            joueur = listeJoueurs.get(listeJoueurs.indexOf(joueur));
        }
        this.joueurActuel = joueur;
    }

    /**
     * Ajoute une victoire à un joueur
     *
     * @param partie qui vient d'être gagner
     */
    public void ajouterPartieAuJoueurCourant(Partie partie) {
        joueurActuel.ajouterUnePartie(partie);
        sauvegarderJoueur(joueurActuel);
    }

    /**
     * Ajoute un joueur à la liste de joueur selectionable pour jouer
     *
     * @param joueur à ajouter
     * @return vrai si le joueur a été ajouté, faux sinon
     */
    public boolean add(Joueur joueur) {
        boolean nouveauNom = false;
        if (!listeJoueurs.contains(joueur)) {
            listeJoueurs.add(joueur);
            nouveauNom = true;
        }
        if (joueurActuel == null) {
            joueurActuel = joueur;
        }
        return nouveauNom;
    }

    /**
     * Retire un joueur de la liste des joueurs selectionnables pour jouer
     *
     * @param joueur à supprimer
     */
    public void supprimerJoueur(Joueur joueur) {
        listeJoueurs.remove(joueur);
        new File("joueurs/" + joueur.getNom() + ".jur").delete();
        if (joueur.equals(joueurActuel)) {
            new File("joueurs/default.djur").delete();
            if (listeJoueurs.isEmpty()) {
                joueurActuel = null;
            } else {
                joueurActuel = listeJoueurs.get(0);
            }
        }
    }

    /**
     * Sauvegarde la liste de tout les joueurs ainsi que le joueur qui joue
     * actuellement les parties
     */
    public void sauvegarderJoueurs() {
        listeJoueurs.forEach((joueur) -> {
            sauvegarderJoueur(joueur);
        });
        if (joueurActuel != null) {
            FileWriter fichier;
            try {
                fichier = new FileWriter("joueurs/default.djur");
                fichier.write(joueurActuel.getNom());
                fichier.close();
            } catch (IOException ex) {
                Logger.getLogger(GestionnaireJoueur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Sauvegarde le joueur en parametre
     *
     * @param joueur
     */
    private void sauvegarderJoueur(Joueur joueur) {
        FileWriter fichier;
        try {
            fichier = new FileWriter("joueurs/" + joueur.getNom() + ".jur");
            joueur.save(fichier);
            fichier.close();
        } catch (IOException ex) {
            Logger.getLogger(GestionnaireJoueur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Charge la liste de joueurs ainsi que le joueur qui jouera les parties
     */
    public void chargerJoueurs() {
        listeJoueurs.clear();
        File repertoire = new File("joueurs/");
        File[] files = repertoire.listFiles();
        String joueurParDefault = "";
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.getName().endsWith("jur")) {
                BufferedReader fichier;
                try {
                    fichier = new BufferedReader(new FileReader(file));
                    while (fichier.ready()) {
                        if (file.getName().equals("default.djur")) {
                            joueurParDefault = fichier.readLine();
                        } else {
                            Joueur joueur = new Joueur();
                            joueur.charger(fichier);
                            listeJoueurs.add(joueur);
                        }
                    }
                    fichier.close();
                } catch (IOException ex) {
                    Logger.getLogger(GestionnaireJoueur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        int i = listeJoueurs.indexOf(new Joueur(joueurParDefault));
        if (i != -1) {
            joueurActuel = listeJoueurs.get(i);
        } else if (!listeJoueurs.isEmpty()) {
            joueurActuel = listeJoueurs.get(0);
        } else if (!joueurParDefault.equals("")) {
            listeJoueurs.add(new Joueur(joueurParDefault));
        }
    }
}
