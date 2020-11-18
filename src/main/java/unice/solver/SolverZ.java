package unice.solver;

import unice.instance.Instance;
import unice.solution.ISolution;

import java.util.ArrayList;
import java.util.Iterator;

public class SolverZ implements ISolver {
    @Override
    public Iterator<ISolution> getIterator(Instance instance) {
        // On renvoie un ensemble de solutions vide
        ArrayList<ISolution> a = new ArrayList<>();
        return a.iterator();
    }

    @Override
    public boolean isFeasible(Instance instance, ISolution solution) {
        return false;
    }
}
