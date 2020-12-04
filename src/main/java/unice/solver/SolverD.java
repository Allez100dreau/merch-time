package unice.solver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unice.instance.Instance;
import unice.solution.ISolution;
import unice.solution.SolutionMT;

import java.util.Iterator;
import java.util.List;

public class SolverD extends Common implements ISolver {
    private static final Logger log = LoggerFactory.getLogger(SolverD.class);

    /**
     * Construit la matrice pour toutes les prix possible
     * et renvoie une solution ISolution
     *
     * @param instance les objets disponibles
     * @return une solution au problème (les objets mis dans le sac à dos )
     */
    private ISolution getSolution(Instance instance) {
        int capacity = instance.getCapacity();
        int numberOfItems = instance.getNumberOfProducts();
        List<Integer> weights = instance.getWeights();

        /**
         * construction de la matrix de tout les prix possible
         * Reference : https://dyclassroom.com/dynamic-programming/0-1-knapsack-problem#:~:text=Time%20complexity%20of%200%201,is%20the%20capacity%20of%20knapsack.
         *
         **/

        int[][] matrix = new int[numberOfItems + 1][capacity + 1];

        for (int item = 1; item <= numberOfItems; item++) {
            for (int itemWeight = 0; itemWeight <= capacity; itemWeight++) {
                int previousItem = weights.get(item - 1);

                /** si on peut pas acheter un nouveau item,
                 *  le prix sera celui de l'indice precedent dans la matrix */
                matrix[item][itemWeight] = matrix[item - 1][itemWeight];

                /** si le prix de l'item actuel est > ou = celui precedent
                 * et si on peut l'ajouter sans aller au dela de la limite alors on met a jour le prix par apport a l'index */
                if ((itemWeight >= previousItem) && (matrix[item - 1][itemWeight - previousItem] + previousItem) > matrix[item][itemWeight]) {
                    matrix[item][itemWeight] = matrix[item - 1][itemWeight - previousItem] + previousItem;
                }
            }
        }

        boolean[] itemsChosen = new boolean[numberOfItems];

        /** on boucle sur toutes les items
         * on initialiser un tableau de boolean itemsChosen a false
         * et si le prix possible a payer d'un item i est different que celui precedent (i-1)
         * on a donc choisi cette produit et on le met true dans le tableau et enlève la le prix du budget */
        while (numberOfItems != 0) {
            itemsChosen[numberOfItems - 1] = false;

            if (matrix[numberOfItems][capacity] != matrix[numberOfItems - 1][capacity]) {
                itemsChosen[numberOfItems - 1] = true;
                capacity = capacity - weights.get(numberOfItems - 1);
            }
            numberOfItems--;
        }

        if (capacity == 0) {
            /** log and return the solution */
            SolutionMT solutionMT = new SolutionMT(itemsChosen);
            log.debug("Items chosen : {}", solutionMT.getSolution(weights));
            return solutionMT;
        }

        /** pas de solution possible */
        return null;
    }

    /**
     * @param instance les objets disponibles
     * @return un Iterator sur les objets d'une solution trouver
     */
    @Override
    public Iterator<ISolution> getIterator(Instance instance) {
        return super.getSolutionIterator(getSolution(instance));
    }

    /**
     * Cette méthode indique si c'est possible d'avoir une autre solution avec les objets restantes
     *
     * @param instance les objets disponibles
     * @param solution la solution trouvée
     * @return true : s'il existe une autre solution avec les objets restants
     * false : sinon
     */
    @Override
    public boolean isFeasible(Instance instance, ISolution solution) {
        /**return true if solution not null else return false*/
        return getSolution(super.getRemainingItems(instance, solution)) != null;
    }
}