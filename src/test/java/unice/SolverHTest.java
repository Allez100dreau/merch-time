import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import unice.instance.InstanceMT;
import unice.solver.SolverH;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SolverHTest {
    InstanceMT instanceMT ;
    int capacity ;
    int numberOfProducts;
    List<Integer> weights;

    SolverH solverH ;

    @BeforeEach
    void setUp(){
        capacity = 20 ;
        numberOfProducts = 4;
        weights = Arrays.asList(new Integer[] {6,14,10,7}) ;
        instanceMT = new InstanceMT(capacity , numberOfProducts, weights );

        solverH = new SolverH() ;
    }

    @Test
    void solve(){
        List<Boolean> expectedResult = new ArrayList<>(Arrays.asList(true,true,false,false));

        List<Boolean> actualResult = solverH.solve(instanceMT).getChosenItems() ;


        assertEquals(expectedResult , actualResult);

        //System.out.println(solverH.solve(instanceMT).getChosenItems()) ;
    }


}
