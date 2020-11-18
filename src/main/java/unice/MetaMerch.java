package unice;

import unice.solver.Solver;
import unice.solver.SolverZ;

import java.util.Iterator;

public class MetaMerch {

    private static Instance parseInstance() {
        return new InstanceMT();
    }

    private static Solver makeSolver() {
        return new SolverZ();
    }

    private MetaMerch() {}

    public static void main(String[] args) {
        final Instance instance = parseInstance();
        final Solver solver = makeSolver();

        String answer = "IMPOSSIBLE";
        final Iterator<Solution> iterator = solver.getIterator(instance);
        ISolutionChecker solutionChecker = new SolutionChecker();
        solutionChecker.checkValidity(instance, iterator);

        while (iterator.hasNext()) {
            if (!solver.isFeasible(instance, iterator.next())) {
                answer = "POSSIBLE";
                break;
            }
        }
        System.out.println(answer);
    }
}
