package unice;

import java.util.Iterator;

public interface Solver {

    Iterator<Solution> getIterator(Instance instance);

    boolean isFeasible(Instance instance, Solution solution);
}
