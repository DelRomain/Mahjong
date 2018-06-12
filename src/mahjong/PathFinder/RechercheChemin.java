package mahjong.PathFinder;

import java.util.ArrayList;
import java.util.Collections;
import mahjong.CaseAdjacente;
import mahjong.Emplacement;
import mahjong.Plateau;
import mahjong.Tuile;

/**
 *
 * @author alafitte
 */
public class RechercheChemin {

    private CaseRecherchee caseArrivee;
    private final ArrayList<CaseRecherchee> listeOuverte;
    private final ArrayList<CaseRecherchee> listeFermee;
    private final Plateau plateau;

    public RechercheChemin(Plateau plateau) {
        this.listeOuverte = new ArrayList();
        this.listeFermee = new ArrayList();
        this.plateau = plateau;
    }

    public boolean rechercheChemin(Tuile depart, Tuile arrivee) {
        listeOuverte.clear();
        listeFermee.clear();

        caseArrivee = new CaseRecherchee(null, arrivee.getCoordonnees(), null);
        return rechercheChemin(new CaseRecherchee(null, depart.getCoordonnees(), null));
    }

    private boolean rechercheChemin(CaseRecherchee caseRecherchee) {
        Emplacement emplacement;
        System.out.println("Coordonées pere: "+caseRecherchee.getEmplacement());
        for (CaseAdjacente positionRelative : CaseAdjacente.values()) {
            emplacement = caseRecherchee.getEmplacement().add(new Emplacement(positionRelative.getLigne(),positionRelative.getColonne()));
            if (estSurPlateau(emplacement.getLigne(), emplacement.getColonne())) {
                Tuile tuile = plateau.getTuile(emplacement.getLigne(), emplacement.getColonne());
                
                System.out.println("> Verrif de la tuile : "+emplacement);
                System.out.println("> Tuile emplacemnt: "+tuile);
                if (tuile == null) {
                    
                    CaseRecherchee caseObservee = new CaseRecherchee(caseRecherchee, emplacement, positionRelative);
                    caseObservee.setDistance(getDistance(caseArrivee, caseObservee));
                    
                    if (caseObservee.getNombreAngleDroit() <= 2) {
                        if (listeOuverte.contains(caseObservee)) {
                            int index = listeOuverte.indexOf(caseObservee);
                            int distanceArrivee = getDistance(caseObservee, caseArrivee);
                            CaseRecherchee caseListe = listeOuverte.get(index);
                            if (caseListe.getTotal() > distanceArrivee || caseListe.getNombreAngleDroit() > caseObservee.getNombreAngleDroit()) {
                                listeOuverte.get(index).setTotal(distanceArrivee);
                                listeOuverte.get(index).setNombreAngleDroit(caseObservee.getNombreAngleDroit());
                                listeOuverte.get(index).setParent(caseObservee.getParent());
                            }
                            /*
                            if(listeOuverte.get(index).getNombreAngleDroit()<caseObservee.getNombreAngleDroit())
                            {
                                listeOuverte.get(index).setNombreAngleDroit(caseObservee.getNombreAngleDroit());
                                listeOuverte.get(index).setParent(caseObservee.getParent());
                            }
                            //*/
                        } else {
                            listeOuverte.add(caseObservee);
                            //listeFermee.add(caseObservee);
                        }
                    }
                }
                else if(caseArrivee.getEmplacement().equals(emplacement))
                {
                    CaseRecherchee caseObservee = new CaseRecherchee(caseRecherchee, emplacement, positionRelative);
                    if (caseObservee.getNombreAngleDroit() <= 2) {
                        caseObservee.setDistance(getDistance(caseArrivee, caseObservee));
                        listeOuverte.add(caseObservee);
                    }
                }
            }
        }

        boolean aFini = listeOuverte.contains(caseArrivee);
        if (!listeOuverte.isEmpty() && !aFini) {
            CaseRecherchee caseCourante = Collections.min(listeOuverte);
            listeFermee.add(caseCourante);
            listeOuverte.remove(caseCourante);
            aFini = rechercheChemin(caseCourante);
        }
        return aFini;
    }

    private int getDistance(CaseRecherchee caseAEvaluer, CaseRecherchee caseReference) {
        return (int) Math.pow(caseAEvaluer.getLigne() - caseReference.getLigne(), 2)
                + (int) Math.pow(caseAEvaluer.getColonne() - caseReference.getColonne(), 2);
    }

    private boolean estSurPlateau(int x, int y) {
        return x >= 0 && y >= 0 && x < Plateau.NOMBRE_COLONNE && y < Plateau.NOMBRE_LIGNE;
    }

    public CaseRecherchee getCheminEnCours() {
        int index = listeOuverte.indexOf(caseArrivee);
        return index != -1 ? listeOuverte.get(index):null;
    }
}
