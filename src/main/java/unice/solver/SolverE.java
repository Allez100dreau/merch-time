package unice.solver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unice.instance.Instance;
import unice.solution.ISolution;
import unice.solution.SolutionMT;

import java.util.*;

public class SolverE implements ISolver {

    private static final Logger log = LoggerFactory.getLogger(SolverE.class);


    /**
     * Cherche toutes les solutions qui répondent au problème de sac
     * @param solutions Les solutions déjà listées
     * @param took une solution primaire (de base tout les éléments sont à FALSE == aucun élément pris)
     * @param primerSolution la somme de tout les objets déjà pris dans took
     * @param weight une instance (liste de poids d'objets) de base il y a tout les objets entrée dans le solverE
     * @param capacity la capacité à atteindre pour être solution
     * @param indice l'indice dans took qui correspond au premier objet de l'instance weight
     * @return la liste de toutes les solutions trouvée (List<boolean[]>)
     */

    public List<boolean[]> getSolution(List<boolean[]> solutions, boolean[] took, int primerSolution, List<Integer> weight, int capacity, int indice) {
        ////////// SI INSTANCE VIDE ////////////
        if (weight.isEmpty()) {                     // si il n'y a plus d'item
            return solutions;                       // on retourne les solutions actuelles
        }
        ////////// SI INSTANCE EST SOLUTION /////////////////
        boolean[] soluceBool = Arrays.copyOf(took,took.length);// on copie la solution primaire took
        int sum = primerSolution ;                  // sommes de tous les objets pris dans took
        for(int e = 0 ; e < weight.size() ; e++){
            sum+= weight.get(e);                    // on ajoute les poids/prix de tous les objets de l'instance
            soluceBool[indice+e] =  true;           // on set à true à partir de indice (car on estime que l'on prend tout ce qui suit indice)
        }

        int solve = solver(solutions, soluceBool,sum, capacity); // on récupère 0, 1 ou 2 en fonction du solver
        if (solve == 0) {// si 0 alors c'est une nouvelle solution
            solutions.add(soluceBool);              // on l'ajoute à toutes les solutions
            return solutions;                       // on retourne les solutions actuelles car ça ne sert à rien de continuer (il n'existe pas d'item "gratuit")
        }

        ///////// SI INSTANCE LA SOMME DE TOUT LES OBJETS EST INSUFFISANTE ////////////
        else if(solve == 2){                        // avec tout les éléments on ne peut pas atteindre la capacité totale
            return solutions;                       // inutile de chercher plus
        }

        ///////// RECHERCHE APPROFONDIE //////////
        boolean[] nouvelleCombinaisonBool = Arrays.copyOf(took,took.length);   // on crée une liste de la taille de la solution primaire "took"
        int nouvelleCombinaison = primerSolution+weight.get(0); // la somme des poids de la solution primaire + le nouvel élément
        nouvelleCombinaisonBool[indice]  =  true;   // on dis que l'on prend l'élément premier
        weight.remove(0);                        // on supprime ce même élément de tout les items car on va traiter toutes les possibilités avec
        List<Integer> instanceSecondaire = new ArrayList<>(weight); // on crée une autre instance de la taille de l'instance de base -1 car on a supprimé le premier élément
        int saveIndice = indice;                    // on mémorise l'indice actuel pour relancer à l'indice suivant
        solve = solver(solutions,nouvelleCombinaisonBool, nouvelleCombinaison, capacity); // on test si l'ajout du premier élément est une solution

        //////// AJOUT DU PREMIER ELEMENT SOLUTION ///////////
        if (solve == 0) {                           // 0 signifie que c'est une solution
            solutions.add(nouvelleCombinaisonBool); // si oui on l'ajoute aux solutions
        }

        //////// POSSIBILITE DE RAJOUTER DES ELEMENTS ///////////
        else if (solve == 2) {                      // 2 signifie que l'on est en dessous de la capacité donc on peut rajouter des éléments
            if (!instanceSecondaire.isEmpty()) {    // si il reste des éléments à ajouter
                for (int i = 0; i < instanceSecondaire.size(); i++) { // pour chaque éléments restants
                    indice++;                       // on incrémente l'indice
                    int combinaisonSecondaire = nouvelleCombinaison; // on crée un nouvel accumulateur

                    boolean[] combinaisonSecondaireBool = Arrays.copyOf(nouvelleCombinaisonBool,nouvelleCombinaisonBool.length); // on copie la solution actuelle

                    combinaisonSecondaire+= instanceSecondaire.get(i); // on ajoute le poids de l'élément en question à l'accumulateur
                    combinaisonSecondaireBool[indice] = true;          // on dit que l'on prend cette item
                    instanceSecondaire.remove(i);   // on le supprime de l'instance secondaire car on va traiter toutes les possibilités avec
                    i--;                            // on décrémente l'indice de la boucle for pour ne manquer aucun éléments
                    solve = solver(solutions,combinaisonSecondaireBool, combinaisonSecondaire, capacity); // on récupère la réponse de solver (0,1 ou 2)

                    //////// UNE SOLUTION DE TAILLE 2 //////////
                    if (solve == 0) {               // c'est une solution
                        solutions.add(combinaisonSecondaireBool); // on l'ajoute
                        continue;                   // on skip et on passe aux éléments suivants car on a atteint la capacité (inutile de rajouter des items)
                    }
                    /////// ON A DEPASSE LE MAX /////////
                    else if (solve == 1) {          // on a dépassé la capacité inutile d'ajouter des éléments
                        continue;                   // on skip aussi
                    }
                    /////// ON CHERCHE PLUS LOIN //////
                    getSolution(solutions, combinaisonSecondaireBool,combinaisonSecondaire,instanceSecondaire,capacity,indice+1); // il y a encore de la capacité donc on continue avec la solution, accumulateur, instance actuelle et on va donc ajouter d'autres éléments (à partir de l'indice suivant)
                }
            }
        }
        //////// ON RECOMMENCE EN PARTANT DE L'ELEMENT SUIVANT /////////
        return getSolution(solutions,took, 0, weight, capacity,saveIndice+1); // on recommence à partir de l'indice suivant car toutes les combinaisons possible avec le premier élément de l'instance on été testé
    }


    /**
     * Détermine l'état de la solution entrée en paramètre
     * @param solutions les solutions déjà listées
     * @param combination la combinaison que l'on test
     * @param total le total de la combinaison
     * @param capacity la capacité à atteindre
     * @return un int représentant un état
     * 0 : capacité atteinte et solution non listée
     * 1 : capacité dépassée ou solution déjà listée
     * 2 : capacité non atteinte
     */

    private static int solver(List<boolean[]> solutions, boolean[] combination,int total, int capacity) {
        ////// CAPACITE NON ATTEINTE /////
        if (capacity > total) {
            return 2;
        }
        ////// CAPACITE ATTEINTE /////
        if (capacity == total && !solutions.contains(combination)) { // solution et elle n'est pas encore listée
            return 0;
        }
        ////// CAPACITE DEPASSEE OU SOLUTION DEJA LISTEE/////
        return 1;
    }


    /**
     * @param instance les données saisis
     * @return une itération sur les solutions trouvées
     */
    @Override
    public Iterator<ISolution> getIterator(Instance instance) {
        ArrayList<ISolution> solve = new ArrayList<>();
        List<boolean[]> solutions = new ArrayList<>();
        List<Integer> weight = new ArrayList<>(instance.getWeights());
        boolean[] took = new boolean[instance.getNumberOfProducts()];
        for(int i = 0 ; i< instance.getNumberOfProducts();i++){
            took[i] = false ;
        }
        getSolution(solutions,took, 0, weight, instance.getCapacity(), 0);

        //log.debug("Les solutions possible : {}", solutions);

        for (boolean[] solution : solutions) {
            ISolution solutionS = new SolutionMT(solution);
            solve.add(solutionS);
        }
        return solve.iterator();
    }

    /**
     * Methode recursive
     *
     * @param weights            liste des restes des prix qui va subir la récursivité
     * @param nbRemainingProduct nombre de produit restant
     * @param capacity           budget
     * @return retourne la somme la plus proche du budget que peut former le reste des prix
     */
    public int knapsackRec(List<Integer> weights, int nbRemainingProduct, int capacity) {
        if (nbRemainingProduct <= 0) {
            return 0;
        } else if (weights.get(nbRemainingProduct - 1) > capacity) {
            return knapsackRec(weights, nbRemainingProduct - 1, capacity);
        } else {
            return Math.max(knapsackRec(weights, nbRemainingProduct - 1, capacity),
                    weights.get(nbRemainingProduct - 1) + knapsackRec(weights, nbRemainingProduct - 1, capacity - weights.get(nbRemainingProduct - 1)));
        }
    }

    /**
     * cette méthode calcule la possibilité d'avoir une nouvelle solution avec les produits restants
     *
     * @param instance l'ensemble des données
     * @param solution la solution qu'on a choisi (produits pris)
     * @return l'existence d'une autre solution avec le produit restant
     */
    @Override
    public boolean isFeasible(Instance instance, ISolution solution) {
        List<Integer> rest = new ArrayList<>();
        //On ajoute tous les produits non pris dans la solution au "reste"
        for (int i = 0; i < instance.getNumberOfProducts(); i++) {
            if (!solution.take(i)) {
                rest.add(instance.getWeights(i));
            }
        }

        boolean[] took = new boolean[instance.getNumberOfProducts()];
        for(int i = 0 ; i< instance.getNumberOfProducts();i++){
            took[i] = false ;
        }
        return knapsackRec(rest, rest.size(), instance.getCapacity()) == instance.getCapacity();
    }


}