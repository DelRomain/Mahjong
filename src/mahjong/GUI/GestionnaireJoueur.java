package mahjong.GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mahjong.Mahjong;
import mahjong.joueur.Joueur;

public class GestionnaireJoueur 
{
    private ArrayList<Joueur> listeJoueurs;
    private Joueur joueur;

    public GestionnaireJoueur() 
    {
        this.listeJoueurs = new ArrayList<>();
    }

    public ArrayList<Joueur> getListeJoueurs() {
        return listeJoueurs;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    void add(Joueur joueur) {
        listeJoueurs.add(joueur);
    }
    
        
     public void sauvegarderJoueurs() {
        for (Joueur j : listeJoueurs) {
            FileOutputStream fos;
            try {
                fos = new FileOutputStream("joueurs/"+j.getNom() + ".jur");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(j);
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(Mahjong.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void chargerJoueurs() {
        File repertoire = new File("joueurs/");
        File[] files = repertoire.listFiles();
        if(files == null)
            return;
        for(File file : files)
        {
            if(file.getName().endsWith(".jur"))
            {
                try {
                    FileInputStream fis = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    listeJoueurs.add((Joueur) ois.readObject());
                    ois.close();
                } catch (IOException ex) {
                    Logger.getLogger(Mahjong.class.getName()).log(Level.SEVERE, null, ex);

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Mahjong.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
}
