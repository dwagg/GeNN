/*
Copyright (c) 2016 David Wagg

This program is released under the "GNU GPL v3" License 
Please see the file License in this distribution for license terms
*/

package basic;

import java.util.ArrayList;
import java.util.Random;

import org.la4j.Vector;
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
	}
	
	public void setNeuronInputs()
	{
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
			weights.add(rand.nextDouble()/100);
		}
		System.out.print("");

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
			neurons.add(new Neuron(neuronWeights,this));
		}
	}
	
	//For each one of the neurons calculate a value vector then make a matrix out of all of the value Vectors as the output signal
	public CRSMatrix prepOutSignal()
	{
		ArrayList<BasicVector> listOfVectors = new ArrayList<BasicVector>();
		setNeuronInputs();
		for (int i = 0; i < numOfNeurons; i++)
		{
			listOfVectors.add(neurons.get(i).fire(input));
		}
		CRSMatrix matrix = CRSMatrix.zero(listOfVectors.get(0).length(),listOfVectors.size());
		
		for (int i = 0; i < listOfVectors.size(); i++)
		{

			matrix.setColumn(i, listOfVectors.get(i));
			
		}
		return matrix;
	}
	
	public void setInput(CRSMatrix input)
	{
		this.input = input;
		this.incomingSignals = input.columns();
	}
	
	public int getNumOfNeurons()
	{
		return numOfNeurons;
	}
	
	public CRSMatrix getInput()
	{
		return input;
	}
	
	public boolean isOutputLayer()
	{
		return false;
	}
	
	public boolean isInputLayer()
	{
		return false;
	}
	
	public ArrayList<Double> getWeights()
	{
		return weights;
	}
	
	public ArrayList<Neuron> getNeurons()
	{
		return neurons;
	}
	
	public ArrayList<BasicVector> getErrosList(ArrayList<BasicVector> targetOutput)
	{
		if (!isOutputLayer())
		{
			return null;
		}
		CRSMatrix output = prepOutSignal();
		int length = numOfNeurons;
		ArrayList<BasicVector> errorsList = new ArrayList<>();
		
		for(int j = 0; j < length; j++)
		{
			BasicVector neuronErrors = new BasicVector(targetOutput.size());
			for (int i = 0; i < targetOutput.size();i++)
			{
				double expected = targetOutput.get(i).get(j);
				neuronErrors.set(i, expected - output.get(i,j));
				
			}
			errorsList.add(neuronErrors);
		}
		return errorsList;
	}
	
	public BasicVector getAverageError(ArrayList<BasicVector> targetOutput)
	{
		ArrayList<BasicVector> errorsList = getErrosList(targetOutput);
		BasicVector meanErrors = new BasicVector(errorsList.size());
		int length = errorsList.get(0).length();
		
		for(int i = 0; i < errorsList.size();i++)
		{
			double error = errorsList.get(i).sum()/length;
			meanErrors.set(i, error);
		}
		return meanErrors;
	}
	
	public ArrayList<BasicVector> getdOutdNetList()
	{
		ArrayList<BasicVector> dOutdNetList = new ArrayList<>();
		for (int i = 0; i < numOfNeurons; i++)
		{
			dOutdNetList.add(neurons.get(i).getdOutdNet());
		}
		return dOutdNetList;
	}
	
	public BasicVector calculateDelta(Layer prev, BasicVector targetValueVector)
	{
		BasicVector deltas = new BasicVector(numOfNeurons);
		if (isOutputLayer())
		{
			for (int i = 0; i < numOfNeurons; i++)
			{
				deltas.set(i, neurons.get(i).nodeDelta(targetValueVector.get(i)));
			}
		} 
		else if(!isInputLayer())
		{

		}
		return deltas;
	}
	
	private void pullNodeWeights()
	{
		for(int i = 0; i < numOfNeurons; i++)
		{
			BasicVector neuronWeights = neurons.get(i).getInWeights();
			for (int j = 0; j < neuronWeights.length();j++ )
			{
				weights.set(i * numOfNeurons + j, neuronWeights.get(j));
			}
		}
	}
	
	public void updateWeights(Layer prev, double learningRate, ArrayList<BasicVector> deltas)
	{
		for (int k = 0; k < deltas.size(); k++)
		{
			for (int i = 0; i < numOfNeurons; i++)
			{
				BasicVector updateBy = new BasicVector(prev.getNumOfNeurons());
				for(int j = 0; j < prev.getNumOfNeurons(); j++)
				{
				updateBy.set(j,learningRate * deltas.get(k).get(i) * input.get(k, j));  
				}
				neurons.get(i).updateWeights(updateBy);
			}
		}
		pullNodeWeights();
	}
}
