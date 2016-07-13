/*
Copyright (c) 2016 David Wagg

This program is released under the "GNU GPL v3" License 
Please see the file License in this distribution for license terms
*/

package basic;

import org.la4j.matrix.sparse.CRSMatrix;

public class OutputLayer extends Layer{

	public OutputLayer(int numOfNeurons, int incomingSignals){
		super(numOfNeurons, incomingSignals);
		
	}
}
