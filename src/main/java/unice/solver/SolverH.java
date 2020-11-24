package unice.solver;

import unice.instance.Instance;
import unice.instance.InstanceMT;
import unice.solution.ISolution;
import unice.solution.SolutionMT;

import java.util.*;

/**
 * Cette classe représente une méthode approchée pour le problème de décision H
 */
public class SolverH implements ISolver {
    /**
     * Méthode itérative gloutonne pour la résolution du problème Merch time
     * @param I les objets disponibles
     * @return une solution au problème (les objets mis dans le sac à dos )
     */
    public ISolution solve(Instance I) {
        ArrayList<Integer> items = new ArrayList<>();
        for (int i = 0; i < I.getNumberOfProducts(); i++) items.add(I.getWeights(i));

        boolean[] chosenItems = new boolean[I.getNumberOfProducts()] ;

        int wCopy = I.getCapacity();
        int chosenItem;

        for (int i = 0, stop = items.size(); i < stop; i++) {
            chosenItem = items.get(0); // We choose the heaviest item
            if (wCopy == 0) { // if the knapsack is full we stop
                break;
            } else if (chosenItem <= wCopy) { // If the item fits the backpack
                // Put it in the backpack
                items.remove(0);

                chosenItems[i] = true ; //add the chosen item to the list of chosen items

                wCopy -= chosenItem;
            } else { // Else, don't use it
                items.remove(0);
            }
        }
        return new SolutionMT(chosenItems);
    }

    /**
     * @param instance les objets disponibles
     * @return un Iterator sur les objets d'une solution trouver
     */
    @Override
    public Iterator<ISolution> getIterator(Instance instance) {
        ArrayList<ISolution> solutions = new ArrayList<>();

        solutions.add(solve(instance));

        return solutions.iterator();
    }

    /**
     * Cette méthode indique si c'est possible d'avoir une nouvelle solution avec les objets restants
     * @param instance les objets disponibles
     * @param solution la solution trouvée
     * @return true : s'il existe une autre solution avec les objets restants
     *         false : sinon
     */
    @Override
    public boolean isFeasible(Instance instance, ISolution solution) {
        List<Integer> remainingItems = new ArrayList<>();
        for (int i = 0; i < instance.getNumberOfProducts(); i++) {
            if (!solution.take(i)) {
                remainingItems.add(instance.getWeights(i));
            }
        }
        int remainingNumberOfProducts = remainingItems.size() ;
        Instance remainingInstance = new InstanceMT(instance.getCapacity() , remainingNumberOfProducts , remainingItems ) ;

        int capacity2 = 0 ; //capacity of the chosen Items from the remaining items
        boolean[] chosenItemsFromRemainingItems = solve(remainingInstance).getChosenItems() ;
        for (int i = 0; i < chosenItemsFromRemainingItems.length; i++) {
            if (chosenItemsFromRemainingItems[i]){ //if the item is taken we add it's weight to the capacity
                capacity2 += remainingInstance.getWeights() .get(i) ;
            }
        }
        return capacity2 == instance.getCapacity() ;
    }
}
