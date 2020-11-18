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
        final Iterator<Solution> m = solver.getIterator(instance);
        while (m.hasNext()) {
            if (!solver.isFeasible(instance, m.next())) {
                answer = "POSSIBLE";
                break;
            }
        }
        System.out.println(answer);
    }
}
