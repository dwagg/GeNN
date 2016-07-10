/*
Copyright (c) 2016 David Wagg

This program is released under the "GNU GPL v3" License 
Please see the file License in this distribution for license terms
*/

package basic;

import java.util.ArrayList;

public class Layer {

	protected ArrayList<Neuron> neurons;
	protected int incomingSignals;
	
	public Layer(int numOfNeurons, int incomingSignals)
	{
		for (int i = 0; i < numOfNeurons; i++)
		{
			//Neuron n = new Neuron();
			
		}
	}
}
