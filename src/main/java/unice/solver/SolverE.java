package unice.solver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unice.instance.Instance;
import unice.instance.InstanceMT;
import unice.solution.ISolution;
import unice.solution.SolutionMT;

import java.util.*;

public class SolverE extends Common implements ISolver {

    private static final Logger log = LoggerFactory.getLogger(SolverE.class);
    private List<Integer> localWeight;

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


    public List<boolean[]> getSolution(List<boolean[]> solutions, boolean[] took, int primerSolution, List<Integer> weight, int capacity, int indice,boolean restart,List<List<Integer>> combination) {
        ////////// SI INSTANCE VIDE ////////////
        if (weight.isEmpty()) {                     // si il n'y a plus d'items
            return solutions;                       // on retourne les solutions actuelles
        }
        int stockElement = weight.get(0);
        ////////// SI INSTANCE EST SOLUTION /////////////////
        boolean[] soluceBool = Arrays.copyOf(took,took.length);// on copie la solution primaire took
        int soluce = primerSolution ;               // sommes de tous les objets pris dans took
        for(int e = 0 ; e < weight.size() ; e++){
            soluce+= weight.get(e);                 // on ajoute les poids/prix de tous les objets de l'instance
            soluceBool[indice+e] =  true;           // on set à true à partir de indice (car on estime que l'on prend tout ce qui suit indice)
        }
        int solve = solver(solutions, soluceBool,soluce, capacity,combination); // on récupère 0, 1 ou 2 en fonction du solver
        if (solve == 0) {                           // si 0 alors c'est une nouvelle solution
            solutions.add(soluceBool);              // on l'ajoute à toutes les solutions
            combination.add(convert(soluceBool));
            return solutions;                       // on retourne les solutions actuelles car ça ne sert à rien de continuer (il n'existe pas d'item "gratuit")
        }

        ///////// SI INSTANCE LA SOMME DE TOUT LES OBJETS EST INSUFFISANTE ////////////
        else if(solve == 2){                        // avec tout les éléments on ne peut pas atteindre la capacité totale
            return solutions;                       // inutile de chercher plus
        }

        ///////// RECHERCHE APPROFONDIE //////////
        boolean[] nouvelleCombinaisonBool = Arrays.copyOf(took,took.length);   // on crée une liste de la taille de la solution primaire "took"
        int nouvelleCombinaison = primerSolution + weight.get(0); // la somme des poids de la solution primaire + le nouvel élément
        nouvelleCombinaisonBool[indice]  =  true;   // on dis que l'on prend l'élément premier
        weight.remove(0);                     // on supprime ce même élément de tout les items car on va traiter toutes les possibilités avec
        List<Integer> instanceSecondaire = new ArrayList<>(weight); // on crée une autre instance de la taille de l'instance de base -1 car on a supprimé le premier élément
        int saveIndice = indice;                    // on mémorise l'indice actuel pour relancer à l'indice suivant
        solve = solver(solutions,nouvelleCombinaisonBool, nouvelleCombinaison, capacity,combination); // on test si l'ajout du premier élément est une solution
        //////// AJOUT DU PREMIER ELEMENT SOLUTION ///////////
        if (solve == 0) {                           // 0 signifie que c'est une solution
            solutions.add(nouvelleCombinaisonBool); // si oui on l'ajoute aux solutions
            combination.add(convert(nouvelleCombinaisonBool));
        }

        //////// POSSIBILITE DE RAJOUTER DES ELEMENTS ///////////
        else if (solve == 2 && !instanceSecondaire.isEmpty()) { // 2 signifie que l'on est en dessous de la capacité donc on peut rajouter des éléments et si il reste des éléments à ajouter
            int stockSecondaire;
            for (int i = 0; i < instanceSecondaire.size(); i++) { // pour chaque éléments restants
                stockSecondaire = instanceSecondaire.get(0);
                int combinaisonSecondaire = nouvelleCombinaison + instanceSecondaire.get(i); // accumulateur de nouvelle combinaison et du nouvel élément
                boolean[] combinaisonSecondaireBool = Arrays.copyOf(nouvelleCombinaisonBool,nouvelleCombinaisonBool.length); // on copie la solution actuelle
                combinaisonSecondaireBool[saveIndice+1] = true; // on dit que l'on prend cette item
                instanceSecondaire.remove(i);   // on le supprime de l'instance secondaire car on va traiter toutes les possibilités avec
                saveIndice += 1;                // on incrémente l'indice
                i=-1;                           // on décrémente l'indice de la boucle for pour ne manquer aucun éléments

                solve = solver(solutions,combinaisonSecondaireBool, combinaisonSecondaire, capacity, combination); // on récupère la réponse de solver (0,1 ou 2)

                //////// UNE SOLUTION DE TAILLE 2 //////////
                if (solve == 0) {               // c'est une solution
                    solutions.add(combinaisonSecondaireBool); // on l'ajoute
                    combination.add(convert(combinaisonSecondaireBool));
                    if(!instanceSecondaire.isEmpty() &&  instanceSecondaire.get(0)==stockSecondaire) {
                        instanceSecondaire.remove(0);
                        saveIndice++;
                    }
                    continue;                   // on skip et on passe aux éléments suivants car on a atteint la capacité (inutile de rajouter des items)
                }
                /////// ON A DEPASSE LE MAX /////////
                else if (solve == 1) {          // on a dépassé la capacité inutile d'ajouter des éléments
                    if(!instanceSecondaire.isEmpty() && instanceSecondaire.get(0)==stockSecondaire) {
                        instanceSecondaire.remove(0);
                        saveIndice++;
                    }
                    continue;                   // on skip aussi

                }
                /////// ON CHERCHE PLUS LOIN //////
                List<Integer> newInstance = new ArrayList<>(instanceSecondaire);
                getSolution(solutions, combinaisonSecondaireBool,combinaisonSecondaire,newInstance,capacity,saveIndice+1,false,combination); // il y a encore de la capacité donc on continue avec la solution, accumulateur, instance actuelle et on va donc ajouter d'autres éléments (à partir de l'indice suivant)
            }
        }
        if(!restart){
            return solutions;
        }
        if(!weight.isEmpty()) {
            while (weight.get(0) == stockElement) {
                weight.remove(0);
                indice++;
                if (weight.isEmpty()) {
                    break;
                }
            }
        }
        //////// ON RECOMMENCE EN PARTANT DE L'ELEMENT SUIVANT /////////
        return getSolution(solutions, took, 0, weight, capacity, indice + 1,true,combination); // on recommence à partir de l'indice suivant car toutes les combinaisons possible avec le premier élément de l'instance on été testé
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

    private int solver(List<boolean[]> solutions, boolean[] combination, int total, int capacity, List<List<Integer>> combinationS) {
        ////// CAPACITE NON ATTEINTE /////
        if (capacity > total) {
            return 2;
        }
        ////// CAPACITE ATTEINTE /////
        List<Integer> solutionsS = this.convert(combination);

        if (capacity == total && !combinationS.contains(solutionsS)) { // solution et elle n'est pas encore listée

            for (int i =0 ; i < solutions.size() ; i++ ){
                if (Arrays.toString(solutions.get(i)).equals(Arrays.toString(combination)) && combinationS.get(i).containsAll(solutionsS)) {
                    return 1;
                }
            }
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
        this.localWeight = instance.getWeights();

        getSolution(solutions,getEmpty(instance.getNumberOfProducts()), 0, weight, instance.getCapacity(), 0,true,new ArrayList<>());

        for (boolean[] solution : solutions) {
            ISolution solutionS = new SolutionMT(solution, instance);
            solve.add(solutionS);
            log.debug("Les solutions possibles : {}", solutionS.getSolution(instance.getWeights()));
        }
        return solve.iterator();
    }


    /**
     * cette méthode calcule la possibilité d'avoir des nouvelles solutions avec les produits restants
     * elle enumerent toutes les autre possiblité
     *
     * @param instance l'ensemble des données
     * @param solution la solution qu'on a choisi (produits pris)
     * @return Une nouvelle solution avec le produit restant s'il en existe une
     */
    @Override
    public Optional<ISolution> isFeasible(Instance instance, ISolution solution) {
        this.localWeight = instance.getWeights();
        List<boolean[]> solutions = new ArrayList<>();
        InstanceMT newInstance = super.getRemainingItems(instance, solution);

        getSolution(solutions,getEmpty(newInstance.getNumberOfProducts()),0,newInstance.getWeights(),newInstance.getCapacity(),0,true,new ArrayList<>());

        if(solutions.size() > 0) return Optional.ofNullable(new SolutionMT(solutions.get(0), newInstance));
        return Optional.empty();
    }

    /**
     * Permet de generer un tableau de N "false"
     * @param numberOfProducts N
     * @return tableau de N "false"
     */
    public boolean[] getEmpty(int numberOfProducts){
        boolean[] took = new boolean[numberOfProducts];
        for(int i = 0 ; i < numberOfProducts;i++){
            took[i] = false ;
        }
        return took;
    }

    /**
     * Permet de convertir un tableau de boolean en list d'entier
     * Si l'indice de l'entier dans les weight est "true" dans le tableau alors on l'ajoute à la liste
     * @param tab tableau de boolean
     * @return list d'entier correspondante
     */
    private List<Integer> convert(boolean[] tab)
    {
        List<Integer> list = new ArrayList<>();
        if (tab.length > 0) {
            for (int i = 0; i < tab.length; i++) {
                if (tab[i]) list.add(this.localWeight.get(i));
            }
        }
        return list;

    }

    public void setLocalWeight(List<Integer> localWeight) {
        this.localWeight = localWeight;
    }

    @Override
    public String toString()
    {
        return "SolverE";
    }
}