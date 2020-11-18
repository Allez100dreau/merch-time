package unice.instance;

import java.util.*;


/**
 * Cette classe représente toute les données introduites:
 *
 * @capacity : représente le budget d'un joueur
 * @numberOfProducts :  représente le nombre des produits disponibles
 * @weight : représente le prix de chaque produit
 */
public class InstanceMT implements Instance {
    final Scanner scanner = new Scanner(System.in);

    private final int numberOfProducts;
    private final int capacity;
    private final List<Integer>  weights;

    public InstanceMT() {
        weights = new ArrayList<>();
        capacity = scanner.nextInt();
        numberOfProducts = scanner.nextInt();

        for (int i = 0; i < numberOfProducts; i++) {
            weights.add( scanner.nextInt());
        }

        Collections.sort(weights);
        Collections.reverse(weights);
    }

    public InstanceMT(int capacity, int numberOfProducts, List<Integer>  weights) {
        this.numberOfProducts = numberOfProducts;
        this.capacity = capacity;
        this.weights = weights;

    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    @Override
    public int getWeights(int i) {
        return weights.get(i);
    }

    @Override
    public List<Integer> getWeights(){
        return weights;

    }
}
