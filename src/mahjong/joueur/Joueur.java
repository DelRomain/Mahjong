package mahjong.joueur;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import mahjong.partie.Chrono;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Joueur other = (Joueur) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        return true;
    }

    public String getMeilleurTemps() {
        String result;
        if (!listePartie.isEmpty()) {
            result = Chrono.getTempsFormate(Collections.max(listePartie, new Comparator<Partie>() {
                @Override
                public int compare(Partie partie1, Partie partie2) {
                    if (partie1.getTempTotalChronoPause() > partie2.getTempTotalChronoPause()) {
                        return 1;
                    } else if (partie1.getTempTotalChronoPause() == partie2.getTempTotalChronoPause()) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            }).getTempTotalChronoPause());
            
        } else {
            result = "/";
        }

        return result;
    }

    public String getMeilleurScore() {
        String result;
        if (!listePartie.isEmpty()) {
            result = "" + Collections.max(listePartie, new Comparator<Partie>() {
                @Override
                public int compare(Partie partie1, Partie partie2) {
                    if (partie1.getScore() > partie2.getScore()) {
                        return 1;
                    } else if (partie1.getScore() == partie2.getScore()) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            }).getScore();
        } else {
            result = "/";
        }

        return result;
    }

    public String getNombrePartie() {
        return listePartie.size() + "";
    }

}
