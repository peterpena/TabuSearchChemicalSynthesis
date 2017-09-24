package br.uece.tabusearch;

/**
 * Tabu list interface
 * @author Alex Ferreira
 *
 */
public interface TabuList extends Iterable<ChemSynSolution> {
	
	/**
	 * Add some solution to the tabu
	 * @param solution the solution to be added
	 */
	void add(ChemSynSolution solution);
	
	/**
	 * Check if a given solution is inside of this tabu list
	 * @param solution the solution to check
	 * @return true if the given solution is contained by this tabu, false otherwise
	 */
	Boolean contains(ChemSynSolution solution);
	
	/**
	 * Update the size of the tabu list dinamically<br>
	 * This method should be implemented only by dynamic sized tabu lists, and may be called after each iteration of the algorithm
	 * @param currentIteration the current iteration of the algorithm
	 * @param bestSolutionFound the best solution found so far
	 */
	void updateSize(Integer currentIteration, ChemSynSolution bestSolutionFound);

}