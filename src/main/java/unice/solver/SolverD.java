package unice.solver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unice.instance.Instance;
import unice.solution.ISolution;
import unice.solution.SolutionMT;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class SolverD extends Common implements ISolver {
    private static final Logger log = LoggerFactory.getLogger(SolverD.class);

    /**
     * Construit la matrice pour toutes les poids (weights) possible
     * et renvoie une solution ISolution s'il en existe
     *
     * @param instance les objets disponibles
     * @return une solution au problème (les objets choisit)
     */
    private ISolution getSolution(Instance instance) {
        int capacity = instance.getCapacity();
        int numberOfItems = instance.getNumberOfProducts();
        List<Integer> weights = instance.getWeights();

        /**
         * construction de la matrix de tout les possibilités
         * Reference : https://dyclassroom.com/dynamic-programming/0-1-knapsack-problem#:~:text=Time%20complexity%20of%200%201,is%20the%20capacity%20of%20knapsack.
         *
         **/

        int[][] matrix = new int[numberOfItems + 1][capacity + 1];

        for (int item = 1; item <= numberOfItems; item++) {
            for (int itemWeight = 0; itemWeight <= capacity; itemWeight++) {
                int previousItem = weights.get(item - 1);

                /** si on peut pas prendre un nouveau item,
                 *  le weight sera pareil comme l'indice precedent de la matrix */
                matrix[item][itemWeight] = matrix[item - 1][itemWeight];

                /** si le weight de l'item actuel est > ou = celui precedent
                 * et si on peut le prendre sans aller au dela de la capacité alors on met a jour le weight par apport a l'index */
                if ((itemWeight >= previousItem) && (matrix[item - 1][itemWeight - previousItem] + previousItem) > matrix[item][itemWeight]) {
                    matrix[item][itemWeight] = matrix[item - 1][itemWeight - previousItem] + previousItem;
                }
            }
        }

        boolean[] itemsChosen = new boolean[numberOfItems];

        /** on boucle sur toutes les items
         * on initialiser un tableau de boolean itemsChosen a false
         * et si le weight a un index i est different que celui precedent (i-1)
         * on a donc choisi une produit a cette index et on le met true dans le tableau et on enlève son weight du capacité */
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
            SolutionMT solutionMT = new SolutionMT(itemsChosen,instance);
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
    public Optional<ISolution> isFeasible(Instance instance, ISolution solution) {
        /**return true if solution not null else return false*/
        return Optional.ofNullable(getSolution(super.getRemainingItems(instance, solution)));
    }

    @Override
    public String toString()
    {
        return "SolverD";
    }
}