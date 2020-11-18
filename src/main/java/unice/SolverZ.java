package unice;

import java.util.ArrayList;
import java.util.Iterator;

public class SolverZ implements Solver{
    @Override
    public Iterator<Solution> getIterator(Instance instance) {
        // On renvoie un ensemble de solutions vide
        ArrayList<Solution> a = new ArrayList<>();
        return a.iterator();
    }

    @Override
    public boolean isFeasible(Instance instance, Solution solution) {
        return false;
    }
}
