package unice.solution;


import java.util.List;

public class SolutionMT implements ISolution {

    List<Boolean> chosenItems;

    public SolutionMT(List<Boolean> chosenItems) {
        this.chosenItems = chosenItems;
    }

    @Override
    public int getN() {
        return this.chosenItems.size();
    }

    @Override
    public boolean take(int i) {
        return this.chosenItems.get(i);
    }

    @Override
    public List<Boolean> getChosenItems() {
        return chosenItems;
    }
}
