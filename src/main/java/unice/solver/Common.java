package unice.solver;

import unice.instance.Instance;
import unice.instance.InstanceMT;
import unice.solution.ISolution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Common {

    /**
     * @param iSolution la solution obtenu
     * @return un Iterator sur les objets du solution
     */
    protected Iterator<ISolution> getSolutionIterator(ISolution iSolution) {
        ArrayList<ISolution> solutions = new ArrayList<>();
        if (iSolution != null) {
            solutions.add(iSolution);
        }
        return solutions.iterator();
    }

    /**
     * Cette méthode indique si c'est possible d'avoir une autre solution
     *
     * @param instance les objets disponibles
     * @param solution la solution trouvée
     * @return la nouvelle Instance a partir des objets restantes
     */
    protected InstanceMT getRemainingItems(Instance instance, ISolution solution) {
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
        /**
         * retourne la nouvelle instance
         */
        return new InstanceMT(instance.getCapacity(), remainingItems.size(), remainingItems);
    }
}
