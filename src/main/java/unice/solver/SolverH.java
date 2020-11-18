package unice.solver;

import unice.Instance;
import unice.Solution;
import unice.SolutionMT;

import java.util.*;

public class SolverH implements Solver {
    public Solution solve(Instance I) {
        ArrayList<Integer> items = new ArrayList<>();
        for (int i = 0; i < I.getN(); i++) items.add(I.getWeight(i));

        boolean[] chosenItems = new boolean[I.getN()];
        for (int i = 0; i < I.getN(); i++) chosenItems[i] = false;

        int wCopy = I.getCapacity();
        int chosenItem = 0;

        for (int i = 0, stop = items.size(); i < stop; i++) {
            chosenItem = items.get(0); // We choose the heaviest item
            if (wCopy == 0) { // if the knapsack is full we stop
                break;
            } else if (chosenItem <= wCopy) { // If the item fits the backpack
                // Put it in the backpack
                items.remove(0);

                chosenItems[i] = true; //add the chosen item to the list of chosen items
                wCopy -= chosenItem;
            } else {
                // Else, don't use it
                items.remove(0);
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
