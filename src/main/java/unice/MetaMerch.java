package unice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unice.instance.Instance;
import unice.instance.InstanceMT;
import unice.parser.ConfigParser;
import unice.solution.ISolution;
import unice.solution.ISolutionChecker;
import unice.solution.SolutionChecker;
import unice.solver.ISolver;

import java.util.Iterator;

public class MetaMerch {

    private static final Logger log = LoggerFactory.getLogger(MetaMerch.class);

    private static Instance parseInstance() {
        //true pour lecture de fichier false pour user input
        return new InstanceMT(true);
    }

    /**
     * Ici on obtient le solver depuis le fichier config.properties
     *
     * @return le solveur choisit
     */
    private static ISolver makeSolver() throws Exception {
        return new ConfigParser().getSolver();
    }

    public static void main(String[] args) throws Exception {

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
