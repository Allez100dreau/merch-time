package unice.solution;

import java.util.List;

/**
 * representation d'une solution en liste de boolean
 * (si le produit est prix true sinon false)
 */

public interface ISolution {

    int getN();

    boolean take(int i);

    List<Boolean> getChosenItems();

}
