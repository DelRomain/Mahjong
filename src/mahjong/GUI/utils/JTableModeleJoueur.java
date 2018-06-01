package mahjong.GUI.utils;

import java.util.ArrayList;
import mahjong.joueur.Joueur;

public class JTableModeleJoueur extends JTableModel<Joueur>
{
	private static final long serialVersionUID = 1L;

	public JTableModeleJoueur() 
	{
		super(new String[]{"Nom"});
		liste = new ArrayList<>();
	}

	@Override
	public Object getValueAt(int row, int col) 
	{
		switch(col)
		{
		case 0: 
			return liste.get(row).getNom();
		default: 
			return col;
		}
	}

	@Override
	public void ajouterEntree(Joueur nouvelleValeur)
	{
		liste.add(nouvelleValeur);
		fireTableRowsInserted(liste.size() -1, liste.size() -1);
	}

	@Override
	public void modifierEntree(Joueur ancienneValeur, Joueur nouvelleValeur) {
		int row = liste.indexOf(ancienneValeur);
		setValueAt(nouvelleValeur.getNom(), row, 0);
		liste.set(row, nouvelleValeur);

		fireTableCellUpdated(row, 1);
	}

	@Override
	public void retirerEntree(int ligne) {
		liste.remove(ligne);
		fireTableRowsDeleted(ligne, ligne);
	}
}