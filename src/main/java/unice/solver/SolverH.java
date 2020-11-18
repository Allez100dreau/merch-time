package unice.solver;

import unice.Instance;
import unice.Solution;

import java.util.*;

public class SolverH implements Solver {
    @Override
    public int[][] solve(int[] I, int W) {
        try {
            ArrayList<Integer> items_copy = new ArrayList<>();
            for (int n : I) items_copy.add(n);

            Collections.sort(items_copy, Collections.reverseOrder()); // We sort the array from biggest to smallest
            int chosen_item = items_copy.get(0); // We choose the heaviest item

            if (W == 0) { // If there's no space left in the backpack
                // Then return nothing
                return new int[0][0];
            } else if (chosen_item <= W) { // If the item fits the backpack
                // Put it in the backpack
                items_copy.remove(0);

                int[] items_array = new int[items_copy.size()];
                for (int i = 0; i < items_copy.size(); i++) items_array[i] = items_copy.get(i);

                int[][] res = solve(items_array, W - chosen_item);

                int[] res0 = new int[res[0].length+1];
                res0[0] = chosen_item;
                for (int i = 1; i <= res[0].length; i++) res0[i] = res[0][i];
                res[0] = res0;

                return res;
            } else {
                // Else, don't use it
                items_copy.remove(0);

                int[] items_array = new int[items_copy.size()];
                for (int i = 0; i < items_copy.size(); i++) items_array[i] = items_copy.get(i);

                return solve(items_array, W);
            }
        } catch (NoSuchElementException e) { // If there are no more items to add
            // Then return nothing
            return new int[0][0];
        }

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
