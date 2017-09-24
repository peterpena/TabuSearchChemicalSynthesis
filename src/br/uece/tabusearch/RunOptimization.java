package br.uece.tabusearch;

import chemaxon.reaction.*;
import chemaxon.formats.*;
import chemaxon.struc.*;
import java.util.ArrayList;

/**
 * @author pedropena
 *
 */
public class RunOptimization {

	/**
	 * @param args
	 * @throws ReactionException 
	 * @throws MolFormatException 
	 */
	public static void main(String[] args) throws MolFormatException, ReactionException {
		runAlgorithm();
	}
	
	public static TabuSearch setupTS(Integer tabuListSize, Integer iterations) {
		return new TabuSearch(new StaticTabuList(tabuListSize), new IterationsStopCondition(iterations), new BasicNeighborSolutionLocator(), 1, "SMIRKS/smirks.csv", "");
	}

	public static void runAlgorithm() throws MolFormatException, ReactionException {
		ChemSynSolution initialSolution = new ChemSynSolution(new Molecule());
		int instanceSize = 1000;
		
		for (int s = 5; s <= 10; s++) { //the size of the tabu list
			for (double i = 0.5; i <= 2; i += 0.5) { //the amount of iterations (50%, 100%, 150% and 200%)
				Integer maxIterations = new Double(instanceSize * i).intValue();
				
				System.out.println(String.format("Running TS with tabu list size [%s] and [%s] iterations. Instance size [%s]", s, maxIterations, instanceSize));
				
				TabuSearch ts = setupTS(s, maxIterations);
				ChemSynSolution bestSol = ts.run(initialSolution);
				
				if(bestSol != null)
					System.out.println("Best Molecule: " + bestSol.getMolecule().getName() + " with return value: " + bestSol.getValue());
			}
		}
	}

}
