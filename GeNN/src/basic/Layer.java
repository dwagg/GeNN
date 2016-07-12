/*
Copyright (c) 2016 David Wagg

This program is released under the "GNU GPL v3" License 
Please see the file License in this distribution for license terms
*/

package basic;

import java.util.ArrayList;
import java.util.Random;

import org.la4j.matrix.sparse.CRSMatrix;
import org.la4j.vector.dense.BasicVector;

public class Layer {

	protected ArrayList<Neuron> neurons;
	protected CRSMatrix input;
	public CRSMatrix getInput() {
		return input;
	}

	protected int incomingSignals;
	//protected BasicVector values;
	protected ArrayList<Double> weights;
	public Layer(int numOfNeurons, int incomingSignals, CRSMatrix input)
	{
		this.neurons = new ArrayList<>();
		this.weights = new ArrayList<>();
		this.input = input;
		//This code to be removed, for testing purposes only===================================
		for (int i = 0; i < numOfNeurons * incomingSignals; i++)
		{
			Random rand = new Random();
			weights.add(rand.nextDouble());
		}
		
		//This is the end of the diagnostic code===============================================
		for (int i = 0; i < numOfNeurons; i++)
		{
			ArrayList<Double> neuronWeights = new ArrayList<Double>();
			for (int j = 0; j < incomingSignals; j++)
			{
				neuronWeights.add(weights.get((incomingSignals * i + j)));
			}
			neurons.add(new Neuron(neuronWeights, input));
		}
	}
	
	//For each one of the neurons calculate a value vector then make a matrix out of all of the value Vectors as the output signal
	public CRSMatrix prepOutSignal()
	{
		final int numOfNeurons = neurons.size();
		ArrayList<BasicVector> listOfVectors = new ArrayList<BasicVector>();
		
		for (int i = 0; i < numOfNeurons; i++)
		{
			listOfVectors.add(neurons.get(i).processInput(input));
		}
		//CRSMatrix matrix = CRSMatrix.zero(numOfNeurons, input.rows());
		CRSMatrix matrix = CRSMatrix.zero(listOfVectors.size(), listOfVectors.get(0).length());
		
		for (int i = 0; i < listOfVectors.size(); i++)
		{

			matrix.setRow(i, listOfVectors.get(i));
			
		}
		return matrix;
	}
	
	public void setInput(CRSMatrix input)
	{
		this.input = input;
	}
}
