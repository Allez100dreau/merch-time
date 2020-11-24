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
        setUp();

        boolean[] expectedResult = {true, true, false, false};

        boolean[] actualResult = solverH.solve(instanceMT).getChosenItems() ;

        assertEquals(Arrays.toString(expectedResult), Arrays.toString(actualResult));
    }
}
