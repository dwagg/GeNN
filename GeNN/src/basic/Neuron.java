/*
Copyright (c) 2016 David Wagg

This program is released under the "GNU GPL v3" License 
Please see the file License in this distribution for license terms
*/
package basic;

import java.util.ArrayList;

public class Neuron {
	protected ArrayList<Integer> inWeights;
	protected ArrayList<Neuron> connections;
	
	public Neuron(ArrayList<Integer> inWeights, Layer prev)
	{
		this.inWeights = inWeights;
		for (int i = 0; i < prev.neurons.size(); i++)
		{
			connections.add(prev.neurons.get(i));
		}
	}
	
}
