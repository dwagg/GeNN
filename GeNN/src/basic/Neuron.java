/*
Copyright (c) 2016 David Wagg

This program is released under the "GNU GPL v3" License 
Please see the file License in this distribution for license terms
*/
package basic;

import java.util.ArrayList;

import org.la4j.matrix.sparse.CRSMatrix;
import org.la4j.vector.dense.BasicVector;

public class Neuron {
	protected BasicVector inWeights;
	protected CRSMatrix inSignal;
	protected BasicVector values;
	protected int inputSize;
	public Neuron(ArrayList<Double> weightList, CRSMatrix inSignal)
	{

		inputSize = weightList.size();
		this.inSignal = inSignal;
		double [] tmpWeights = new double [inputSize];
		for(int i = 0; i < inputSize; i++)
		{
			tmpWeights[i] = weightList.get(i);
		}
		this.inWeights = new BasicVector(tmpWeights);
	}
	
	//Taking the input signal and multiplying it by the weight vector to get the values vector, then passing the values in the values vector through the activation function
	public BasicVector processInput(CRSMatrix matrix)
	{
	
		BasicVector valueVector = (BasicVector) matrix.multiply(inWeights);
		return applyActivation(valueVector);
	}
	
	//For each of the values in the value vector apply the activation function and build a vector of the results
	public BasicVector applyActivation(BasicVector valueVector)
	{
		double [] tmpValArr = new double[valueVector.length()];
		for (int i = 0; i < valueVector.length(); i++)
		{
			double tmpVal = Network.sigmoid(valueVector.get(i));
			tmpValArr[i] = tmpVal;
		}
		
		return new BasicVector(tmpValArr);
	}
	
}
