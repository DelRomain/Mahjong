package mahjong.GestionPartie;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import javax.swing.JOptionPane;
import mahjong.GUI.InterfaceDeJeu;
import mahjong.GestionPlateau.Plateau;
import mahjong.TypeDePlateau.TypePlateau;
import mahjong.TypeDeCoup.CoupRetirerTuile;
import mahjong.Listener.ChronoListener;
import mahjong.Listener.InterfaceListener;
import mahjong.Listener.PlateauListener;

/**
 * Classe gérant la partie
 */
public class Partie implements ChronoListener, PlateauListener, InterfaceListener {

    private boolean enPause;
    private long seed;
    private int score;

    //temps maximal en seconde pour ne pas perdre de point lors d'un coup
    private final int TEMP_PAR_COUP = 20;

    private Chrono chrono;
    private final Timer timer;
    private Plateau plateau;
    private InterfaceDeJeu interfaceDeJeu;

    /**
     * Crée une nouvelle partie à partir d'une seed et d'un type de plateau
     *
     * @param interfaceDeJeu : interface sur laquelle afficher la partie
     * @param seed de la partie
     * @param typePlateau de la partie
     */
    public Partie(InterfaceDeJeu interfaceDeJeu, long seed, TypePlateau typePlateau) {
        this.seed = seed;

        this.chrono = new Chrono(this, TEMP_PAR_COUP);
        this.chrono.addChronoListener(interfaceDeJeu);
        this.chrono.addChronoListener(this);

        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(chrono, 0, 10);

        this.plateau = new Plateau();
        this.plateau.genererNouveauPlateau(seed, typePlateau);

        this.interfaceDeJeu = interfaceDeJeu;
        this.interfaceDeJeu.setPartie(this);
        this.interfaceDeJeu.addInterfaceListener(this);
        this.interfaceDeJeu.setTailleChronometre(TEMP_PAR_COUP * 100);

        this.enPause = false;
    }

    /**
     * Crée une nouvelle partie à partir d'une autre
     *
     * @param interfaceDeJeu : interface sur laquelle afficher la partie
     * @param partie : partie à copier
     */
    public Partie(InterfaceDeJeu interfaceDeJeu, Partie partie) {
        this.seed = partie.seed;
        this.score = partie.score;
        this.plateau = partie.plateau;

        this.chrono = new Chrono(this, TEMP_PAR_COUP);
        this.chrono.setTempsTotalDeJeu(partie.getTempsDejeu());
        this.chrono.addChronoListener(interfaceDeJeu);
        this.chrono.addChronoListener(this);

        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(chrono, 0, 10);

        this.interfaceDeJeu = interfaceDeJeu;
        this.interfaceDeJeu.setPartie(this);
        this.interfaceDeJeu.setScore(this.score);
        this.interfaceDeJeu.addInterfaceListener(this);
        this.interfaceDeJeu.setTailleChronometre(TEMP_PAR_COUP * 100);

        this.enPause = false;

        verrifierVictoire();
        if (plateau.getCoups().isEmpty()) {
            interfaceDeJeu.bloquerBoutonRetourCoup();
        } else {
            interfaceDeJeu.debloquerBoutonRetourCoup();
        }
    }

    /**
     * Crée une partie vide
     */
    public Partie() {
        this.plateau = new Plateau();
        this.timer = null;
        this.score = 0;
    }

    public final void verrifierVictoire() {
        if (plateau.partieTerminee()) {
            chrono.cancel();
            interfaceDeJeu.verrouillerPlateau();
            JOptionPane.showMessageDialog(null, "Vous avez gagné !", "Victoire", JOptionPane.INFORMATION_MESSAGE);
            interfaceDeJeu.victoire();
            interfaceDeJeu.afficherMenuPrincipal();
        } else if (!plateau.plateauJouable()) {
            interfaceDeJeu.afficherAvertisementPlusDeCoup();
        }
    }

    public boolean enPause() {
        return enPause;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public int getScore() {
        return score;
    }

    public long getTempsDejeu() {
        return chrono.getTempsTotalDeJeu();
    }

    public long getSeed() {
        return seed;
    }

    /**
     * Sauvegarde la partie
     *
     * @param fichier
     * @throws IOException
     */
    public void save(FileWriter fichier) throws IOException {
        fichier.write(score + "/" + chrono.getTempsCoup() + "/" + getTempsDejeu() + "\n");
        plateau.save(fichier);
    }

    /**
     * Charge la partie
     *
     * @param fichier
     * @throws IOException
     */
    public void charger(BufferedReader fichier) throws IOException {
        String[] values = fichier.readLine().split("/");
        score = Integer.parseInt(values[0]);
        chrono = new Chrono(this, TEMP_PAR_COUP, Long.parseLong(values[1]), Long.parseLong(values[2]));
        plateau.charger(fichier);
        seed = plateau.getSeed();
    }

    @Override
    public void mettreAJourChronometreDeJeu(long temps) {
    }

    @Override
    public void effacerCheminLiaisonTuiles() {
    }

    /**
     * Permet d'attribuer le score à un coup, de relancer un chrono pour un
     * nouveau coup et de lancer une verification de victoire
     *
     * @param coup qui vient d'être joeur
     */
    @Override
    public void genererCoup(CoupRetirerTuile coup) {
        int scoreAjouter = chrono.getScoreTemp();
        coup.setScore(scoreAjouter);
        score += scoreAjouter;

        interfaceDeJeu.debloquerBoutonRetourCoup();
        interfaceDeJeu.setScore(score);

        chrono.resetChronoCoup();
        chrono.resetChronoAffichageChemin();

        verrifierVictoire();
        this.plateau.effacerHint();
    }

    @Override
    public void togglePause() {
        this.enPause = !enPause;
        if (enPause) {
            chrono.cancel();
            interfaceDeJeu.verrouillerPlateau();
        } else {
            chrono = new Chrono(chrono);
            this.chrono.addChronoListener(interfaceDeJeu);
            this.chrono.addChronoListener(this);
            timer.scheduleAtFixedRate(chrono, 0, 10);
            interfaceDeJeu.deverrouillerPlateau();
        }
    }

    @Override
    public void annulerCoup() {
        chrono.resetChronoCoup();
        System.out.println(score);
        score -= plateau.annulerCoup() + 10;
        System.out.println(score);
        interfaceDeJeu.setScore(score);
        interfaceDeJeu.repaint();

        if (plateau.getCoups().isEmpty()) {
            interfaceDeJeu.bloquerBoutonRetourCoup();
        }

    }

    @Override
    public void mettreAJourChronometreDeCoup(long temps, Color color) {

    }

    @Override
    public void melangerPlateau() {
        plateau.melangerPlateau();
        interfaceDeJeu.repaint();
    }

    @Override
    public void hint() {
        plateau.afficherHint();
        score -= 30;
        interfaceDeJeu.setScore(score);
        interfaceDeJeu.repaint();

    }

    @Override
    public void applicationCoup(CoupRetirerTuile coup) {
        verrifierVictoire();
    }
}
