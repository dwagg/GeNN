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
	protected int incomingSignals;
	protected int numOfNeurons;
	//protected BasicVector values;
	protected ArrayList<Double> weights;

	public Layer(int numOfNeurons, int incomingSignals)
	{
		this.neurons = new ArrayList<>();
		this.weights = new ArrayList<>();
		this.numOfNeurons = numOfNeurons;
		this.incomingSignals = incomingSignals;
	}
	
	public void initLayer(CRSMatrix input)
	{
		setInput(input);
		generateWeights();
		buildNeurons();
		
		for (Neuron neuron: neurons)
		{
			neuron.setInSignal(input);
		}
	}
	
	public void generateWeights()
	{
		Random rand = new Random();
		for (int i = 0; i < numOfNeurons * incomingSignals;i++)
		{
			weights.add(rand.nextDouble());
		}

	}
	
	public void buildNeurons()
	{
		for (int i =0; i < numOfNeurons; i++)
		{
			ArrayList<Double> neuronWeights = new ArrayList<Double>();
			for (int j = 0; j < incomingSignals; j++)
			{
				neuronWeights.add(weights.get((incomingSignals * i + j)));
			}
			neurons.add(new Neuron(neuronWeights));
		}
	}
	
	//For each one of the neurons calculate a value vector then make a matrix out of all of the value Vectors as the output signal
	public CRSMatrix prepOutSignal()
	{
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
	
	public int getNumOfNeurons()
	{
		return numOfNeurons;
	}
	
	public CRSMatrix getInput()
	{
		return input;
	}
}
