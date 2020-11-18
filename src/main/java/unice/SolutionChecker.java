package unice;

import java.util.Iterator;

public class SolutionChecker implements ISolutionChecker {

    boolean checkSolution(Instance instance, Solution solution) {
        int sum = 0;
        for (int i = 0; i < instance.getN(); i++) {
            sum += solution.take(i) ? instance.getWeight(i) : 0;
        }
        return sum == instance.getCapacity();
    }

    @Override
    public boolean checkValidity(Instance instance, Iterator<Solution> solutions) {
        while(solutions.hasNext()) {
            assert checkSolution(instance, solutions.next()) : "Invalid solution";
        }
        return true;
    }
}
