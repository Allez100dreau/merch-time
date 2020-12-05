import org.junit.Test;
import unice.instance.Instance;
import unice.instance.InstanceMT;
import unice.solution.ISolution;
import unice.solution.SolutionMT;
import unice.solver.SolverE;
import java.util.*;
import static org.junit.Assert.*;


public class TestSolverE {

    SolverE solverE;

    /*
    @Test
    public void getSolutionTest()
    {
        //test 1
        solverE = new SolverE();
        boolean[] s = {true, false, false, true, true, false, false, false, false};

        Integer[] weights = {6,1,1,1,1,7,5,2,1};
        List<Integer> w = Arrays.asList(weights);
        List<Integer> weight = new ArrayList<>(w);
        Collections.sort(weight);
        Collections.reverse(weight);
        int capacity = 10;
        List<boolean[]> newSolution = solverE.getSolution(new ArrayList<>(), solverE.getEmpty(weight.size()),0,weight,capacity,0,true, new ArrayList<>());
        boolean contain = false;
        for (boolean[] smt : newSolution) {

            if (Arrays.toString(smt).equals(Arrays.toString(s)))
            {
                contain = true;
            }
        }
        assertTrue(contain);


        //test 2
        solverE = new SolverE();

        Integer[] weights2 = {7, 13, 13, 8, 8 ,8};
        List<Integer> w2 = Arrays.asList(weights2);
        List<Integer> weight2 = new ArrayList<>(w2);
        Collections.sort(weight2);
        Collections.reverse(weight2);

        boolean[] s2 = {true, false, true, false, false, false};
        newSolution = solverE.getSolution(new ArrayList<>(), solverE.getEmpty(weight2.size()),0,weight2,21,0,true, new ArrayList<>());
        assertEquals( 1,newSolution.size());

        contain = false ;
        for (boolean[] smt : newSolution) {

            if (Arrays.toString(smt).equals(Arrays.toString(s2)))
            {
                contain = true;
            }
        }
        assertTrue(contain);


        //test 3
        solverE = new SolverE();

        Integer[] weights3 = { 14, 10, 7,6};
        List<Integer> w3 = Arrays.asList(weights3);
        List<Integer> weight3 = new ArrayList<>(w3);

        capacity = 20;

        newSolution = solverE.getSolution(new ArrayList<>(), solverE.getEmpty(weight3.size()),0,weight3,capacity,0,true, new ArrayList<>());

        assertEquals( 0,newSolution.size()); //logiquement 1


    }
    */

    @Test
    public void getIteratorTest() {
        solverE = new SolverE();

        Integer[] weights = {7, 13, 13, 8, 8, 8};
        List<Integer> w = Arrays.asList(weights);
        List<Integer> weight = new ArrayList<>(w);
        Instance instanceMT = new InstanceMT(21, 6, weight);
        boolean[] sol = {true, false, true, false, false, false};

        ISolution solution = new SolutionMT(sol);
        List<ISolution> listeSol = new ArrayList<>();
        listeSol.add(solution);

        // Si c'est la meme solution l'algo ne retourne qu'une seul
        Iterator<ISolution> iterator = solverE.getIterator(instanceMT);
        assertEquals(Arrays.toString(iterator.next().getChosenItems()), Arrays.toString(listeSol.iterator().next().getChosenItems()));
        assertFalse(iterator.hasNext());


        //test 3
        solverE = new SolverE();

        Integer[] weights3 = {6, 14, 10, 7};
        List<Integer> w3 = Arrays.asList(weights3);
        List<Integer> weight3 = new ArrayList<>(w3);
        instanceMT = new InstanceMT(20,4,weight3);
        iterator = solverE.getIterator(instanceMT);

        boolean[] sol2 = {true, false, false, true}; // 14 + 6
        assertEquals(Arrays.toString(iterator.next().getChosenItems()), Arrays.toString(sol2));
        assertFalse(iterator.hasNext());

    }


/*
    @Test
    public void isFeasibleTest()
    {
        //1er test
        //on veut savoir s'il est possible de construire une nouvelle solution
        solverE = new SolverE();

        //prix disponnibles
        Integer[] w = {7,13,13,8,8,8};
        List<Integer> weights = Arrays.asList(w);
        //solution choisi
        boolean[] sol = {false,true,false,true,false,false};

        Instance instance = new InstanceMT(21,6,weights);
        ISolution solution = new SolutionMT(sol);

        assertTrue(solverE.isFeasible(instance,solution));

        //2eme test
        //prix disponnibles
        Integer[] w1 = {7,6,5,2,2,1,1,1,1,1,1};
        weights = Arrays.asList(w1);
        //solution choisi
        boolean[] s1 = {false,true,true,true,true,true,false,false,false,true,false};
        instance = new InstanceMT(10,11,weights);
        solution = new SolutionMT(s1);
        System.out.print(solverE.isFeasible(instance,solution));

        assertFalse(solverE.isFeasible(instance,solution));

        //3eme test
        //on garde les meme prix mais on prend une nouvelle solution qui ne marche pas
        //solution choisi
        boolean[] s2 = {true,true,true,true,true,false,false,false,false,false,false};
        instance = new InstanceMT(10,11,weights);
        solution = new SolutionMT(s2);

        assertTrue(solverE.isFeasible(instance,solution));

        //4eme test
        //prix disponnibles
        Integer[] w2 = {6, 14 ,10 ,7};
        List<Integer> weights2 = Arrays.asList(w2);
        //solution choisi
        boolean[] s4 = {true,true,false,false};
        instance = new InstanceMT(20,4,weights2);
        solution = new SolutionMT(s4);

        assertFalse(solverE.isFeasible(instance,solution));
    }
*/
}
