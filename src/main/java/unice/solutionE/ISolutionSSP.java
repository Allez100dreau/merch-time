package unice.solutionE;

import java.util.*;

/**
 * representation d'une solution en liste de boolean
 * (si le produit est prix true sinon false)
 */
public class ISolutionSSP{

    private final List<Boolean> take;

    public ISolutionSSP(List<Boolean> take){
        this.take = take ;
    }


    public List<Boolean> getTake() {
        return take;
    }
}