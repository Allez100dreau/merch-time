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
                chosenItems[i] = true; // Add the chosen item to the list of chosen items
                knapSackWeight += chosenItem;

                // If the backpack if full, we have our solution !
                if (knapSackWeight == I.getCapacity()) return new SolutionMT(chosenItems);
            }
        }

        // If we couldn't return a solution, then there is none !
        return null;
    }


    /**
     * @param instance les objets disponibles
     * @return un Iterator sur la solution trouvée.
     */
    @Override
    public Iterator<ISolution> getIterator(Instance instance) {
        return super.getSolutionIterator(solve(instance));
    }

    /**
     * Cette méthode indique s'il est possible d'avoir une nouvelle solution avec les objets restants
     * @param instance les objets disponibles
     * @param solution la solution trouvée
     * @return true : s'il existe une autre solution avec les objets restants
     *         false : sinon
     */
    @Override
    public boolean isFeasible(Instance instance, ISolution solution) {
        return solve(super.getRemainingItems(instance, solution)) != null;
    }
}
