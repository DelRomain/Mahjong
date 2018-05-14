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
public class CaseRecherchee {

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
        
        nombreAngleDroit = parent != null? 
                parent.getNombreAngleDroit() + (direction != parent.getDirection()? 1 : 0 ):
                0;
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

    
}
