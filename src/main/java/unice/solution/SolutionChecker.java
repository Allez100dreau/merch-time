package unice.solution;

import unice.instance.Instance;

public class SolutionChecker implements ISolutionChecker {

    boolean checkSolution(Instance instance, ISolution solution) {
        int sum = 0;
        for (int i = 0; i < solution.getInstance().getNumberOfProducts(); i++) {
            sum += solution.take(i) ? instance.getWeights(i) : 0;
        }
        return sum == instance.getCapacity();
    }

    @Override
    public boolean checkValidity(Instance instance, ISolution solution) {

        if(checkSolution(instance, solution)){
            return true;
        }else{
            return false;
        }
    }


}
