package mahjong.Listener;

import java.util.EventListener;

/**
 * Interface permettant de prendre en compte les événements du à l'interface
 */
public interface InterfaceListener extends EventListener
{
    public void togglePause();
    public void melangerPlateau();
    public void annulerCoup();
    public void hint();
}
