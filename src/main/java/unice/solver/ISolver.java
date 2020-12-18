package unice.solver;

import unice.instance.Instance;
import unice.solution.ISolution;

import java.util.Iterator;
import java.util.Optional;

public interface ISolver {

    Iterator<ISolution> getIterator(Instance instance);

    Optional<ISolution> isFeasible(Instance instance, ISolution solution);
}
