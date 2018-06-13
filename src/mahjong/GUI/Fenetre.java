package mahjong.GUI;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;
import mahjong.Listener.InterfaceListener;
import mahjong.Type_Plateau.TypePlateau;
import mahjong.joueur.GestionnaireJoueur;
import mahjong.partie.Partie;
import mahjong.partie.SauvegardePartie;

public class Fenetre extends JFrame implements WindowListener {

    private final JPanel container;
    private final InterfaceDeJeu interfaceDeJeu;
    private final MenuPrincipal menu;
    private final GestionnaireJoueur gestionnaireJoueurs;
    private final SelectionJoueurGUI ecranSelectionJoueur;
    private final classementGUI classement;
    private Partie partie;

    private final EventListenerList listeners = new EventListenerList();

    public Fenetre() {
        super("Mahjong");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(this);

        gestionnaireJoueurs = new GestionnaireJoueur();
        gestionnaireJoueurs.chargerJoueurs();

        container = new JPanel();
        container.setLayout(new CardLayout());

        menu = new MenuPrincipal(this);
        container.add(menu, "Menu");

        interfaceDeJeu = new InterfaceDeJeu(this);
        container.add(interfaceDeJeu, "Interface");

        ecranSelectionJoueur = new SelectionJoueurGUI(this);
        container.add(ecranSelectionJoueur, "EcranSelectionJoueur");

        classement = new classementGUI(this);
        container.add(classement, "Classement");

        this.setContentPane(container);
        this.setMinimumSize(new Dimension(PlateauGUI.LARGEUR_TUILE * 15 + 150, PlateauGUI.HAUTEUR_TUILE * 15));
        this.setVisible(true);
    }

    public void lancerPartie(long seed, TypePlateau typePlateau) {
        partie = new Partie(interfaceDeJeu, seed, typePlateau);
        addInterfaceListener(partie);
        CardLayout cl = (CardLayout) (container.getLayout());
        cl.show(container, "Interface");
    }

    public void afficherMenuPrincipal() {
        removeInterfaceListener(partie);
        partie = null;
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

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        boolean doitFermer = true;
        if (partie != null) {
            fireTogglePause();
            int option = JOptionPane.showConfirmDialog(this, "Voulez-vous sauvegarder la partie courante ?", "Fermeture fenetre", JOptionPane.YES_NO_CANCEL_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                JFileChooser fileChooser = new JFileChooser("partie");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int returnVal = fileChooser.showSaveDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    SauvegardePartie save = new SauvegardePartie(partie, gestionnaireJoueurs.getJoueur());
                    save.sauvegarder(file.getAbsolutePath() + ".mprt");

                } else if (returnVal == JFileChooser.CANCEL_OPTION) {
                    doitFermer = false;
                }
            }
            if (option == JOptionPane.CANCEL_OPTION) {
                doitFermer = false;
            }
        }
        if (doitFermer) {
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        fireTogglePause();
    }

    public void addInterfaceListener(InterfaceListener listener) {
        listeners.add(InterfaceListener.class, listener);
    }

    public void removeInterfaceListener(InterfaceListener listener) {
        listeners.remove(InterfaceListener.class, listener);
    }
    
    private void fireTogglePause() 
    {
        if(partie != null && !partie.enPause())
        {
            interfaceDeJeu.tooglePause();
            for (InterfaceListener listener : listeners.getListeners(InterfaceListener.class)) {
                listener.togglePause();
            }
        }
    }

    public void lancerPartie(SauvegardePartie partieChargee) 
    {
        gestionnaireJoueurs.setJoueur(partieChargee.getJoueur());
        partie = new Partie(interfaceDeJeu, partieChargee.getPartie());
        addInterfaceListener(partie);
        CardLayout cl = (CardLayout) (container.getLayout());
        cl.show(container, "Interface");
    }
}
