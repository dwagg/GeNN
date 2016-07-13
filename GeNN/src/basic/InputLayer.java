/*
Copyright (c) 2016 David Wagg

This program is released under the "GNU GPL v3" License 
Please see the file License in this distribution for license terms
*/

package basic;

import org.la4j.matrix.sparse.CRSMatrix;
import org.la4j.vector.functor.VectorAccumulator;

public class InputLayer extends Layer{
	
	public InputLayer(int numOfNeurons)
	{
		super(numOfNeurons, 0);
	}
	
	public CRSMatrix prepOutSignal()
	{
		return input;
	}
	
	public void initLayer(CRSMatrix input)
	{
		this.input = input;
		for(int i = 0; i < this.numOfNeurons * numOfNeurons;i++)
		{
			this.weights.add(1d);
		}
	}
}
