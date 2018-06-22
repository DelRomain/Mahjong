package mahjong.RechercheChemin;

import java.util.Objects;
import mahjong.GestionTuile.Coordonnees;

/**
 * Classe permetant de faire la recherche de chemin. Elle contient le parent de
 * la case en cour, le nombre d'angle droit et la distance jusqu'à la tuile
 * cible.
 */
public class CaseRecherchee implements Comparable {

    private final Coordonnees emplacement;
    private int distance;
    private CaseRecherchee parent;
    private int nombreAngleDroit;
    private final Direction direction;

    public CaseRecherchee(CaseRecherchee parent, Coordonnees emplacement, Direction direction) {
        this.parent = parent;
        this.emplacement = emplacement;
        this.direction = direction;

        nombreAngleDroit = getNombreAngleDroit(parent);
    }

    private int getNombreAngleDroit(CaseRecherchee parent) {
        int angleDroit = 0;
        if (parent != null) {
            angleDroit = parent.getNombreAngleDroit();
            if (parent.getDirection() != null) {
                if (direction != parent.getDirection()) {
                    angleDroit++;
                }
            }
        }
        return angleDroit;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    public void setParent(CaseRecherchee parent) {
        this.parent = parent;
    }

    public CaseRecherchee getParent() {
        return parent;
    }

    public int getLigne() {
        return emplacement.getLigne();
    }

    public int getColonne() {
        return emplacement.getColonne();
    }

    public Coordonnees getEmplacement() {
        return emplacement;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setNombreAngleDroit(int nombreAngleDroit) {
        this.nombreAngleDroit = nombreAngleDroit;
    }

    public int getNombreAngleDroit() {
        return nombreAngleDroit;
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
        if (!Objects.equals(this.emplacement, other.emplacement)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object o) {
        CaseRecherchee caseAComparer = (CaseRecherchee) o;
        if (this.distance > caseAComparer.getDistance()) {
            return 1;
        } else if (this.distance == caseAComparer.distance) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "CaseRecherchee{" + "lgn=" + emplacement.getLigne() + ", col=" + emplacement.getColonne() + ", total=" + distance + ", nombreAngleDroit=" + nombreAngleDroit + ", direction=" + direction + ", parent=" + parent + '}';
    }
}
