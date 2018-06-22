package mahjong.GUI.utils;

import java.util.ArrayList;
import mahjong.GestionJoueur.Joueur;
import mahjong.GestionPartie.Chrono;

/**
 * Modele pour le tableau d'affichage du classement
 */
public class JTableModeleClassement extends JTableModel<Joueur> {

    private static final long serialVersionUID = 1L;

    public JTableModeleClassement() {
        super(new String[]{"Nom", "Nombre de partie", "Meilleur score", "Meilleur temps"});
        liste = new ArrayList<>();
    }

    @Override
    public Object getValueAt(int row, int col) {
        switch (col) {
            case 0:
                return liste.get(row).getNom();
            case 1:
                return liste.get(row).getNombrePartie();
            case 2:
                return liste.get(row).getMeilleurScore();
            case 3:
                return Chrono.getTempsFormate(liste.get(row).getMeilleurTemps());
            default:
                return col;
        }
    }

    @Override
    public void ajouterEntree(Joueur nouvelleValeur) {
        liste.add(nouvelleValeur);
        fireTableRowsInserted(liste.size() - 1, liste.size() - 1);
    }

    @Override
    public void modifierEntree(Joueur ancienneValeur, Joueur nouvelleValeur) {
        int row = liste.indexOf(ancienneValeur);
        setValueAt(nouvelleValeur.getNom(), row, 0);
        setValueAt(nouvelleValeur.getNombrePartie(), row, 1);
        setValueAt(nouvelleValeur.getMeilleurScore(), row, 2);
        setValueAt(nouvelleValeur.getMeilleurTemps(), row, 3);
        liste.set(row, nouvelleValeur);

        fireTableCellUpdated(row, 1);
    }

    @Override
    public void retirerEntree(int ligne) {
        liste.remove(ligne);
        fireTableRowsDeleted(ligne, ligne);
    }
}
