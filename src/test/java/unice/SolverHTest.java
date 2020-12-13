import org.junit.Test;
import unice.instance.InstanceMT;
import unice.solver.SolverH;

import java.util.*;

import static org.junit.Assert.*;

public class SolverHTest {

    private InstanceMT instanceMT ;
    private int capacity ;
    private int numberOfProducts;
    private List<Integer> weights;

    private SolverH solverH ;


    void setUp(){
        solverH = new SolverH() ;
    }

    @Test
    public void solve(){
        setUp();

        //Test 1
        capacity = 20 ;
        numberOfProducts = 4;
        weights = Arrays.asList(6, 14, 10, 7) ;
        instanceMT = new InstanceMT(capacity ,numberOfProducts, weights);
        boolean[] expectedResult = {true, false, false, true};
        boolean[] actualResult = solverH.solve(instanceMT) != null ? solverH.solve(instanceMT).getChosenItems() : new boolean[]{};
        System.out.println("Actual Result 1 : " + Arrays.toString(actualResult));
        assertEquals(Arrays.toString(expectedResult), Arrays.toString(actualResult));

        //Test 2
        capacity = 40 ;
        numberOfProducts = 12;
        weights = Arrays.asList(6, 10, 13, 4, 7, 4, 3, 8, 4, 4, 6, 9);
        instanceMT = new InstanceMT(capacity ,numberOfProducts, weights);
        expectedResult = new boolean[]{true, true, true, true, false, false, false, false, false, false, false, false};
        actualResult = solverH.solve(instanceMT) != null ? solverH.solve(instanceMT).getChosenItems() : new boolean[]{};
        System.out.println("Actual Result 2 : " +Arrays.toString(actualResult));
        assertEquals(Arrays.toString(expectedResult), Arrays.toString(actualResult));

        //Test 3
        capacity = 10 ;
        numberOfProducts = 11;
        weights = Arrays.asList(6, 1, 1, 1, 1, 2, 1, 7, 5, 2, 1);
        instanceMT = new InstanceMT(capacity ,numberOfProducts, weights);
        expectedResult = new boolean[]{true, false, false, true, false, true, false, false, false, false, false};
        actualResult = solverH.solve(instanceMT) != null ? solverH.solve(instanceMT).getChosenItems() : new boolean[]{};
        System.out.println("Actual Result 3 : " + Arrays.toString(actualResult));
        assertEquals(Arrays.toString(expectedResult), Arrays.toString(actualResult));

        //Test 4 - Notre algo ne renvoie pas de solution ici, car il met 13, puis 7 et est bloqué à 20 !
        capacity = 21 ;
        numberOfProducts = 5;
        weights = Arrays.asList(13, 13, 7, 6, 2);
        instanceMT = new InstanceMT(capacity ,numberOfProducts, weights);
        expectedResult = new boolean[]{};
        actualResult = solverH.solve(instanceMT) != null ? solverH.solve(instanceMT).getChosenItems() : new boolean[]{};
        System.out.println("Actual Result 4 : " + Arrays.toString(actualResult));
        assertEquals(Arrays.toString(expectedResult), Arrays.toString(actualResult));

        //Test 5 - Pareil, il met 12, 7, et il est bloqué
        capacity = 20 ;
        numberOfProducts = 5;
        weights = Arrays.asList(12, 12, 7, 5, 3);
        instanceMT = new InstanceMT(capacity ,numberOfProducts, weights);
        expectedResult = new boolean[]{};
        actualResult = solverH.solve(instanceMT) != null ? solverH.solve(instanceMT).getChosenItems() : new boolean[]{};
        System.out.println("Actual Result 5 : " + Arrays.toString(actualResult));
        assertEquals(Arrays.toString(expectedResult), Arrays.toString(actualResult));

        //Test 6
        capacity = 20 ;
        numberOfProducts = 6;
        weights = Arrays.asList(12, 12, 7, 5, 2, 1);
        instanceMT = new InstanceMT(capacity ,numberOfProducts, weights);
        expectedResult = new boolean[]{true, false, true, false, false, true};
        actualResult = solverH.solve(instanceMT) != null ? solverH.solve(instanceMT).getChosenItems() : new boolean[]{};
        System.out.println("Actual Result 6 : " + Arrays.toString(actualResult));
        assertEquals(Arrays.toString(expectedResult), Arrays.toString(actualResult));

        //Test 7 - Ce test passe pas non plus, il met 13, 10, 9, 7 et il est bloqué avec 39.
        capacity = 40 ;
        numberOfProducts = 11;
        weights = Arrays.asList(13, 10, 9, 7, 6, 6, 4, 4, 4, 4, 3);
        instanceMT = new InstanceMT(capacity ,numberOfProducts, weights);
        expectedResult = new boolean[]{};
        actualResult = solverH.solve(instanceMT) != null ? solverH.solve(instanceMT).getChosenItems() : new boolean[]{};
        Collections.sort(weights);
        Collections.reverse(weights);
        System.out.println(weights);
        System.out.println("Actual Result 7 : " + Arrays.toString(actualResult));
        assertEquals(Arrays.toString(expectedResult), Arrays.toString(actualResult));


    }
}
