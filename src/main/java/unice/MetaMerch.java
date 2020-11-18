package unice;

import unice.instance.Instance;
import unice.instance.InstanceMT;
import unice.solution.ISolution;
import unice.solution.ISolutionChecker;
import unice.solution.SolutionChecker;
import unice.logger.LoggerMT;
import unice.solver.*;

import java.util.Iterator;

public class MetaMerch {

    private static Instance parseInstance() {
        return new InstanceMT();
    }

    /**
     * Ici on decide quelle solution on veut adopter
     * @return la solution choisit
     */
    private static ISolver makeSolver() {
        return new SolverE();
        //return new SolverZ();
        //return new SolverD();
        //return new SolverH();
    }

    private MetaMerch() {}

    public static void main(String[] args) {

        LoggerMT.init(false,true);

        final Instance instance = parseInstance();
        final ISolver solver = makeSolver();

        String answer = "IMPOSSIBLE";
        final Iterator<ISolution> iterator = solver.getIterator(instance);
        ISolutionChecker solutionChecker = new SolutionChecker();


        while (iterator.hasNext()) {
            ISolution solution = iterator.next();

            solutionChecker.checkValidity(instance, solution);

            if (!solver.isFeasible(instance, solution)) {
                answer = "POSSIBLE";

            }
        }


        LoggerMT.result(answer);
    }
}
