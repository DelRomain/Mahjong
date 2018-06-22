package mahjong.GestionJoueur;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import mahjong.GestionPartie.Partie;

/**
 * Classe stockant les informations relative à un joueur
 */
public class Joueur{

    private String nom;
    private long meilleurTemps;
    private int meilleurScore;
    private final ArrayList<Partie> listePartie;

    public Joueur(String nom, ArrayList<Partie> listePartie) {
        this.nom = nom;
        this.listePartie = listePartie;
    }

    public Joueur(String nom) {
        this.nom = nom;
        this.listePartie = new ArrayList<>();
    }

    public Joueur() {
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
        if(partie.getScore()>meilleurScore)
            meilleurScore = partie.getScore();
        if(partie.getTempsDejeu()>meilleurTemps)
            meilleurTemps = partie.getTempsDejeu();
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
        return Objects.equals(this.nom, other.nom);
    }

    public long getMeilleurTemps() {
        return meilleurTemps;
    }

    public int getMeilleurScore() {
        return meilleurScore;
    }

    public String getNombrePartie() {
        return listePartie.size() + "";
    }

    public void save(FileWriter fichier) throws IOException
    {
        fichier.write(nom+"\n");
        fichier.write(getNombrePartie()+"/"+getMeilleurScore()+"/"+getMeilleurTemps()+"\n");
        for(Partie partie : listePartie)
        {
           partie.save(fichier);
           fichier.write("\n");
           fichier.flush();
        }
    }

    public void charger(BufferedReader fichier) throws IOException 
    {
        nom = fichier.readLine();
        String valeurs[] = fichier.readLine().split("/");
        
        meilleurScore = Integer.parseInt(valeurs[1]);
        meilleurTemps = Long.parseLong(valeurs[2]);
        int nombrePartie = Integer.parseInt(valeurs[0]);
        
        for(int i = 0; i<nombrePartie;i++)
        {
            Partie partie = new Partie();
            partie.charger(fichier);
            listePartie.add(partie);
        }
    }
}
