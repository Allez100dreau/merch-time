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
    private static ISolver[] makeSolver() throws Exception {
        return ConfigParser.getSolver();
    }

    private static String getOneResult(Instance instance, ISolver solver1, ISolver solver2 ) throws Exception
    {
        String answer = "IMPOSSIBLE";
        final Iterator<ISolution> iterator = solver1.getIterator(instance);
        ISolutionChecker solutionChecker = new SolutionChecker();

        while (iterator.hasNext()) {
            ISolution solution = iterator.next();

            if(!solutionChecker.checkValidity(instance, solution)){
                answer = "ERREUR";
                log.info(answer);
                log.debug("\n\n");
                continue;
            }

            Optional<ISolution> bobSolution = solver2.isFeasible(instance, solution);

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
     * à la fin il y a le score de chaque solveur et des solveurs combiner
     */

    public static void generatingComparisonChart()
    {
        File chartFile = new File("src/main/resources/ComparisonChart.csv");
        PrintWriter pw = null;
        Iterator<FileParser> inputs =  parseAllFiles();

        ISolver solverE = new SolverE(), solverD = new SolverD(), solverH = new SolverH();
        int scoreSolverH = 0, scoreSolverE = 0, scoreSolverD = 0,
                scoreSolverHD = 0, scoreSolverDH = 0,
                scoreSolverEH = 0, scoreSolverED = 0;

        try {
            pw = new PrintWriter(chartFile);
            pw.write("Instances \\ Solveurs,");

            pw.write(solverE.toString()+",");
            pw.write(solverH.toString()+",");
            pw.write(solverD.toString()+",");

            pw.write(solverH.toString()+" - "+solverD.toString()+",");
            pw.write(solverD.toString()+" - "+solverH.toString()+",");

            pw.write(solverE.toString()+" - "+solverH.toString()+",");
            pw.write(solverE.toString()+" - "+solverD.toString()+"\n");
            int i = 0;


            while (inputs.hasNext()) {
                if (i == 44)  {
                    pw.write("\nScore (/"+scoreSolverE+"):,");
                    pw.write(scoreSolverE+",");
                    pw.write(scoreSolverH+",");
                    pw.write(scoreSolverD+",");
                    pw.write(scoreSolverHD+",");
                    pw.write(scoreSolverDH+",");
                    pw.write(scoreSolverEH+",");
                    pw.write(scoreSolverED+"\n\n");
                    scoreSolverH = 0;
                    scoreSolverE = 0;
                    scoreSolverD = 0;
                    scoreSolverHD = 0;
                    scoreSolverDH = 0;
                    scoreSolverEH = 0;
                    scoreSolverED = 0;
                }
                FileParser parser = inputs.next();
                Instance instance = new InstanceMT(parser.getCapacity(), parser.getNumberOfProducts(), parser.getWeights());
                pw.write(parser.getFileName()+",");

                String resultSolverE = getOneResult(instance, solverE, solverE);
                pw.write(resultSolverE+",");
                scoreSolverE++;

                String resultSolverH = getOneResult(instance, solverH, solverH);
                pw.write(resultSolverH+",");
                if (resultSolverE.equals(resultSolverH)) scoreSolverH++;

                String resultSolverD = getOneResult(instance, solverD, solverD);
                pw.write(resultSolverD+",");
                if (resultSolverE.equals(resultSolverD)) scoreSolverD++;

                String resultSolverHD = getOneResult(instance, solverH, solverD);
                pw.write(resultSolverHD+",");
                if (resultSolverE.equals(resultSolverHD)) scoreSolverHD++;

                String resultSolverDH = getOneResult(instance, solverD, solverH);
                pw.write(resultSolverDH+",");
                if (resultSolverE.equals(resultSolverDH)) scoreSolverDH++;

                String resultSolverEH = getOneResult(instance, solverE, solverH);
                pw.write(resultSolverEH+",");
                if (resultSolverE.equals(resultSolverEH)) scoreSolverEH++;

                String resultSolverED = getOneResult(instance, solverE, solverD);
                pw.write(resultSolverED+"\n");
                if (resultSolverE.equals(resultSolverED)) scoreSolverED++;
                i++;

            }

            pw.write("\nScore (/"+scoreSolverE+"):,");
            pw.write(scoreSolverE+",");
            pw.write(scoreSolverH+",");
            pw.write(scoreSolverD+",");
            pw.write(scoreSolverHD+",");
            pw.write(scoreSolverDH+",");
            pw.write(scoreSolverEH+",");
            pw.write(scoreSolverED+"\n");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw!=null) pw.close();
        }
        log.info("Le tableau de comparaison des Solveurs est sauvegardé à: \n" + chartFile.getAbsolutePath());

    }

    /**
     * Lance un seul test avec les solveurs choisi dans "application.properties"
     * et le input file choisi dans
     * @throws Exception
     */
    public static void generateOneResult() throws Exception {
        final Instance instance = parseInstance();
        final ISolver[] solvers = makeSolver();
        final String answer = getOneResult(instance, solvers[0] , solvers[1]);
        log.info(answer);
    }

    public static void main(String[] args) throws Exception {

        generateOneResult();
        //generatingComparisonChart();

        //to separate each test in log file
        log.debug("\n\n");

    }
}
