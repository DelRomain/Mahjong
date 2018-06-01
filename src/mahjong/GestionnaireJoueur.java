package mahjong;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mahjong.joueur.Joueur;

public class GestionnaireJoueur {

    private ArrayList<Joueur> listeJoueurs;
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
        this.joueurActuel = joueur;
    }

    public boolean add(Joueur joueur) {
        boolean nouveauNom = false;
        if(!listeJoueurs.contains(joueur))
        {
            listeJoueurs.add(joueur);
            nouveauNom = true;
        }
        if(joueurActuel == null)
            joueurActuel = joueur;
        return nouveauNom;
    }

    public void sauvegarderJoueurs() {
        listeJoueurs.forEach((j) -> {
            try {
                FileOutputStream fos;
                fos = new FileOutputStream("joueurs/" + j.getNom() + ".jur");
                try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                    oos.writeObject(j);
                }
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(Mahjong.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        FileOutputStream fos;
        try {
            fos = new FileOutputStream("joueurs/default.djur");
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(joueurActuel);
            }
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(Mahjong.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void chargerJoueurs() {
        listeJoueurs.clear();
        File repertoire = new File("joueurs/");
        File[] files = repertoire.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.getName().endsWith("jur")) {
                try {
                    FileInputStream fis = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    
                    Joueur joueur = (Joueur) ois.readObject();
                    if(!listeJoueurs.contains(joueur))
                    {
                        listeJoueurs.add(joueur);
                    }
                    if(file.getName().equals("default.djur"))
                        joueurActuel = joueur;
                    
                    ois.close();
                } catch (IOException ex) {
                    Logger.getLogger(Mahjong.class.getName()).log(Level.SEVERE, null, ex);

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Mahjong.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void supprimerJoueur(Joueur joueur)
    {
        listeJoueurs.remove(joueur);
        new File("joueurs/" + joueur.getNom() + ".jur").delete();
        if(joueur.equals(joueurActuel))
        {
            new File("joueurs/default.djur").delete();
            if(listeJoueurs.isEmpty())
                joueurActuel = null;
            else
                joueurActuel = listeJoueurs.get(0);
        }
            
    }

}
