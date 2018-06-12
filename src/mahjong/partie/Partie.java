package mahjong.partie;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import javax.swing.JOptionPane;
import mahjong.GUI.InterfaceDeJeu;
import mahjong.Plateau;
import mahjong.Type_Plateau.TypePlateau;
import mahjong.coup.CoupRetirerTuile;
import mahjong.Listener.ChronoListener;
import mahjong.Listener.InterfaceListener;
import mahjong.Listener.PlateauListener;

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

    public Partie(InterfaceDeJeu interfaceDeJeu, long seed, TypePlateau typePlateau) {
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(chrono, 0, 10);

        this.seed = seed;

        this.chrono = new Chrono(this, TEMP_PAR_COUP);
        this.chrono.addChronoListener(interfaceDeJeu);
        this.chrono.addChronoListener(this);

        plateau = new Plateau();
        plateau.genererNouveauPlateau(seed, typePlateau);

        this.interfaceDeJeu = interfaceDeJeu;
        this.interfaceDeJeu.setPartie(this);
        this.interfaceDeJeu.addInterfaceListener(this);
        this.interfaceDeJeu.setTailleChronometre(TEMP_PAR_COUP * 100);
    }

    public Partie() {
        timer = null;
        score = 0;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public boolean estEnPaused() {
        return enPause;
    }

    public int getScore() {
        return score;
    }

    public long getTempsDejeu() {
        return chrono.getTempsTotalDeJeu();
    }

    public void verrifierVictoire() {
        if (plateau.partieGagnee()) {
            chrono.cancel();
            interfaceDeJeu.verrouillerPlateau();
            JOptionPane.showMessageDialog(null, "Vous avez gagné !", "Victoire", JOptionPane.INFORMATION_MESSAGE);
            interfaceDeJeu.victoire();
            interfaceDeJeu.afficherMenuPrincipal();
        }
    }

    public long getSeed() {
        return seed;
    }

    public void save(FileWriter fichier) throws IOException {
        fichier.write(score + "/" + chrono.getTemp() + "/" + getTempsDejeu() + "\n");
        plateau.save(fichier);
    }

    public void charger(BufferedReader fichier) throws IOException {
        String[] values = fichier.readLine().split("/");
        score = Integer.parseInt(values[0]);
        chrono = new Chrono(this, TEMP_PAR_COUP, Long.parseLong(values[1]), Long.parseLong(values[2]));
        plateau.charger(fichier);
    }

    @Override
    public void mettreAJourChronometreDeJeu(long temps) {

    }

    @Override
    public void effacerCheminLiaisonTuiles() {

    }

    @Override
    public void genererCoup(CoupRetirerTuile coup) {
        int scoreAjouter = chrono.getScoreTemp();
        score += scoreAjouter;
        interfaceDeJeu.debloquerBoutonRetourCoup();
        interfaceDeJeu.setScore(score);
        chrono.resetChronoCoup();
        chrono.resetChronoAffichageChemin();
        verrifierVictoire();
    }

    @Override
    public void togglePause() {
        this.enPause = !enPause;
        if (enPause) {
            chrono.cancel();
            interfaceDeJeu.verrouillerPlateau();
        } else {
            chrono = new Chrono(chrono);
            timer.scheduleAtFixedRate(chrono, 0, 10);
            interfaceDeJeu.deverrouillerPlateau();
        }
    }

    @Override
    public void annulerCoup() {
        chrono.resetChronoCoup();
        score -= plateau.annulerCoup() + 10;
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
}
