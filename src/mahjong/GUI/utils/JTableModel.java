package mahjong.GUI.utils;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Canevas du modele pour tout les tableaux graphiques du jeu
 * @param <T> definit le type d'information qui sera afficher par le tableau
 */
public abstract class JTableModel<T> extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    protected ArrayList<T> liste;
    private final String[] entetes;

    public JTableModel(String[] entetes) {
        this.entetes = entetes;
    }

    @Override
    public int getColumnCount() {
        return entetes.length;
    }

    @Override
    public int getRowCount() {
        return liste.size();
    }

    @Override
    public String getColumnName(int index) {
        return entetes[index];
    }

    public ArrayList<T> getListe() {
        return liste;
    }

    public abstract void ajouterEntree(T nouvelleValeur);

    public abstract void modifierEntree(T ancienneValeur, T nouvelleValeur);

    public abstract void retirerEntree(int ligne);

}
