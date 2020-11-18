package unice.solutionE;

import java.util.*;

public class MTSolver {

    /**
     * Cette methode permet de Input les données
     * @return  un objet IIstanceMT qui contient toute les données .
     */
    private static Instance parseInstance() {
        Scanner sc = new Scanner(System.in);
        int capacity = sc.nextInt();
        int n = sc.nextInt();
        List<Integer> weight = new ArrayList<>();
        for(int i = 0 ; i<n ;i++){
            weight.add(sc.nextInt());
        }
        return new Instance(capacity,n,weight);
    }

    private static SolverE makeSolver() {
        return new SolverE();
    }


    public static void main(String[] args) {

        LoggerMT.init(true);

        final Instance instance = parseInstance();
        final SolverE solver = makeSolver();

        String answer = "IMPOSSIBLE";
        final Iterator<ISolutionSSP> you = solver.getIterator(instance);

        // pour chaque solution on verifie s'il est possible de construire
        // une autre combinaison de prix avec le reste des prix
        while(you.hasNext()) {
            if(! solver.isFeasible(instance, you.next())) {
                answer = "POSSIBLE";
                break;
            }
        }
        LoggerMT.show(answer);
    }
}