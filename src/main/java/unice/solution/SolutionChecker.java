package unice.solution;

import unice.instance.Instance;
import unice.solver.ISolver;

import java.util.Iterator;

public class SolutionChecker implements ISolutionChecker {

    boolean checkSolution(Instance instance, ISolution solution) {
        int sum = 0;
        for (int i = 0; i < instance.getNumberOfProducts(); i++) {
            sum += solution.take(i) ? instance.getWeights(i) : 0;
        }
        return sum == instance.getCapacity();
    }

    @Override
    public boolean checkValidity(Instance instance, ISolution solution) {

        assert checkSolution(instance, solution) : "Invalid solution";

        return true;
    }


}
