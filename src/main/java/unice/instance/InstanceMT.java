package unice.instance;

import unice.parser.ConfigParser;
import unice.parser.FileParser;
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
    private FileParser parser = new FileParser();

    public InstanceMT(boolean isParsing) {

        /**
         *  Soit on choisit de lire avec un fichier, soit directement avec l'input utilisateur
         *  a voir si par la suite on utilise directement parseAllFiles et on renvoie directement tous les output sous la forme d'un tableau d'instance
         *
          */
        if(isParsing)
        {
            parser.parseFile(ConfigParser.getInputFile());
            capacity = parser.getCapacity();
            numberOfProducts = parser.getNumberOfProducts();
            weights = parser.getWeights();
        }
        else {
            weights = new ArrayList<>();
            capacity = scanner.nextInt();
            numberOfProducts = scanner.nextInt();

            for (int i = 0; i < numberOfProducts; i++) {
                weights.add(scanner.nextInt());
            }
        }

        Collections.sort(weights);
        Collections.reverse(weights);
    }

    public InstanceMT(int capacity, int numberOfProducts, List<Integer>  weights) {
        this.numberOfProducts = numberOfProducts;
        this.capacity = capacity;
        this.weights = weights;

        Collections.sort(weights);
        Collections.reverse(weights);
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
