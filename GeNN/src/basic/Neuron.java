/*
Copyright (c) 2016 David Wagg

This program is released under the "GNU GPL v3" License 
Please see the file License in this distribution for license terms
*/
package basic;

import java.util.ArrayList;

import org.la4j.Vector;
import org.la4j.matrix.sparse.CRSMatrix;
import org.la4j.vector.dense.BasicVector;

public class Neuron {
	protected BasicVector inWeights;
	protected CRSMatrix inSignal;
	protected BasicVector values;
	protected int inputSize;
	protected Layer layer;
	public Neuron(ArrayList<Double> weightList, Layer layer)
	{

		inputSize = weightList.size();
		double [] tmpWeights = new double [inputSize];
		this.layer = layer;
		for(int i = 0; i < inputSize; i++)
		{
			tmpWeights[i] = weightList.get(i);
		}
		this.inWeights = new BasicVector(tmpWeights);
	}
	
	public void setInSignal(CRSMatrix inSignal)
	{
		this.inSignal = inSignal;
	}
	
	//Taking the input signal and multiplying it by the weight vector to get the values vector, then passing the values in the values vector through the activation function
	public BasicVector processInput(CRSMatrix matrix)
	{
		CRSMatrix tmp = new CRSMatrix(inSignal.rows(), inSignal.columns());
		for(int i =0; i < tmp.rows(); i++)
		{
			for( int j = 0; j < tmp.columns(); j++)
			{
				tmp.set(i, j, inSignal.get(i, j));
			}
		}
		Vector valueVector = inSignal.multiply(inWeights);
		BasicVector basicVector = new BasicVector(valueVector.length());
		for (int i = 0; i < valueVector.length();i++)
		{
			basicVector.set(i, valueVector.get(i));
		}
		return basicVector;
	}
	
	public BasicVector fire(CRSMatrix matrix)
	{
		return applyActivation(processInput(matrix));
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
	
	public double nodeDelta(double expected)
	{
		BasicVector sums = this.fire(inSignal);
		BasicVector delta = new BasicVector(sums.length());
		BasicVector dOutdNet = getdOutdNet();
		for(int i = 0; i < delta.length(); i++)
		{

			delta.set(i, -1 * ((expected - sums.get(i)) * dOutdNet.get(i)));
		}
				
		return delta.sum()/delta.length();
	}
	
	public BasicVector getdOutdNet()
	{
		BasicVector sums = this.fire(inSignal);
		BasicVector dOutdNet = new BasicVector(sums.length());
		for (int i = 0; i < sums.length(); i++)
		{
			dOutdNet.set(i, sums.get(i)* (1-sums.get(i)));
		}
		return dOutdNet;
	}
	
	public BasicVector getError(double expected)
	{
		BasicVector errors = this.fire(inSignal);
		for (int i = 0; i < errors.length(); i++)
		{
			errors.set(i, errors.get(i) - expected);
		}
		return errors;
	}
	
	public void updateWeights(BasicVector updateBy)
	{
		for (int i = 0; i < inWeights.length(); i++)
		{
			if (updateBy.get(i) < 0)
			{
				updateBy.set(i, updateBy.get(i) * 3);
			}
			inWeights.set(i, inWeights.get(i) - updateBy.get(i));
		}
	}
	
	public BasicVector getInWeights()
	{
		return inWeights;
	}
	
}
