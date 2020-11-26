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
        capacity = 20 ;
        numberOfProducts = 4;
        weights = Arrays.asList(6,14,10,7) ;
        instanceMT = new InstanceMT(capacity ,numberOfProducts, weights);

        solverH = new SolverH() ;
    }

    @Test
    public void solve(){
        //Test 1
        setUp();
        boolean[] expectedResult = {true, false, false, true};
        boolean[] actualResult = solverH.solve(instanceMT).getChosenItems() ;
        assertEquals(Arrays.toString(expectedResult), Arrays.toString(actualResult));

        //Test 2
        capacity = 40 ;
        numberOfProducts = 12;
        weights = Arrays.asList(6,10, 13, 4, 7, 4, 3, 8, 4, 4, 6, 9) ;
        instanceMT = new InstanceMT(capacity ,numberOfProducts, weights);
        expectedResult = new boolean[]{true, true, true, true, false, false, false, false, false, false, false, false};
        actualResult = solverH.solve(instanceMT).getChosenItems() ;
        assertEquals(Arrays.toString(expectedResult), Arrays.toString(actualResult));
    }
}
