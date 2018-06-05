package mahjong.joueur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mahjong.partie.Partie;

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

    public void setJoueur(Joueur joueur) {
        if(!listeJoueurs.contains(joueur))
            listeJoueurs.add(joueur);
        else
            joueur = listeJoueurs.get(listeJoueurs.indexOf(joueur));
        this.joueurActuel = joueur;
    }

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

    public void ajouterPartieAuJoueurCourant(Partie partie) {
        joueurActuel.ajouterUnePartie(partie);
        sauvegarderJoueur(joueurActuel);
    }

    public void sauvegarderJoueurs() {
        listeJoueurs.forEach((joueur) -> {
            sauvegarderJoueur(joueur);
        });
        try {
            FileWriter fichier = new FileWriter("joueurs/default.djur");
            fichier.write(joueurActuel.getNom());
            fichier.close();
        } catch (IOException ex) {
            Logger.getLogger(GestionnaireJoueur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private void sauvegarderJoueur(Joueur joueur) {
        try {
            FileWriter fichier = new FileWriter("joueurs/" + joueur.getNom() + ".jur");
            joueur.save(fichier);
            fichier.close();
        } catch (IOException ex) {
            Logger.getLogger(GestionnaireJoueur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
                try {
                    BufferedReader fichier = new BufferedReader(new FileReader(file));
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
        }
        else if(!joueurParDefault.equals(""))
        {
            listeJoueurs.add(new Joueur(joueurParDefault));
        }
    }

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

   

}
