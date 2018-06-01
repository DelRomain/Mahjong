package mahjong.GUI;

import mahjong.GestionnaireJoueur;
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
    private final GestionnaireJoueur gestionnaireJoueurs;
    private final SelectionJoueurGUI ecranSelectionJoueur;
    private final classementGUI classement;

    public Fenetre() {
        super("Mahjong");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gestionnaireJoueurs = new GestionnaireJoueur();
        gestionnaireJoueurs.chargerJoueurs();
        
        container = new JPanel();
        container.setLayout(new CardLayout());

        menu = new MenuPrincipal(this);
        container.add(menu, "Menu");

        interfaceDeJeu = new InterfaceDeJeu(this);
        container.add(interfaceDeJeu, "Interface");
        
        ecranSelectionJoueur = new SelectionJoueurGUI(this);
        container.add(ecranSelectionJoueur,"EcranSelectionJoueur");
        
        classement = new classementGUI(this);
        container.add(classement, "Classement");

        this.setContentPane(container);
        this.setMinimumSize(new Dimension(PlateauGUI.LARGEUR_TUILE * 15 + 150, PlateauGUI.HAUTEUR_TUILE * 15));
        this.setVisible(true);
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
    
    public void afficherEcranSelectionJoueur() {
        gestionnaireJoueurs.chargerJoueurs();
        ecranSelectionJoueur.rechargerListeJoueur();
        CardLayout cl = (CardLayout) (container.getLayout());
        cl.show(container, "EcranSelectionJoueur");
    } 

    public GestionnaireJoueur getGestionnaireJoueurs() {
        return gestionnaireJoueurs;
    }

    void afficherClassement() {
        classement.rechargerListeJoueur();
        CardLayout cl = (CardLayout) (container.getLayout());
        cl.show(container, "Classement");
    }
}
