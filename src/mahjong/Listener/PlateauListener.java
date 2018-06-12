package mahjong.Listener;

import java.util.EventListener;
import mahjong.coup.CoupRetirerTuile;

public interface PlateauListener extends EventListener{
    public void genererCoup(CoupRetirerTuile coup);
}
