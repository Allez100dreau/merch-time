import org.junit.jupiter.api.Test;
import unice.instance.Instance;
import unice.instance.InstanceMT;
import unice.solution.ISolution;
import unice.solution.SolutionMT;
import unice.solver.SolverE;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

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

        List<List<Integer>> newSolution = solverE.getSolution(new ArrayList<List<Integer>>(),new ArrayList<Integer>(),weight,capacity);
        assertTrue(newSolution.contains(sol1));


        //test 2
        solverE = new SolverE();

        Integer[] weights2 = {7, 13, 13, 8, 8 ,8};
        List<Integer> w2 = Arrays.asList(weights2);
        List<Integer> weight2 = new ArrayList<>(w2);
        Collections.sort(weight2);
        capacity = 21;

        newSolution = solverE.getSolution(new ArrayList<List<Integer>>(),new ArrayList<Integer>(),weight2,capacity);
        assertEquals( 1,newSolution.size());


        //test 3
        solverE = new SolverE();

        Integer[] weights3 = {6, 14, 10, 7};
        List<Integer> w3 = Arrays.asList(weights3);
        List<Integer> weight3 = new ArrayList<>(w3);
        Collections.sort(weight3);
        capacity = 20;

        newSolution = solverE.getSolution(new ArrayList<List<Integer>>(),new ArrayList<Integer>(),weight3,capacity);
        assertEquals( 1,newSolution.size()); // logiquement 1


    }

    @Test
    public void getIteratorTest(){
        solverE = new SolverE();

        Integer[] weights =  {7, 13, 13, 8, 8 ,8};
        List<Integer> w = Arrays.asList(weights);
        List<Integer> weight = new ArrayList<>(w);
        Instance instanceMT = new InstanceMT(21,6,weight);
        Boolean[] s1 = {false,true,false,true,false,false};
        List<Boolean> sol = Arrays.asList(s1);
        ISolution solution= new SolutionMT(sol);
        List<ISolution> listeSol = new ArrayList<>();
        listeSol.add(solution);
        assertEquals(solverE.getIterator(instanceMT).next().getChosenItems(),listeSol.iterator().next().getChosenItems());
        assertEquals(solverE.getIterator(instanceMT).next().getChosenItems(),sol);

        //test 3
        solverE = new SolverE();

        Integer[] weights3 = {6, 14, 10, 7};
        List<Integer> w3 = Arrays.asList(weights3);
        List<Integer> weight3 = new ArrayList<>(w3);
        instanceMT = new InstanceMT(20,4,weight3);

        Boolean[] s2 = {true,true,false,false};
        List<Boolean> sol2 = Arrays.asList(s2);

        assertEquals(solverE.getIterator(instanceMT).next().getChosenItems(),sol2);
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
        Boolean[] s = {false,true,false,true,false,false};
        List<Boolean> sol = Arrays.asList(s);

        Instance instance = new InstanceMT(21,6,weights);
        ISolution solution = new SolutionMT(sol);

        assertTrue(solverE.isFeasible(instance,solution));

        //2eme test
        //prix disponnibles
        Integer[] w1 = {6,1,1,1,1,2,1,7,5,2,1};
        weights = Arrays.asList(w1);
        //solution choisi
        Boolean[] s1 = {false,true,true,true,true,true,true,false,false,true,true};
        sol = Arrays.asList(s1);

        instance = new InstanceMT(10,11,weights);
        solution = new SolutionMT(sol);

        assertFalse(solverE.isFeasible(instance,solution));

        //3eme test
        //on garde les meme prix mais on prend une nouvelle solution qui ne marche pas
        //solution choisi
        Boolean[] s2 = {true,true,true,true,true,false,false,false,false,false,false};
        sol = Arrays.asList(s2);

        instance = new InstanceMT(10,11,weights);
        solution = new SolutionMT(sol);

        assertTrue(solverE.isFeasible(instance,solution));

        //4eme test
        //prix disponnibles
        Integer[] w2 = {6, 14 ,10 ,7};
        List<Integer> weights2 = Arrays.asList(w2);
        //solution choisi
        Boolean[] s4 = {true,true,false,false};
        sol = Arrays.asList(s4);
        instance = new InstanceMT(20,4,weights2);
        solution = new SolutionMT(sol);

        assertFalse(solverE.isFeasible(instance,solution));

    }





}
