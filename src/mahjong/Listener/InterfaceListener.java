package mahjong.Listener;

import java.util.EventListener;

public interface InterfaceListener extends EventListener
{
    public void togglePause();
    public void melangerPlateau();
    public void annulerCoup();
    public void hint();
}
