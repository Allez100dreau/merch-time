package unice.solution;

import unice.instance.Instance;
import unice.solver.ISolver;

import java.util.Iterator;

public interface ISolutionChecker {

    boolean checkValidity(Instance instance, ISolution solution);

    // Faire une méthode pour voir s'il manque des solutions
}
