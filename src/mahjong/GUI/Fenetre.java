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
import mahjong.TypeDePlateau.TypePlateau;
import mahjong.GestionJoueur.GestionnaireJoueur;
import mahjong.GestionPartie.Partie;
import mahjong.GestionPartie.SauvegardePartie;

/**
 * Fenetre du mahjong
 */
public class Fenetre extends JFrame implements WindowListener {

    private final JPanel container;
    private final InterfaceDeJeu interfaceDeJeu;
    private final MenuPrincipal menu;
    private final GestionnaireJoueur gestionnaireJoueurs;
    private final SelectionJoueur ecranSelectionJoueur;
    private final ClassementJoueurs classement;
    private Partie partie;

    private final EventListenerList listeners = new EventListenerList();

    /**
     * Initialise une nouvelle fenetre
     */
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

        ecranSelectionJoueur = new SelectionJoueur(this);
        container.add(ecranSelectionJoueur, "EcranSelectionJoueur");

        classement = new ClassementJoueurs(this);
        container.add(classement, "Classement");

        this.setContentPane(container);
        this.setMinimumSize(new Dimension(AfficheurDePlateau.LARGEUR_TUILE * 15 + 150, AfficheurDePlateau.HAUTEUR_TUILE * 15));
        this.setVisible(true);
    }

    /**
     * Lance une nouvelle partie avec la seed et le type de plateau indiqués
     *
     * @param seed de la partie
     * @param typePlateau de la partie
     */
    public void lancerPartie(long seed, TypePlateau typePlateau) {
        partie = new Partie(interfaceDeJeu, seed, typePlateau);
        addInterfaceListener(partie);
        CardLayout cl = (CardLayout) (container.getLayout());
        cl.show(container, "Interface");
    }

    /**
     * Lance une partie à partir d'un fichier de sauvegarde
     *
     * @param partieChargee
     */
    public void lancerPartie(SauvegardePartie partieChargee) {
        gestionnaireJoueurs.setJoueur(partieChargee.getJoueur());
        partie = new Partie(interfaceDeJeu, partieChargee.getPartie());
        addInterfaceListener(partie);
        CardLayout cl = (CardLayout) (container.getLayout());
        cl.show(container, "Interface");
    }

    /**
     * Affiche le menu principal dans la fenetre
     */
    public void afficherMenuPrincipal() {
        removeInterfaceListener(partie);
        partie = null;
        CardLayout cl = (CardLayout) (container.getLayout());
        cl.show(container, "Menu");
    }

    /**
     * Affiche l'ecran de selection du joueur dans la fenetre
     */
    public void afficherEcranSelectionJoueur() {
        gestionnaireJoueurs.chargerJoueurs();
        ecranSelectionJoueur.rechargerListeJoueur();
        CardLayout cl = (CardLayout) (container.getLayout());
        cl.show(container, "EcranSelectionJoueur");
    }

    /**
     * Affiche l'ecran de classement des joueur dans la fenetre
     */
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

    /**
     * Ajoute une classe écouteur des événement de type interface
     *
     * @param listener : la classe écouteur
     */
    public void addInterfaceListener(InterfaceListener listener) {
        listeners.add(InterfaceListener.class, listener);
    }

    /**
     * Retire une classe écouteur des événement de type interface
     *
     * @param listener : la classe écouteur
     */
    public void removeInterfaceListener(InterfaceListener listener) {
        listeners.remove(InterfaceListener.class, listener);
    }

    /**
     * Lance l'événement "tooglePause"
     */
    private void fireTogglePause() {
        if (partie != null && !partie.enPause()) {
            interfaceDeJeu.tooglePause();
            for (InterfaceListener listener : listeners.getListeners(InterfaceListener.class)) {
                listener.togglePause();
            }
        }
    }

    public GestionnaireJoueur getGestionnaireJoueurs() {
        return gestionnaireJoueurs;
    }
}
