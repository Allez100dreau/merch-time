package unice;

import java.util.Iterator;

public interface ISolutionChecker {

    boolean checkValidity(Instance instance, Iterator<Solution> solution);

    // Faire une méthode pour voir s'il manque des solutions
}
