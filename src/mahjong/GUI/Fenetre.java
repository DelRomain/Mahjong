package mahjong.GUI;

import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import mahjong.TypePlateau;
import mahjong.joueur.Joueur;
import mahjong.partie.Partie;

public class Fenetre extends JFrame {

    private final JPanel container;
    private final InterfaceDeJeu interfaceDeJeu;
    private final MenuPrincipal menu;
    private static GestionnaireJoueur gestionnaireJoueurs;

    public Fenetre() {
        super("Mahjong");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        container = new JPanel();
        container.setLayout(new CardLayout());

        menu = new MenuPrincipal(this);
        container.add(menu, "Menu");

        interfaceDeJeu = new InterfaceDeJeu(this);
        container.add(interfaceDeJeu, "Interface");

        this.setContentPane(container);
        this.setMinimumSize(new Dimension(PlateauGUI.LARGEUR_TUILE * 15 + 150, PlateauGUI.HAUTEUR_TUILE * 15));
        this.setVisible(true);
        
        gestionnaireJoueurs = new GestionnaireJoueur();
        gestionnaireJoueurs.add(new Joueur("test"));
        gestionnaireJoueurs.setJoueur(gestionnaireJoueurs.getListeJoueurs().get(0));
        gestionnaireJoueurs.chargerJoueurs();
    }

    public void lancerPartie(long seed, TypePlateau typePlateau) {
        new Partie(interfaceDeJeu, seed, typePlateau);
        CardLayout cl = (CardLayout) (container.getLayout());
        cl.show(container, "Interface");
    }

    public void afficherMenuPrincipal() {
        CardLayout cl = (CardLayout) (container.getLayout());
        cl.show(container, "Menu");
    }    

    public GestionnaireJoueur getGestionnaireJoueurs() {
        return gestionnaireJoueurs;
    }
}
