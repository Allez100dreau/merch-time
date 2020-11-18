package unice;

public class SolutionMT implements Solution {

    boolean[] chosenItems;

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
}
