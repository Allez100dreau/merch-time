package unice.solver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unice.instance.Instance;
import unice.instance.InstanceMT;
import unice.solution.ISolution;
import unice.solution.SolutionMT;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SolverD implements ISolver {

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
            for (int itemPrice = 0; itemPrice <= capacity; itemPrice++) {
                int previousItem = weights.get(item - 1);

                /** si on peut pas acheter un nouveau item,
                 *  le prix sera celui de l'indice precedent dans la matrix */
                matrix[item][itemPrice] = matrix[item - 1][itemPrice];

                /** si le prix de l'item actuel est > ou = celui precedent
                 * et si on peut l'ajouter sans aller au dela de la limite alors on met a jour le prix par apport a l'index */
                if ((itemPrice >= previousItem) && (matrix[item - 1][itemPrice - previousItem] + previousItem) > matrix[item][itemPrice]) {
                    matrix[item][itemPrice] = matrix[item - 1][itemPrice - previousItem] + previousItem;
                }
            }
        }

        /** list chosen used for log only */
        List<Integer> chosen = new ArrayList<>();

        boolean[] itemsChosen = new boolean[numberOfItems];

        /** on boucle sur toutes les items
         * on initialiser un tableau de boolean itemsChosen a false
         * et si le prix possible a payer d'un item i est different que celui precedent (i-1)
         * on a donc choisi cette produit et on le met true dans le tableau et enlève la le prix du budget */
        while (numberOfItems != 0) {
            itemsChosen[numberOfItems - 1] = false;

            if (matrix[numberOfItems][capacity] != matrix[numberOfItems - 1][capacity]) {
                itemsChosen[numberOfItems - 1] = true;

                /** list chosen used for log only */
                chosen.add(weights.get(numberOfItems - 1));

                capacity = capacity - weights.get(numberOfItems - 1);
            }
            numberOfItems--;
        }

        /** used for log only: log items only if remaining capacity is 0 i.e bag has been filled completely */
         if (capacity == 0) {
            log.debug("Items chosen : {}", chosen);
        }

        return new SolutionMT(itemsChosen);
    }

    /**
     * @param instance les objets disponibles
     * @return un Iterator sur les objets d'une solution trouver
     */
    @Override
    public Iterator<ISolution> getIterator(Instance instance) {
        ArrayList<ISolution> solutions = new ArrayList<>();
        solutions.add(getSolution(instance));
        return solutions.iterator();
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

        /**
         * pour chaque item non choisi dans la solution (retourne false)
         * on les ajoute dans une liste des items restantes
         */
        List<Integer> remainingItems = new ArrayList<>();
        for (int i = 0; i < instance.getNumberOfProducts(); i++) {
            if (!solution.take(i)) {
                remainingItems.add(instance.getWeights(i));
            }
        }


        int remainingNoOfProducts = remainingItems.size();
        Instance remainingInstance = new InstanceMT(instance.getCapacity(), remainingNoOfProducts, remainingItems);

        /**initialisation du nouveau capacité max des items restantes*/
        int newSolCapacity = 0;

        boolean[] newChosenItems = getSolution(remainingInstance).getChosenItems();
        for (int i = 0; i < newChosenItems.length; i++) {
            /**si l'item a l'indice i a ete choice (true) on ajoute son prix a newSolCapacity*/
            if (newChosenItems[i]) {
                newSolCapacity += remainingInstance.getWeights().get(i);
            }
        }

        return newSolCapacity == instance.getCapacity();
    }
}