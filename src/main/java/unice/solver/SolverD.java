package unice.solver;

import unice.instance.Instance;
import unice.logger.LoggerMT;
import unice.solution.ISolution;
import unice.solution.SolutionMT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SolverD implements ISolver {

    /**
     * Méthode recursive :
     * tant qu'il reste des produit non acheter on boucle pour chercher une solution
     *
     * @param solutions liste de combinaison de prix (de somme = à la capacité) qui forme une solution
     * @param weight    les prix des produit qui ne font pas partie de la solution encore
     * @param capacity  le budget
     * @return la liste des solutions possible
     */

    public List<List<Integer>> getSolution(List<List<Integer>> solutions, List<Integer> weight, int capacity) {
        int oldCapacity = capacity;
        int numberOfItems = weight.size();

        int item, itemWeight;

        /**
         * la matrix de tout les choix
         *
         **/
        int[][] matrix = new int[numberOfItems + 1][capacity + 1];


        for (item = 0; item <= numberOfItems; item++) {
            for (itemWeight = 0; itemWeight <= capacity; itemWeight++) {
                if (item == 0 || itemWeight == 0) {
                    //la premiere fois (premiere ligne duu matrix tout est 0)
                    matrix[item][itemWeight] = 0;
                } else if (weight.get(item - 1) <= itemWeight) {
                    matrix[item][itemWeight] = Math.max(weight.get(item - 1) + matrix[item - 1][itemWeight - weight.get(item - 1)], matrix[item - 1][itemWeight]);
                } else {
                    matrix[item][itemWeight] = matrix[item - 1][itemWeight];
                }
            }
        }
/*
        System.out.println();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
            System.out.println();
        }
*/

        //le dernier element est celui qui contient la meilleure solution
        int maxWeight = matrix[numberOfItems][capacity];
//        System.out.println(maxWeight);

        List<Integer> combinaison = new ArrayList<>();

        for (int i = numberOfItems; i > 0 && maxWeight > 0; i--) {

            if (maxWeight == matrix[i - 1][capacity]) {
                continue;
            } else {
                //System.out.print(weight.get(i - 1) + " ");
                //on boucle sur toutes les produits et on ajoute celui la combinaison la plus proche du capacite dans combinaison
                combinaison.add(weight.get(i - 1));
                maxWeight = maxWeight - weight.get(i - 1);
                capacity = capacity - weight.get(i - 1);
            }
        }

        /**
         * on verifie que la sum des combinaison obtenu est bien la capacite du sac
         * sinon on peut avoir des solution qui sont moins de la taille du sac
         */
        int sum = 0;
        for (Integer i : combinaison) {
            sum += i;
        }
        if (sum == oldCapacity) {
            Collections.sort(combinaison);
            //on ajoute la combinaison dans une liste des combinaison possible
            solutions.add(combinaison);
            for (Integer i : combinaison) {
                /**
                 * on enleve les produits qui se trouve dans la combinaison choisi pour la
                 * methode recursive puisse trouver si il existe une autre combinaison
                 * possible avec les produits restants
                 **/
                weight.remove(i);
            }
            //recursive
            return getSolution(solutions, weight, oldCapacity);
        }
        //si il existe pas d'autre solutions, retourner la liste des solutions obtenu
        return solutions;
    }

    /**
     * @param instance les données saisis
     * @return une itération sur les produit prix pour une solution retrouver
     */
    @Override
    public Iterator<ISolution> getIterator(Instance instance) {
        ArrayList<ISolution> solve = new ArrayList<>();
        List<List<Integer>> solutions = new ArrayList<>();
        List<Integer> weight = new ArrayList<>(instance.getWeights());
        Collections.sort(weight);

        getSolution(solutions, weight, instance.getCapacity());

        LoggerMT.show("Les solutions possibles :" + solutions);

        for (List<Integer> solution : solutions) {
            List<Boolean> takes = new ArrayList<>();

            for (int e = 0; e < instance.getNumberOfProducts(); e++) {
                boolean isTaken = false;
                for (int k = solution.size() - 1; k >= 0; k--) {
                    if (instance.getWeights(e) == solution.get(k)) {
                        isTaken = true;
                        solution.remove(k);
                        break;
                    }
                }
                takes.add(isTaken);
            }
            ISolution solutionS = new SolutionMT(takes);
            solve.add(solutionS);
        }
        return solve.iterator();
    }

    @Override
    public boolean isFeasible(Instance instance, ISolution solution) {
        List<Integer> rest = new ArrayList<>();
        //On ajoute tous les produits non pris dans la solution au "reste"
        for(int i = 0; i < instance.getNumberOfProducts() ; i++){
            if(!solution.take(i)){
                rest.add(instance.getWeights(i));
            }
        }
        return knapsackRec(rest, rest.size(), instance.getCapacity()) == instance.getCapacity();
    }


    /**
     * Methode recursive
     * @param weights liste des restes des prix qui va subir la récursivité
     * @param nbRemainingProduct nombre de produit restant
     * @param capacity budget
     * @return retourne la somme la plus proche du budget que peut former le reste des prix
     */
    public int knapsackRec(List<Integer> weights, int nbRemainingProduct, int capacity) {
        if (nbRemainingProduct <= 0) {
            return 0;
        } else if (weights.get(nbRemainingProduct - 1) > capacity) {
            return knapsackRec(weights, nbRemainingProduct - 1, capacity);
        } else {
            return Math.max(knapsackRec(weights, nbRemainingProduct - 1, capacity),
                    weights.get(nbRemainingProduct - 1) + knapsackRec(weights,  nbRemainingProduct - 1, capacity - weights.get(nbRemainingProduct - 1)));
        }
    }
}