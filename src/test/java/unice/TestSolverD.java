import org.junit.Test;
import unice.instance.Instance;
import unice.instance.InstanceMT;
import unice.solution.ISolution;
import unice.solution.SolutionMT;
import unice.solver.SolverD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TestSolverD {

    SolverD solverD;
    Instance instanceMT;
    ISolution solution;

    @Test
    public void getIteratorTest1() {
        solverD = new SolverD();

        Integer[] weights = {7, 13, 13, 8, 8, 8};
        List<Integer> w = Arrays.asList(weights);
        List<Integer> weight = new ArrayList<>(w);
        instanceMT = new InstanceMT(21, 6, weight);
        boolean[] sol = {false, true, false, true, false, false};
        solution = new SolutionMT(sol);
        List<ISolution> listeSol = new ArrayList<>();
        listeSol.add(solution);
        assertEquals(Arrays.toString(solverD.getIterator(instanceMT).next().getChosenItems()), Arrays.toString(listeSol.iterator().next().getChosenItems()));
        assertEquals(Arrays.toString(solverD.getIterator(instanceMT).next().getChosenItems()), Arrays.toString(sol));
    }

    @Test
    public void getIteratorTest2() {
        solverD = new SolverD();
        Integer[] weights3 = {6, 14, 10, 7};
        List<Integer> w3 = Arrays.asList(weights3);
        List<Integer> weight3 = new ArrayList<>(w3);
        instanceMT = new InstanceMT(20, 4, weight3);
        boolean[] sol2 = {true, true, false, false};
        assertEquals(Arrays.toString(solverD.getIterator(instanceMT).next().getChosenItems()), Arrays.toString(sol2));
    }

    @Test
    public void isFeasibleTest1() {
        solverD = new SolverD();
        Integer[] w = {7, 13, 13, 8, 8, 8};
        List<Integer> weights = Arrays.asList(w);
        boolean[] sol = {false, true, false, true, false, false};

        instanceMT = new InstanceMT(21, 6, weights);
        solution = new SolutionMT(sol);

        assertTrue(solverD.isFeasible(instanceMT, solution));
    }

    @Test
    public void isFeasibleTest2() {
        solverD = new SolverD();
        Integer[] w1 = {6, 1, 1, 1, 1, 2, 1, 7, 5, 2, 1};
        List<Integer> weights = Arrays.asList(w1);
        boolean[] s1 = {false, true, true, true, true, true, true, false, false, true, true};

        instanceMT = new InstanceMT(10, 11, weights);
        solution = new SolutionMT(s1);

        assertFalse(solverD.isFeasible(instanceMT, solution));
    }

    @Test
    public void isFeasibleTest3() {
        solverD = new SolverD();
        boolean[] s2 = {true, true, true, true, true, false, false, false, false, false, false};
        Integer[] w1 = {6, 1, 1, 1, 1, 2, 1, 7, 5, 2, 1};
        List<Integer> weights = Arrays.asList(w1);

        instanceMT = new InstanceMT(10, 11, weights);
        solution = new SolutionMT(s2);

        assertTrue(solverD.isFeasible(instanceMT, solution));
    }

    @Test
    public void isFeasibleTest4() {
        solverD = new SolverD();
        Integer[] w2 = {6, 14, 10, 7};
        List<Integer> weights2 = Arrays.asList(w2);
        //solution choisi
        boolean[] s4 = {true, true, false, false};
        instanceMT = new InstanceMT(20, 4, weights2);
        solution = new SolutionMT(s4);

        assertFalse(solverD.isFeasible(instanceMT, solution));
    }
}
