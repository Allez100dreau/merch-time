package unice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unice.instance.Instance;
import unice.instance.InstanceMT;
import unice.solution.ISolution;
import unice.solution.ISolutionChecker;
import unice.solution.SolutionChecker;
import unice.solver.ISolver;
import unice.solver.SolverD;
import unice.solver.SolverE;
import unice.solver.SolverH;

import java.util.Iterator;

public class MetaMerch {

    private static final Logger log = LoggerFactory.getLogger(MetaMerch.class);

    private static Instance parseInstance() {
        //true pour lecture de fichier false pour user input
        return new InstanceMT(true);
    }

    /**
     * Ici on decide quelle solution on veut adopter
     *
     * @return la solution choisit
     */
    private static ISolver makeSolver() {
        return new SolverE();
        //return new SolverZ();
        //return new SolverD();
        //return new SolverH();
    }

    private MetaMerch() {
    }

    public static void main(String[] args) {

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
        log.info(answer);

        //to separate each test in log file
        log.debug("\n\n");

    }
}
