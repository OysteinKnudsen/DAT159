package no.hvl.dat159;

import java.util.List;

import no.hvl.lph.dat159.Block;

public class Blockchain {

	private int miningDifficulty; // The number of leading zeros in block hashes
	private String miningTarget;  // The reg-exp representing the mining target

	private List<Block> listOfBlocks; // The list of blocks
	
	public Blockchain(int miningDifficulty) {
		// TODO
		// Initializing *ALL* the instance variables to a valid state.
	}
	
	public String getHashLastBlock() {
		// TODO
		// Returning the hash of the last block appended to the chain.
		// If the chain is empty, "0" is returned.
	}

	public boolean validateAndAppendNewBlock(Block b) {
		// TODO
		// Validate and append to chain if valid.
		// Return whether everything went OK and the block was appended.
	}
	
	public boolean isValidChain() {
		// TODO
		// Validate the entire chain.
	}

	// getters and setters
	
}
