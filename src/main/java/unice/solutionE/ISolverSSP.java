package unice.solutionE;

import java.util.Iterator;
import java.util.List;

public interface ISolverSSP {
    List<List<Integer>> getSolution(List<List<Integer>> solutions, List<Integer> primerSolution , List<Integer> weight, int capacity) ;
    Iterator<ISolutionSSP> getIterator(Instance instance);
    boolean isFeasible(Instance instance, ISolutionSSP solution);

    }
