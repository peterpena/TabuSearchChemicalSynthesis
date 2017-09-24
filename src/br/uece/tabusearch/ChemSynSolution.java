package br.uece.tabusearch;
import chemaxon.reaction.*;
import chemaxon.formats.*;
import chemaxon.struc.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChemSynSolution implements Solution {
	
	private Molecule mol;
	private Reactor reactor;
	
	public ChemSynSolution(Molecule mol) {
		this.mol = mol;
		
		// create Reactor
		reactor = new Reactor();
		
	}

	public Double getValue() {
		// TODO: Calculate the solubility of the molecule (calculate the objective)
		return mol.getMass();
		
	}
	
	public Molecule getMolecule() {
		return mol;
	}
	
	//get reactants that react with this molecule
	List<ChemSynSolution> getNeighbors(ArrayList<Molecule> mols, float bf, HashMap<String, String> smirks) throws ReactionException, MolFormatException{
		
		List<ChemSynSolution> neighbors = new ArrayList<ChemSynSolution>();
		
		RxnMolecule rxmol = (RxnMolecule)MolImporter.importMol(smirks.get("Pictet-Spengler"));
		 
		 // set the reaction ('rxmol' is the reaction molecule)
		 // TODO: reaction rules are read from RDF/MRV tags
		 reactor.setReaction(rxmol);
		 
		for(Molecule reactant : mols) {
			
			Molecule[] reactants = {mol, reactant};
			
			if(Math.random() < bf) {

				 // set the reactants
				 reactor.setReactants(reactants);

				 // get the results
				 Molecule[] products;
				 while ((products = reactor.react()) != null) {
				     // export result molecules
				     for (Molecule product : products) {
				    	     ChemSynSolution sol = new ChemSynSolution(product);
				         neighbors.add(sol);
				     }
				 }
			}
		}
		return neighbors;
	}

	@Override
	public List getNeighbors(ArrayList mols, float bf) {
		// TODO Auto-generated method stub
		return null;
	}
}
