/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mahjong.PathFinder;

import mahjong.CaseAdjacente;


/**
 *
 * @author alafitte
 */
public class CaseRecherchee implements Comparable{

    private final int ligne,colonne;
    private int total;
    private CaseRecherchee parent;
    private int nombreAngleDroit;
    private final CaseAdjacente direction;

    public CaseAdjacente getDirection() {
        return direction;
    }

    
    public CaseRecherchee(CaseRecherchee parent,int ligne, int colonne, CaseAdjacente direction) {
        this.parent = parent; 
        this.ligne = ligne;
        this.colonne = colonne;
        this.direction = direction;
        
        nombreAngleDroit = getNombreAngleDroit(parent);
    }
    
    private int getNombreAngleDroit(CaseRecherchee parent)
    {
        int angleDroit = 0;
        if(parent != null)
        {
            angleDroit = parent.getNombreAngleDroit();
            if(parent.getDirection() != null)
            {
                if(direction != parent.getDirection())
                    angleDroit++;
            }
        }
        return angleDroit;
    }

    public int getTotal() {
        return total;
    }
    
    public void setTotal(int total) {
        this.total = total;
    }

    public CaseRecherchee getParent() {
        return parent;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CaseRecherchee other = (CaseRecherchee) obj;
        if (this.ligne != other.ligne) {
            return false;
        }
        if (this.colonne != other.colonne) {
            return false;
        }
        return true;
    }

    public int getNombreAngleDroit() 
    {
        return nombreAngleDroit;
    }

    public void setParent(CaseRecherchee parent) {
        this.parent = parent;
    }

    @Override
    public int compareTo(Object o) {
        CaseRecherchee caseAComparer = (CaseRecherchee)o;
        if(this.total>caseAComparer.getTotal())
            return 1;
        else if(this.total == caseAComparer.total)
            return 0;
        else
            return -1;
    }

    @Override
    public String toString() {
        return "CaseRecherchee{" + "lgn=" + ligne + ", col=" + colonne + ", total=" + total + ", nombreAngleDroit=" + nombreAngleDroit + ", direction=" + direction + ", parent=" + parent + '}';
    }

    public void setDistance(int distance) 
    {
        this.total = distance;
    }
}
