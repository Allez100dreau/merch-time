import org.junit.Test;
import unice.instance.Instance;
import unice.instance.InstanceMT;
import unice.solution.ISolution;
import unice.solution.SolutionMT;
import unice.solver.SolverE;

import java.util.*;

import static org.junit.Assert.*;
/*
public class TestSolverE {

    SolverE solverE;

    @Test
    public void getSolutionTest()
    {
        //test 1
        solverE = new SolverE();
        Integer[] s = {1,1,1,1,6};
        List<Integer> sol1 = Arrays.asList(s);

        Integer[] weights = {6,1,1,1,1,2,1,7,5,2,1};
        List<Integer> w = Arrays.asList(weights);
        List<Integer> weight = new ArrayList<>(w);
        Collections.sort(weight);
        int capacity = 10;

        List<List<Integer>> newSolution = solverE.getSolution(new ArrayList<>(),new ArrayList<>(),weight,capacity);
        assertTrue(newSolution.contains(sol1));


        //test 2
        solverE = new SolverE();

        Integer[] weights2 = {7, 13, 13, 8, 8 ,8};
        List<Integer> w2 = Arrays.asList(weights2);
        List<Integer> weight2 = new ArrayList<>(w2);
        Collections.sort(weight2);
        capacity = 21;

        newSolution = solverE.getSolution(new ArrayList<>(),new ArrayList<>(),weight2,capacity);
        assertEquals( 1,newSolution.size());


        //test 3
        solverE = new SolverE();

        Integer[] weights3 = {6, 14, 10, 7};
        List<Integer> w3 = Arrays.asList(weights3);
        List<Integer> weight3 = new ArrayList<>(w3);
        Collections.sort(weight3);
        capacity = 20;

        newSolution = solverE.getSolution(new ArrayList<>(),new ArrayList<>(),weight3,capacity);
        assertEquals( 1,newSolution.size()); // logiquement 1


    }

    @Test
    public void getIteratorTest(){
        solverE = new SolverE();

        Integer[] weights = {7, 13, 13, 8, 8 ,8};
        List<Integer> w = Arrays.asList(weights);
        List<Integer> weight = new ArrayList<>(w);
        Instance instanceMT = new InstanceMT(21,6,weight);
        boolean[] sol = {false,true,false,true,false,false};

        ISolution solution= new SolutionMT(sol);
        List<ISolution> listeSol = new ArrayList<>();
        listeSol.add(solution);
        assertEquals(Arrays.toString(solverE.getIterator(instanceMT).next().getChosenItems()), Arrays.toString(listeSol.iterator().next().getChosenItems()));
        assertEquals(Arrays.toString(solverE.getIterator(instanceMT).next().getChosenItems()), Arrays.toString(sol));

        //test 3
        solverE = new SolverE();

        Integer[] weights3 = {6, 14, 10, 7};
        List<Integer> w3 = Arrays.asList(weights3);
        List<Integer> weight3 = new ArrayList<>(w3);
        instanceMT = new InstanceMT(20,4,weight3);

        boolean[] sol2 = {true,true,false,false};

        assertEquals(Arrays.toString(solverE.getIterator(instanceMT).next().getChosenItems()), Arrays.toString(sol2));
    }

    @Test
    public void knapsackRecTest()
    {
        // possible d'avoir une somme de 21 à partir des nombre suivant
        solverE = new SolverE();
        Integer[] weights = {7,13,8,8};
        List<Integer> w = Arrays.asList(weights);
        assertEquals( 21 , solverE.knapsackRec(w,w.size(),21));


        // non possible d'avoir une somme de 20 à partir des nombre suivant
        solverE = new SolverE();
        Integer[] weights2 = {6, 14 ,10 ,7};
        w = Arrays.asList(weights2);
        assertEquals( 20 , solverE.knapsackRec(w,w.size(),20) );
    }


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
        Integer[] w1 = {6,1,1,1,1,2,1,7,5,2,1};
        weights = Arrays.asList(w1);
        //solution choisi
        boolean[] s1 = {false,true,true,true,true,true,true,false,false,true,true};
        instance = new InstanceMT(10,11,weights);
        solution = new SolutionMT(s1);

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
}*/
