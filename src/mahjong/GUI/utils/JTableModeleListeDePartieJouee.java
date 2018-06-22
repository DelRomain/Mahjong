package mahjong.GUI.utils;

import java.util.ArrayList;
import mahjong.GestionPartie.Chrono;
import mahjong.GestionPartie.Partie;

/**
 * Modele pour le tableau d'affichage des parties jouées
 */
public class JTableModeleListeDePartieJouee extends JTableModel<Partie> {

    private static final long serialVersionUID = 1L;

    public JTableModeleListeDePartieJouee() {
        super(new String[]{"Seed", "Type de plateau", "Temps", "Score"});
        liste = new ArrayList<>();
    }

    @Override
    public Object getValueAt(int row, int col) {
        switch (col) {
            case 0:
                return liste.get(row).getSeed();
            case 1:
                return liste.get(row).getPlateau().getTypeDePlateau().getNom();
            case 2:
                return Chrono.getTempsFormate(liste.get(row).getTempsDejeu());
            case 3:
                return liste.get(row).getScore();
            default:
                return col;
        }
    }

    @Override
    public void ajouterEntree(Partie nouvelleValeur) {
        liste.add(nouvelleValeur);
        fireTableRowsInserted(liste.size() - 1, liste.size() - 1);
    }

    @Override
    public void modifierEntree(Partie ancienneValeur, Partie nouvelleValeur) {
        int row = liste.indexOf(ancienneValeur);
        setValueAt(nouvelleValeur.getSeed(), row, 0);
        setValueAt(nouvelleValeur.getPlateau().getTypeDePlateau().getNom(), row, 1);
        setValueAt(Chrono.getTempsFormate(nouvelleValeur.getTempsDejeu()), row, 2);
        setValueAt(nouvelleValeur.getScore(), row, 3);
        liste.set(row, nouvelleValeur);

        fireTableCellUpdated(row, 1);
    }

    @Override
    public void retirerEntree(int ligne) {
        liste.remove(ligne);
        fireTableRowsDeleted(ligne, ligne);
    }
}
