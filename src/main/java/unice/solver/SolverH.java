package unice.solver;

import unice.Instance;
import unice.Solution;
import unice.SolutionMT;

import java.util.*;

public class SolverH implements Solver {
    public Solution solve(Instance I) {
        boolean[] chosenItems = new boolean[I.getN()];
        for (int i = 0; i < I.getN(); i++) chosenItems[i] = false;

        int wCopy = I.getCapacity();
        int chosenItem;

        for (int i = 0, stop = I.getN(); i < stop; i++) {
            chosenItem = I.getWeight(i); // We choose the heaviest item
            if (wCopy == 0) { // if the knapsack is full we stop
                break;
            } else if (chosenItem <= wCopy) { // If the item fits the backpack
                // Put it in the backpack
                chosenItems[i] = true; //add the chosen item to the list of chosen items
                wCopy -= chosenItem;
            }
        }

        return new SolutionMT(chosenItems);
    }

    @Override
    public Iterator<Solution> getIterator(Instance instance) {
        ArrayList<Solution> solutions = new ArrayList<>();

        solutions.add(solve(instance));

        return solutions.iterator();
    }

    @Override
    public boolean isFeasible(Instance instance, Solution solution) {
        return false;
    }
}
