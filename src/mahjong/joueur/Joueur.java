package mahjong.joueur;

import java.io.Serializable;
import java.util.ArrayList;
import mahjong.partie.Partie;

public class Joueur implements Serializable {

    private String nom;
    private ArrayList<Partie> listePartie;

    public Joueur(String nom, ArrayList<Partie> listePartie) {
        this.nom = nom;
        this.listePartie = listePartie;
    }

    public Joueur(String nom) {
        this.nom = nom;
        this.listePartie = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Partie> getListePartie() {
        return listePartie;
    }

    public void ajouterUnePartie(Partie partie) {
        listePartie.add(partie);
    }
}
