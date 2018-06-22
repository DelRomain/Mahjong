package mahjong.Listener;

import java.util.EventListener;
import mahjong.TypeDeCoup.CoupRetirerTuile;

/**
 * Interface permettant de prendre en compte les événements liés au plateau
 */
public interface PlateauListener extends EventListener{
    public void genererCoup(CoupRetirerTuile coup);
    public void applicationCoup(CoupRetirerTuile coup);
}
