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
