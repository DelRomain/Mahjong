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

    private final int x,y;
    private int total;
    private CaseRecherchee parent;
    private int nombreAngleDroit;
    private final CaseAdjacente direction;

    public CaseAdjacente getDirection() {
        return direction;
    }

    
    public CaseRecherchee(CaseRecherchee parent,int x, int y, CaseAdjacente direction) {
        this.parent = parent; 
        this.x = x;
        this.y = y;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
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
        return "CaseRecherchee{" + "x=" + x + ", y=" + y + ", total=" + total + ", nombreAngleDroit=" + nombreAngleDroit + ", direction=" + direction + ", parent=" + parent + '}';
    }

    void setDistance(int distance) 
    {
        this.total = distance;
    }
}
