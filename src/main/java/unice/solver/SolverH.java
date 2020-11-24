package unice.solver;

import unice.instance.Instance;
import unice.solution.ISolution;
import unice.solution.SolutionMT;

import java.util.*;

public class SolverH implements ISolver {
    public ISolution solve(Instance I) {
        ArrayList<Integer> items = new ArrayList<>();
        for (int i = 0; i < I.getNumberOfProducts(); i++) items.add(I.getWeights(i));

        boolean[] chosenItems = new boolean[I.getNumberOfProducts()] ;
        //List<Boolean> chosenItems = new ArrayList<>();
        //for (int i = 0; i < I.getNumberOfProducts(); i++) chosenItems.add(false);

        int wCopy = I.getCapacity();
        int chosenItem = 0;

        for (int i = 0, stop = items.size(); i < stop; i++) {
            chosenItem = items.get(0); // We choose the heaviest item
            if (wCopy == 0) { // if the knapsack is full we stop
                break;
            } else if (chosenItem <= wCopy) { // If the item fits the backpack
                // Put it in the backpack
                items.remove(0);

                //chosenItems.set(i, true);
                chosenItems[i] = true ; //add the chosen item to the list of chosen items

                wCopy -= chosenItem;
            } else {
                // Else, don't use it
                items.remove(0);
            }
        }
        List<Boolean> chosenItemsList = new ArrayList<>();
        for(boolean bool:chosenItems) {
            chosenItemsList.add(bool);
        }
        return new SolutionMT(chosenItemsList);
    }

    @Override
    public Iterator<ISolution> getIterator(Instance instance) {
        ArrayList<ISolution> solutions = new ArrayList<>();

        solutions.add(solve(instance));

        return solutions.iterator();
    }

    @Override
    public boolean isFeasible(Instance instance, ISolution solution) {
        return false;
    }
}
