package mahjong.Listener;

import java.awt.Color;
import java.util.EventListener;

public interface ChronoListener extends EventListener
{
    public void mettreAJourChronometreDeCoup(long temps, Color color);
    public void mettreAJourChronometreDeJeu(long temps);
    public void effacerCheminLiaisonTuiles();       
}
