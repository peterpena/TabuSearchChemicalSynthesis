package br.uece.tabusearch;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.collections4.IteratorUtils;

import chemaxon.formats.MolFormatException;
import chemaxon.reaction.ReactionException;
import chemaxon.struc.Molecule;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Default implementation of the Tabu Search algorithm
 * @author Alex Ferreira
 *
 */
public class TabuSearch {
	
	private TabuList tabuList;
	private StopCondition stopCondition;
	private BestNeighborSolutionLocator solutionLocator;
	private float branchFactor;
	private String smirksFile;
	private String molsFile;
	
	/**
	 * Construct a {@link TabuSearch} object
	 * @param tabuList the tabu list used in the algorithm to handle tabus
	 * @param stopCondition the algorithm stop condition
	 * @param solutionLocator the best neightbor solution locator to be used in each algortithm iteration
	 */
	public TabuSearch(TabuList tabuList, StopCondition stopCondition, BestNeighborSolutionLocator solutionLocator, float branchFactor, String smirksFile, String molsFile) {
		this.tabuList = tabuList;
		this.stopCondition = stopCondition;
		this.solutionLocator = solutionLocator;
		this.branchFactor = branchFactor;
		this.smirksFile = smirksFile;
		this.molsFile = molsFile;
	}
	
	/**
	 * Execute the algorithm to perform a minimization.
	 * @param initialSolution the start point of the algorithm
	 * @return the best solution found in the given conditions
	 * @throws ReactionException 
	 * @throws MolFormatException 
	 */
	public ChemSynSolution run(ChemSynSolution initialSolution) throws MolFormatException, ReactionException {
        
        HashMap<String, String> smirks = readSmirks(smirksFile);
        ArrayList<Molecule> buildingBlocks = readMolecules(molsFile);
        
        if(buildingBlocks == null) {
        		System.out.println("Please provide building blocks....");
        		return null;
        }
            
        /*Initialization of elements*/
		ChemSynSolution bestSolution = initialSolution;
		ChemSynSolution currentSolution = initialSolution;		
		
		Integer currentIteration = 0;
		while (!stopCondition.mustStop(++currentIteration, bestSolution)) {
			
			List<ChemSynSolution> candidateNeighbors = currentSolution.getNeighbors(buildingBlocks, branchFactor, smirks);
			List<ChemSynSolution> solutionsInTabu = IteratorUtils.toList(tabuList.iterator());
			
			ChemSynSolution bestNeighborFound = solutionLocator.findBestNeighbor(candidateNeighbors, solutionsInTabu);
			if (bestNeighborFound.getValue() < bestSolution.getValue()) {
				bestSolution = bestNeighborFound;
			}
			
			tabuList.add(currentSolution);
			currentSolution = bestNeighborFound;
			
			tabuList.updateSize(currentIteration, bestSolution);
		}
		
		return bestSolution;
	}
	
	/**
	 * Read smirks from a csv file.
	 * @param csv file containing smirks
	 * @return a map of smirks and name of smirks
	 */
	private HashMap<String, String> readSmirks(String csvFile) {
		
		HashMap<String, String> hm = new HashMap<String, String>();
		
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] smirk = line.split(cvsSplitBy);

                System.out.println("Smirk [name= " + smirk[0] + " , smirk=" + smirk[1] + "]");
                hm.put(smirk[0], smirk[1]);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return hm;	
	}
	
	/**
	 * Read building blocks from file.
	 * @param database containing building blocks
	 * @return an arraylist of molecules
	 */
	private ArrayList<Molecule> readMolecules(String file){
		//TODO: Add molecules from db into buidlingblocks
		return null;
	}
}


