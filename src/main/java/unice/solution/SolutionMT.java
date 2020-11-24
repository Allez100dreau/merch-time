package unice.solution;


public class SolutionMT implements ISolution {

    private final boolean[] chosenItems;

    public SolutionMT(boolean[] chosenItems) {
        this.chosenItems = chosenItems;
    }

    @Override
    public int getN() {
        return this.chosenItems.length;
    }

    @Override
    public boolean take(int i) {
        return this.chosenItems[i];
    }

    @Override
    public boolean[] getChosenItems() {
        return chosenItems;
    }
}
