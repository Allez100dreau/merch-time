package unice.solution;


import java.util.ArrayList;
import java.util.List;

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

    @Override
    public int countTrue(){
        int i = 0 ;
        for (Boolean b: chosenItems) {
            i++;
        }
        return i;
    }
    @Override
    public List<Integer> getSolution(List<Integer> weigth){
         List<Integer> s  = new ArrayList<>();
        for(int i = 0 ; i < weigth.size() ; i++)
        {
            if (chosenItems[i] == true) s.add(weigth.get(i));
        }
        return s ;

    }
}
