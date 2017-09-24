package br.uece.tabusearch;

import java.util.Iterator;

import org.apache.commons.collections4.queue.CircularFifoQueue;

/**
 * Basic fixed size implementation of {@link TabuList}
 * @author Alex Ferreira
 *
 */
public final class StaticTabuList implements TabuList {
	
	private CircularFifoQueue<ChemSynSolution> tabuList;
	
	/**
	 * Construct a new {@link StaticTabuList}
	 * @param size the size of the tabu list
	 */
	public StaticTabuList(Integer size) {
		this.tabuList = new CircularFifoQueue<ChemSynSolution>(size);
	}

	@Override
	public void add(ChemSynSolution solution) {
		tabuList.add(solution);
	}

	@Override
	public Boolean contains(ChemSynSolution solution) {
		return tabuList.contains(solution);
	}
	
	@Override
	public Iterator<ChemSynSolution> iterator() {
		return tabuList.iterator();
	}

	/**
	 * This method does not perform any update in the tabu list,
	 * due to the fixed size nature of this implementation
	 */
	@Override
	public void updateSize(Integer currentIteration, ChemSynSolution bestSolutionFound) {
		//Do nothing, this implementation has a fixed size
	}

}
