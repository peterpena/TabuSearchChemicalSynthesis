package br.uece.tabusearch;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

/**
 * Basic implementation of {@link BasicNeighborSolutionLocator}, that doensn't have any Aspiration Criteria
 * and simply returns the non-tabu {@link Solution} with the lowest value.
 * 
 * @author Alex Ferreira
 *
 */
public class BasicNeighborSolutionLocator implements BestNeighborSolutionLocator {

	/**
	 * Find the non-tabu {@link Solution} with the lowest value.<br>
	 * This method doesn't use any Aspiration Criteria.
	 */
	@Override
	public ChemSynSolution findBestNeighbor(List<ChemSynSolution> neighborsSolutions, final List<ChemSynSolution> solutionsInTabu) {
		//remove any neighbor that is in tabu list
		CollectionUtils.filterInverse(neighborsSolutions, new Predicate<ChemSynSolution>() {
			@Override
			public boolean evaluate(ChemSynSolution neighbor) {
				return solutionsInTabu.contains(neighbor);
			}
		});
		
		//sort the neighbors
		Collections.sort(neighborsSolutions, new Comparator<ChemSynSolution>() {
			@Override
			public int compare(ChemSynSolution a, ChemSynSolution b) {
				return a.getValue().compareTo(b.getValue());
			}
		});
		
		//get the neighbor with lowest value
		return neighborsSolutions.get(0);
	}

}
