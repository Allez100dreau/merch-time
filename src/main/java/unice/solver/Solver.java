package unice.solver;

import unice.Instance;
import unice.Solution;

import java.util.Iterator;

public interface Solver {

    Iterator<Solution> getIterator(Instance instance);

    boolean isFeasible(Instance instance, Solution solution);
}
