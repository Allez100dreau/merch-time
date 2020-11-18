package unice.solutionE;

import java.util.*;

public class SolverE implements  ISolverSSP{


    /**
     *
     * @param solutions liste des solutions possibles
     * @param combination une nouvelle combinaison qui peut êtres une futur solution
     * @param capacity le budget
     * @return
     * true :  si la combinaison est une nouvelle solution
     * false : sinon
     */

    private static int solver(List<List<Integer>> solutions, List<Integer> combination, int capacity) {
        for(int i = 0 ; i<combination.size() ; i++){
            capacity -= combination.get(i);
        }
        if(capacity < 0){
            return 1;
        }
        if(capacity == 0 && !solutions.contains(combination)){
            return 0;
        }

        return 2;
    }

    /**
     * Méthode recursive :
     * tant qu'il reste des produit non acheter on boucle pour chercher une solution
     *
     * @param solutions liste de combinaison de prix (de somme = à la capacité) qui forme une solution
     * @param primerSolution
     * @param weight les prix des produit qui ne font pas partie de la solution encore
     * @param capacity le budget
     * @return la liste des solutions possible
     */

    public List<List<Integer>> getSolution(List<List<Integer>> solutions, List<Integer> primerSolution , List<Integer> weight, int capacity) {
        if(weight.size() == 0) {
            return solutions;
        }
        int W = solver(solutions,weight,capacity);
        if(W == 0){
            //LoggerMT.show(weight);
            solutions.add(weight);
            return solutions;
        }
        List<Integer> nouvelleCombinaison = new ArrayList<>(primerSolution);
        nouvelleCombinaison.add(weight.get(0));
        weight.remove(0);
        List<Integer> instanceSecondaire = new ArrayList<>(weight);
        //LoggerMT.show(nouvelleCombinaison);
        W = solver(solutions,nouvelleCombinaison,capacity);
        if(W == 0){
            solutions.add(nouvelleCombinaison);
        }
        else if(W == 2) {
            if (instanceSecondaire.size() > 0) {
                for (int i = 0; i < instanceSecondaire.size(); i++) {
                    List<Integer> combinaisonSecondaire = new ArrayList<>(nouvelleCombinaison);
                    combinaisonSecondaire.add(instanceSecondaire.get(i));
                    instanceSecondaire.remove(i);
                    i--;
                    //LoggerMT.show(combinaisonSecondaire);
                    W = solver(solutions, combinaisonSecondaire, capacity);
                    if (W == 0) {
                        solutions.add(combinaisonSecondaire);
                        continue;
                    }else if (W == 1){
                        continue;
                    }

                    solutions = getSolution(solutions, combinaisonSecondaire, instanceSecondaire, capacity);
                }
            }
        }

        int wMAJUSCULE = solver(solutions,weight,capacity);
        if(wMAJUSCULE == 2){
            return solutions;
        }
        return getSolution(solutions,primerSolution,weight,capacity);
    }

    /**
     *
     * @param instance les données saisis
     * @return une itération sur les produit prix pour une solution retrouver
     */
    public Iterator<ISolutionSSP> getIterator(Instance instance){
        ArrayList<ISolutionSSP> solve = new ArrayList<>();
        List<List<Integer>> solutions = new ArrayList<>();
        List<Integer> weight = new ArrayList<>(instance.getWeight());
        Collections.sort(weight);
        Collections.reverse(weight);

        getSolution(solutions, new ArrayList<Integer>(), weight ,instance.getCapacity());
        //LoggerMT.show(solutions);
        for (List<Integer> solution : solutions) {
            List<Boolean> takes = new ArrayList<>();
            for (int e = 0; e < instance.getNumberOfProducts(); e++) {
                boolean isTaken = false;
                for (int k = solution.size() - 1; k >= 0; k--) {
                    if (instance.getWeight(e) == solution.get(k)) {
                        isTaken = true;
                        solution.remove(k);
                        break;
                    }
                }
                takes.add(isTaken);
            }
            ISolutionSSP solutionSSP = new ISolutionSSP(takes);
            solve.add(solutionSSP);
        }
        return solve.iterator();
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

    /**
     * cette méthode calcule la possibilité d'avoir une nouvelle solution avec les produits restants
     * @param instance l'ensemble des données
     * @param solution la solution qu'on a choisi (produits pris)
     * @return l'existence d'une autre solution avec le produit restant
     */
    public boolean isFeasible(Instance instance, ISolutionSSP solution){
        List<Integer> rest = new ArrayList<>();
        //On ajoute tous les produits non pris dans la solution au "reste"
        for(int i = 0; i < instance.getNumberOfProducts() ; i++){
            if(!solution.getTake().get(i)){
                rest.add(instance.getWeight(i));
            }
        }
        return knapsackRec(rest, rest.size(), instance.getCapacity()) == instance.getCapacity();
    }

}