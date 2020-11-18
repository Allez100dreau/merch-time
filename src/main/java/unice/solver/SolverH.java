package unice.solver;

import unice.Instance;
import unice.Solution;

import java.util.*;

public class SolverH implements Solver {
    public int[][] solve(int[] I, int W) {
        ArrayList<Integer> itemsCopy = new ArrayList<>();
        for (int n : I) itemsCopy.add(n);

        Collections.sort(itemsCopy, Collections.reverseOrder()); // We sort the array from biggest to smallest
        int chosenItem = 0;

        ArrayList<Integer> chosenItems = new ArrayList<>();
        int wCopy = W;
        for (int i = 0, stop = itemsCopy.size(); i < stop; i++) {
            chosenItem = itemsCopy.get(0); // We choose the heaviest item
            if (wCopy == 0) { // if the knapsack is full we stop
                break;
            } else if (chosenItem <= wCopy) { // If the item fits the backpack
                // Put it in the backpack
                itemsCopy.remove(0);

                chosenItems.add(chosenItem); //add the chosen item to the list of chosen items
                wCopy -= chosenItem;
            } else {
                // Else, don't use it
                itemsCopy.remove(0);
            }
        }
        //Converting chosenItems arraylist to an array
        int[] res0 = new int[chosenItems.size()] ;
        for (int i = 0; i < chosenItems.size(); i++) {
            res0[i] = chosenItems.get(i) ;
        }
        int[][] res = new int[1][res0.length] ;
        res[0] = res0 ;

        return res ;
    }

    @Override
    public Iterator<Solution> getIterator(Instance instance) {
        return null;
    }

    @Override
    public boolean isFeasible(Instance instance, Solution solution) {
        return false;
    }
}
