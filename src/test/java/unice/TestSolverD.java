import org.junit.Test;
import unice.instance.Instance;
import unice.instance.InstanceMT;
import unice.solution.ISolution;
import unice.solution.SolutionMT;
import unice.solver.SolverD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSolverD {
    SolverD solverD;

    @Test
    public void getSolutionTest() {
        //test 1
        solverD = new SolverD();
        Integer[] s = {1, 1, 1, 1, 1, 1, 2, 2};
        List<Integer> sol1 = Arrays.asList(s);

        Integer[] weights = {6, 1, 1, 1, 1, 2, 1, 7, 5, 2, 1};
        List<Integer> w = Arrays.asList(weights);
        List<Integer> weight = new ArrayList<>(w);
        Collections.sort(weight);
        int capacity = 10;

        List<List<Integer>> newSolution = solverD.getSolution(new ArrayList<List<Integer>>(), weight, capacity);
        assertTrue(newSolution.contains(sol1));


        //test 2
        solverD = new SolverD();

        Integer[] weights2 = {7, 13, 13, 8, 8, 8};
        List<Integer> w2 = Arrays.asList(weights2);
        List<Integer> weight2 = new ArrayList<>(w2);
        Collections.sort(weight2);
        capacity = 21;

        newSolution = solverD.getSolution(new ArrayList<List<Integer>>(), weight2, capacity);
        assertEquals(2, newSolution.size());


        //test 3
        solverD = new SolverD();

        Integer[] weights3 = {6, 14, 10, 7};
        List<Integer> w3 = Arrays.asList(weights3);
        List<Integer> weight3 = new ArrayList<>(w3);
        Collections.sort(weight3);
        capacity = 20;

        newSolution = solverD.getSolution(new ArrayList<List<Integer>>(), weight3, capacity);
        assertEquals(1, newSolution.size());


    }

    @Test
    public void getIteratorTest() {
        solverD = new SolverD();

        Integer[] weights = {7, 13, 13, 8, 8, 8};
        List<Integer> w = Arrays.asList(weights);
        List<Integer> weight = new ArrayList<>(w);
        Instance instanceMT = new InstanceMT(21, 6, weight);
        Boolean[] s1 = {false, true, false, true, false, false};
        List<Boolean> sol = Arrays.asList(s1);
        ISolution solution = new SolutionMT(sol);
        List<ISolution> listeSol = new ArrayList<>();
        listeSol.add(solution);
        assertEquals(solverD.getIterator(instanceMT).next().getChosenItems(), listeSol.iterator().next().getChosenItems());
        assertEquals(solverD.getIterator(instanceMT).next().getChosenItems(), sol);

        //test 3
        solverD = new SolverD();

        Integer[] weights3 = {6, 14, 10, 7};
        List<Integer> w3 = Arrays.asList(weights3);
        List<Integer> weight3 = new ArrayList<>(w3);
        instanceMT = new InstanceMT(20, 4, weight3);

        Boolean[] s2 = {true, true, false, false};
        List<Boolean> sol2 = Arrays.asList(s2);

        assertEquals(solverD.getIterator(instanceMT).next().getChosenItems(), sol2);
    }


}
