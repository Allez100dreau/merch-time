package unice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unice.instance.Instance;
import unice.instance.InstanceMT;
import unice.parser.ConfigParser;
import unice.parser.FileParser;
import unice.solution.ISolution;
import unice.solution.ISolutionChecker;
import unice.solution.SolutionChecker;
import unice.solver.ISolver;
import unice.solver.SolverD;
import unice.solver.SolverE;
import unice.solver.SolverH;

import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Optional;

import static unice.parser.FileParser.parseAllFiles;

public class MetaMerch {

    private static final Logger log = LoggerFactory.getLogger(MetaMerch.class);

    private static Instance parseInstance() {
        //true pour lecture de fichier false pour user input
        return new InstanceMT(true);
    }

    /**
     * Ici on obtient le solver depuis le fichier application.properties
     * changer le solveur dans le fichier application.properties qui se trouve dans resources
     * @return le solveur choisit
     */
    private static ISolver makeSolver() throws Exception {
        return new ConfigParser().getSolver();
    }

    private static String getOneResult(Instance instance, ISolver solver ) throws Exception
    {
        String answer = "IMPOSSIBLE";
        final Iterator<ISolution> iterator = solver.getIterator(instance);
        ISolutionChecker solutionChecker = new SolutionChecker();

        while (iterator.hasNext()) {
            ISolution solution = iterator.next();

            if(!solutionChecker.checkValidity(instance, solution)){
                answer = "ERREUR";
                log.info(answer);
                log.debug("\n\n");
                continue;
            }

            Optional<ISolution> bobSolution = solver.isFeasible(instance, solution);

            if (!bobSolution.isPresent()) {
                answer = "POSSIBLE";
            }
            else {
                ISolution bobSolutionPresent = bobSolution.get();
                if (!solutionChecker.checkValidity(instance, bobSolutionPresent)) {
                    answer = "POSSIBLE";
                }
            }
        }
        return  answer;
    }

    /**
     * Cette méthode génère le tableau de comparaison des solveurs
     * Pour chaque test il y a le résultat
     * à la fin il y a le score de chaque solveur
     */

    public static void generatingComparisonChart()
    {
        File chartFile = new File("src/main/resources/ComparisonChart.csv");
        PrintWriter pw = null;

        ISolver solverE = new SolverE(), solverD = new SolverD(), solverH = new SolverH();
        int scoreSolverH = 0, scoreSolverE = 0, scoreSolverD = 0;

        Iterator<FileParser> inputs =  parseAllFiles();

        try {
            pw = new PrintWriter(chartFile);
            pw.write("Instances \\ Solvers,");

            pw.write(solverE.toString()+",");
            pw.write(solverH.toString()+",");
            pw.write(solverD.toString()+"\n");

            while (inputs.hasNext()) {
                FileParser parser = inputs.next();
                Instance instance = new InstanceMT(parser.getCapacity(), parser.getNumberOfProducts(), parser.getWeights());
                pw.write(parser.getFileName()+",");

                String resultSolverE = getOneResult(instance, solverE);
                pw.write(resultSolverE+",");
                scoreSolverE++;

                String resultSolverH = getOneResult(instance, solverH);
                pw.write(resultSolverH+",");
                if (resultSolverE.equals(resultSolverH)) scoreSolverH++;

                String resultSolverD = getOneResult(instance, solverD);
                pw.write(resultSolverD+"\n");
                if (resultSolverE.equals(resultSolverD)) scoreSolverD++;
            }

            pw.write("\nScore du Solver (/"+scoreSolverE+"):,");
            pw.write(scoreSolverE+",");
            pw.write(scoreSolverH+",");
            pw.write(scoreSolverD+"");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw!=null) pw.close();
        }
        log.info("Le tableau de comparaison des Solver est sauvegarde à: \n" + chartFile.getAbsolutePath());

    }

    public static void main(String[] args) throws Exception {
        final Instance instance = parseInstance();
        final ISolver solver = makeSolver();
        final String answer = getOneResult(instance, solver);
        log.info(answer);

        generatingComparisonChart();

        //to separate each test in log file
        log.debug("\n\n");

    }
}
