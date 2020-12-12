package unice.solver;

import unice.instance.Instance;
import unice.instance.InstanceMT;
import unice.solution.ISolution;
import unice.solution.SolutionMT;

import java.util.*;

/**
 * Cette classe représente une méthode approchée pour le problème de décision H
 */
public class SolverH extends Common implements ISolver {
    /**
     * Méthode itérative gloutonne pour la résolution du problème Merch time
     * @param I les objets disponibles
     * @return une solution au problème (les objets mis dans le sac à dos )
     */
    public ISolution solve(Instance I) {
        boolean[] chosenItems = new boolean[I.getNumberOfProducts()];

        int knapSackWeight = 0;
        int chosenItem;

        for (int i = 0 ; i < I.getNumberOfProducts(); i++) {
            chosenItem = I.getWeights(i); // We choose the heaviest item
            if (chosenItem + knapSackWeight <= I.getCapacity()) { // If the item fits the backpack
                // Put it in the backpack
                chosenItems[i] = true; //add the chosen item to the list of chosen items
                knapSackWeight += chosenItem;
            }else if (knapSackWeight == I.getCapacity()) { // if the knapsack is full we stop
                break;
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
        return super.getSolutionIterator(solve(instance));
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
        return solve(super.getRemainingItems(instance, solution)).getSolution(instance.getWeights()).size() > 0 ;
    }
}
