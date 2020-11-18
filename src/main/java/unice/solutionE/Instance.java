package unice.solutionE;

import java.util.List;


/**
 * Cette classe représente toute les données introduites:
 *
 * @capacity : représente le budget d'un joueur
 * @numberOfProducts :  représente le nombre des produits disponibles
 * @weight : représente le prix de chaque produit
 * @frequencies : represente la fréquence des objets disponible
 */
public class Instance implements IInstanceMT {

    private final int capacity;
    private final int numberOfProducts;
    private final List<Integer> weight;

    public Instance(int capacity, int n, List<Integer> weight) {
        this.capacity = capacity;
        this.numberOfProducts = n;
        this.weight = weight;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public int getWeight(int i) {
        return weight.get(i);
    }

    public List<Integer> getWeight() {
        return weight;
    }


}