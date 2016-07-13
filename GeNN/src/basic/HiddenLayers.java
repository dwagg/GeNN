/*
Copyright (c) 2016 David Wagg

This program is released under the "GNU GPL v3" License 
Please see the file License in this distribution for license terms
*/
package basic;

import java.util.ArrayList;

import org.la4j.matrix.sparse.CRSMatrix;

public class HiddenLayers {
	private int numOfLayers;
	private ArrayList<Integer> sizeOfLayers;
	private ArrayList<Layer> layers;
	private InputLayer inLayer; 
	
	public HiddenLayers(int numOfLayers, ArrayList<Integer> sizeOfLayers, InputLayer inLayer)
	{
		assert numOfLayers == sizeOfLayers.size();
		this.sizeOfLayers = new ArrayList<Integer>();
		this.numOfLayers = numOfLayers;
		this.inLayer = inLayer;
		this.layers = new ArrayList<>();
		for (int i = 0; i < numOfLayers; i++)
		{
			this.sizeOfLayers.add(sizeOfLayers.get(i));
		}
		
		for(int i = 0; i < this.sizeOfLayers.size(); i++)
		{
			Layer prev;
			if (i ==0)
			{
				prev = inLayer;
			}
			else
			{
				prev = layers.get(i-1);
			}
			layers.add(new Layer(this.sizeOfLayers.get(i),prev.getNumOfNeurons()));
		}
	}
	
	public CRSMatrix processOutput()
	{
		for(int i = 0; i < numOfLayers; i++)
		{
			Layer prev;
			if (i == 0)
			{
				prev = inLayer;
			}
			else
			{
				prev = layers.get(i-1);
			}
			//CRSMatrix in = inLayer.prepOutSignal();
			CRSMatrix out = prev.prepOutSignal();
			layers.get(i).initLayer(out);
			//layers.get(i).initLayer(in);
		}
		
		return layers.get(numOfLayers -1).prepOutSignal();
	}

	public Layer getHiddenOutputLayer()
	{
		return layers.get(layers.size() -1);
	}
}
