package unice.solver;

import unice.instance.Instance;
import unice.solution.ISolution;
import unice.solution.SolutionMT;

import java.util.*;

/**
 * Cette classe représente une méthode approchée pour le problème de décision H
 */
public class SolverH extends Common implements ISolver {
    /**
     * Méthode itérative gloutonne pour la résolution du problème Merch time
     * @param instance les objets disponibles
     * @return une solution au problème (les objets mis dans le sac à dos )
     */
    public ISolution solve(Instance instance) {
        boolean[] chosenItems = new boolean[instance.getNumberOfProducts()];

        int knapSackWeight = 0;
        int chosenItem;

        for (int i = 0 ; i < instance.getNumberOfProducts(); i++) {

            chosenItem = instance.getWeights(i); // We choose the heaviest item

            if (chosenItem + knapSackWeight <= instance.getCapacity()) { // If the item fits the backpack
                // Put it in the backpack
                chosenItems[i] = true; // Add the chosen item to the list of chosen items
                knapSackWeight += chosenItem;

                // If the backpack if full, we have our solution !
                if (knapSackWeight == instance.getCapacity()) return new SolutionMT(chosenItems, instance);
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
    public Optional<ISolution> isFeasible(Instance instance, ISolution solution) {
        return Optional.ofNullable(solve(super.getRemainingItems(instance, solution)));
    }

    @Override
    public String toString()
    {
        return "SolverH";
    }
}
