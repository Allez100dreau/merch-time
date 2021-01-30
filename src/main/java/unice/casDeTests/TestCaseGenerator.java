package unice.casDeTests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.Random;

import unice.instance.Instance;
import unice.instance.InstanceMT;
import unice.solution.ISolution;
import unice.solution.ISolutionChecker;
import unice.solution.SolutionChecker;
import unice.solver.ISolver;
import unice.solver.SolverD;
import unice.solver.SolverE;
import unice.solver.SolverH;

// TODO Most objects do not have toString method. They should.
class MerchSolver {
	
	private final ISolver me;
	
	private final ISolver bob;
	
	private final ISolutionChecker solutionChecker = new SolutionChecker();

	public MerchSolver(ISolver me, ISolver bob) {
		super();
		this.me = me;
		this.bob = bob;
	}
	
	private void checkSolution(Instance instance, ISolution solution) {
		// FIXME The checkValidity method should not take the instance as an argument 
		// because it is provided with the solution
	    if( ! solutionChecker.checkValidity(solution.getInstance(), solution)){
            throw new IllegalStateException("Solution checker failed");
        }
	}
	boolean solve(Instance instance) 
    {
        final Iterator<ISolution> iterator = me.getIterator(instance);    
        while (iterator.hasNext()) {
            ISolution solMe = iterator.next();
            checkSolution(instance, solMe);
            // System.out.println(solMe);
            final Optional<ISolution> solBob = bob.isFeasible(instance, solMe);
            if (solBob.isPresent()) {
            	// System.out.println(solBob.get());
                checkSolution(instance, solBob.get());
            } else return true;
        }
        return  false;
    }

	@Override
	public String toString() {
		return me.getClass().getSimpleName() + "-" + bob.getClass().getSimpleName();
	}

	
	
}

public class TestCaseGenerator {

	
	private final int minItems;
	private final int maxItems;
	
	private final int minPrice;
	private final int maxPrice;
	
	private final int minCapacity;
	private final int maxCapacity;
	
	
	private final MerchSolver[] candidates;
	
	private final Random rnd;
	
			
	public TestCaseGenerator(int minItems, int maxItems, int minPrice, int maxPrice, int minCapacity, int maxCapacity, long seed) {
		super();
		this.minItems = minItems;
		this.maxItems = maxItems;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.minCapacity = minCapacity;
		this.maxCapacity = maxCapacity;
		rnd = new Random(seed);
		candidates = createCandidates();
		
	}

	private static MerchSolver[] createCandidates() {
		return new MerchSolver[]{
				new MerchSolver(new SolverE(), new SolverD()),
				new MerchSolver(new SolverE(), new SolverH()),
				new MerchSolver(new SolverD(), new SolverD()),
				new MerchSolver(new SolverD(), new SolverH()),
				new MerchSolver(new SolverH(), new SolverD()),
				new MerchSolver(new SolverH(), new SolverH())
		};
	}
	
	private int nextInt(int min, int max) {
		return rnd.nextInt(max- min) + min;
	}
	private Instance generateInstance() {
		final int n  = nextInt(minItems, maxItems);
		final ArrayList<Integer> items = new ArrayList<Integer>(n);
		final int capacity = nextInt(minCapacity, maxCapacity);
		for (int i = 0; i < n; i++) {
			items.add(nextInt(minPrice, maxPrice));
		}
		return new InstanceMT(capacity, n, items);
	}
	
	private boolean[] evaluateInstance(Instance instance) {
		boolean answers[] = new boolean[candidates.length];
		for (int i = 0; i < answers.length; i++) {
			// System.out.println(candidates[i]);
			answers[i] = candidates[i].solve(instance);
		}
		return answers;
	}
	
	public void printCandidates() {
		System.out.print("s ");
		for (MerchSolver cand: candidates) System.out.print(cand +  " ");
		System.out.println();
	}
		
	public void generateAndTest() {
		// Generate and print the instance
		Instance instance = generateInstance();
		System.out.print("c "+instance.getCapacity() + "\nv ");
		instance.getWeights().forEach(n -> System.out.print(n + " "));
		System.out.println();
		
		// Evaluate the instance and print the answers	
		boolean[] answers = evaluateInstance(instance);
		System.out.print("s ");
		for (boolean ans : answers) System.out.print((ans ? 1 : 0) + " ");
		System.out.println('\n');
	}
	
	public static void main(String[] arg){
		// TODO The candidates are pretty slow, use less than 30 items
        final TestCaseGenerator test = new TestCaseGenerator(10, 20, 1, 14, 10, 50, 0);
        final int n = 10;
        test.printCandidates();
        for (int i = 0; i < n; i++) {
        	test.generateAndTest();
		}
        		
        
    }
}
