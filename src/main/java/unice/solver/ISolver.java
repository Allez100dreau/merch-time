package unice.solver;

import unice.instance.Instance;
import unice.solution.ISolution;

import java.util.Iterator;

public interface ISolver {

    Iterator<ISolution> getIterator(Instance instance);

    boolean isFeasible(Instance instance, ISolution solution);
}