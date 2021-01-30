package unice.solution;


import unice.instance.Instance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolutionMT implements ISolution {

    private final boolean[] chosenItems;
    private Instance instance;

    public SolutionMT(boolean[] chosenItems) {
        this.chosenItems = chosenItems;
    }

    public SolutionMT(boolean[] chosenItems, Instance instance) {
        this.chosenItems = chosenItems;
        this.instance = instance;
    }

    @Override
    public int getN() {
        return this.chosenItems.length;
    }


    public boolean take(int i) {
        return this.chosenItems[i];
    }

    @Override
    public Instance getInstance() {
        return instance;
    }

    @Override
    public boolean[] getChosenItems() {
        return chosenItems;
    }

    @Override
    public int countTrue() {
        int count = 0;
        for (Boolean b : chosenItems) {
            if (b) {
                count++;
            }
        }
        return count;
    }

    @Override
    public List<Integer> getSolution(List<Integer> weight) {
        List<Integer> solution = new ArrayList<>();
        for (int i = 0; i < weight.size(); i++) {
            if (chosenItems[i]) solution.add(weight.get(i));
        }
        return solution;

    }

	@Override
	public String toString() {
		return "SolutionMT [chosenItems=" + Arrays.toString(chosenItems) + "]";
	}
    
    
}
