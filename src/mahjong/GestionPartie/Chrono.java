package mahjong.GestionPartie;

import java.awt.Color;
import java.util.TimerTask;
import javax.swing.event.EventListenerList;
import mahjong.Listener.ChronoListener;

/**
 * Classe gérant le temps pour la partie
 */
public class Chrono extends TimerTask {

    private long tempsDuCoup;
    private long tempsTotalDeJeu;
    private long tempsAffichageChemin;
    private final int TEMP_COUP;
    private final int TEMP_COUP_RETRAIT_POINT;
    private final int TEMP_COUP_FIN_CHRONO;
    private final Partie partie;

    private final EventListenerList listeners = new EventListenerList();

    /**
     * Génère un nouveau chrononometre à partir d'information d'une sauvegarde
     *
     * @param partie sur lequel le chronometre s'applique
     * @param tempCoup : temps pour jouer un coup
     * @param tempCoupRestant : temps du chronometre de coup
     * @param tempTotal : temps depuis le debut de la partie
     */
    public Chrono(Partie partie, int tempCoup, long tempCoupRestant, long tempTotal) {
        this(partie, tempCoup);
        this.tempsTotalDeJeu = tempTotal;
        this.tempsDuCoup = tempCoupRestant;
        this.tempsAffichageChemin = 21;
    }

    /**
     * Génère un nouveau chrononometre
     *
     * @param partie sur lequel le chronometre s'applique
     * @param tempCoup : temps pour jouer un coup
     */
    public Chrono(Partie partie, int tempCoup) {
        this.tempsTotalDeJeu = 0;
        this.TEMP_COUP = tempCoup * 100;
        this.tempsDuCoup = TEMP_COUP;

        this.TEMP_COUP_RETRAIT_POINT = (int) (0.75 * TEMP_COUP);
        this.TEMP_COUP_FIN_CHRONO = (int) (0.25 * TEMP_COUP);
        this.partie = partie;
        this.tempsAffichageChemin = 0;
    }

    /**
     * Génère un nouveau chrononometre à partir d'information d'un ancien
     *
     * @param chrono à recopier
     */
    public Chrono(Chrono chrono) {
        this.partie = chrono.partie;
        this.tempsTotalDeJeu = chrono.tempsTotalDeJeu;
        this.tempsDuCoup = chrono.tempsDuCoup;
        this.tempsAffichageChemin = chrono.tempsAffichageChemin;
        this.TEMP_COUP = chrono.TEMP_COUP;
        this.TEMP_COUP_FIN_CHRONO = chrono.TEMP_COUP_FIN_CHRONO;
        this.TEMP_COUP_RETRAIT_POINT = chrono.TEMP_COUP_RETRAIT_POINT;
    }

    @Override
    public void run() {
        Color color;
        this.tempsDuCoup--;
        this.tempsTotalDeJeu++;

        if (tempsAffichageChemin <= 20) {
            this.tempsAffichageChemin++;
        }

        if (tempsAffichageChemin == 20) {
            fireEffacerCheminLiaisonTuiles();
        }

        fireMettreAJourChronometreDeJeu(tempsTotalDeJeu);

        if (tempsDuCoup > TEMP_COUP_RETRAIT_POINT) {
            color = Color.GREEN;
        } else if (tempsDuCoup > TEMP_COUP_FIN_CHRONO) {
            color = Color.ORANGE;
        } else {
            color = Color.RED;
        }

        fireMettreAJourChronometreDeCoup(tempsDuCoup, color);
    }

    /**
     * Remet à zéro le temps pour jouer un coup
     */
    public void resetChronoCoup() {
        this.tempsDuCoup = this.TEMP_COUP;
    }

    /**
     * Remet à zéro le temps pendant lequel le chemin entre deux tuiles jouées
     * sera visible
     */
    public void resetChronoAffichageChemin() {
        this.tempsAffichageChemin = 0;
    }

    /**
     * Récupere le score d'un coup en fonction du temps mis pour le jouer
     * @return le score attribué au coup
     */
    public int getScoreTemp() {
        int pointGagnee = 0;
        if (tempsDuCoup > TEMP_COUP_RETRAIT_POINT) {
            pointGagnee = 20;
        } else {
            pointGagnee = (int) tempsDuCoup / 100;
        }
        return pointGagnee;
    }

    public long getTempsCoup() {
        return tempsDuCoup;
    }

    public long getTempsTotalDeJeu() {
        return tempsTotalDeJeu;
    }

    public void setTempsTotalDeJeu(long tempsTotalDeJeu) {
        this.tempsTotalDeJeu = tempsTotalDeJeu;
    }

    public long getTempsAffichage() {
        return tempsAffichageChemin;
    }

    //Méthode pour la classe ChronoListener
    /**
     * Ajoute une classe écouteur des événements généré par le chronometre
     *
     * @param listener : la classe écouteur
     */
    public void addChronoListener(ChronoListener listener) {
        listeners.add(ChronoListener.class, listener);
    }

    /**
     * Retire une classe écouteur des événements généré par le chronometre
     *
     * @param listener : la classe écouteur
     */
    public void removeChronoListener(ChronoListener listener) {
        listeners.remove(ChronoListener.class, listener);
    }
    
    /**
     * Lance un événement du type "effacerCheminLiaisonTuiles"
     */
    public void fireEffacerCheminLiaisonTuiles() {
        for (ChronoListener listener : listeners.getListeners(ChronoListener.class)) {
            listener.effacerCheminLiaisonTuiles();
        }
    }

    /**
     * Lance un événement du type "mettreAJourChronometreDeCoup"
     */
    public void fireMettreAJourChronometreDeCoup(long temps, Color color) {
        for (ChronoListener listener : listeners.getListeners(ChronoListener.class)) {
            listener.mettreAJourChronometreDeCoup(temps, color);
        }
    }

    /**
     * Lance un événement du type "mettreAJourChronometreDeJeu"
     */
    public void fireMettreAJourChronometreDeJeu(long temps) {
        for (ChronoListener listener : listeners.getListeners(ChronoListener.class)) {
            listener.mettreAJourChronometreDeJeu(temps);
        }
    }

    /**
     * Renvoie une chaine de caractère formaté selon hh:mm:ss
     * @param temps à formater 
     * @return temps sous la forme hh:mm:ss
     */
    public static String getTempsFormate(long temps) {
        long tempTotalSeconde = temps / 100;
        long nombreSeconde = tempTotalSeconde % 60;
        long nombreMinute = ((tempTotalSeconde - nombreSeconde) / 60) % 60;
        long nombreHeure = (tempTotalSeconde - nombreMinute * 60 - nombreSeconde) / 3600;

        return String.format("%02d", nombreHeure) + ":" + String.format("%02d", nombreMinute) + ":" + String.format("%02d", nombreSeconde);
    }
}
