/*
Copyright (c) 2016 David Wagg

This program is released under the "GNU GPL v3" License 
Please see the file License in this distribution for license terms
*/

package basic;
import java.io.File;
import java.util.ArrayList;
import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.matrix.*;
import org.la4j.matrix.sparse.CRSMatrix;
import org.la4j.vector.dense.BasicVector;

public class Network {

	protected InputLayer inputLayer;
	protected HiddenLayers hiddenLayers;
	protected OutputLayer outputlayer;
	protected BasicVector targetValueVector;
	protected double learningRate;
	protected ArrayList<BasicVector> targetOutput;
	//protected BasicVector 
	//Constructor takes an input layer, hidden layers and an output layer as well as a file name where the data is
	public Network(String filePath,int targetColumn, int numOfHiddenLayers,ArrayList<Integer> hiddenLayerSizes, int outputLayerSize, double learningRate, int numOfTrainingExamples)
	{
		
		this.inputLayer = new InputLayer(ImportCSV.getNumOfVars(filePath)-1);
		this.inputLayer.initLayer((CRSMatrix) ImportCSV.ImportData(filePath, targetColumn, numOfTrainingExamples));
		this.hiddenLayers = new HiddenLayers(numOfHiddenLayers,hiddenLayerSizes,this.inputLayer);
		this.hiddenLayers.processOutput();
		this.outputlayer = new OutputLayer(outputLayerSize,this.hiddenLayers.getHiddenOutputLayer());
		this.outputlayer.initLayer(this.hiddenLayers.getHiddenOutputLayer().prepOutSignal());
		this.targetValueVector = ImportCSV.getTargetVector(filePath, targetColumn,numOfTrainingExamples);
		this.learningRate = learningRate;
		this.targetOutput = buildtargetOutput(outputLayerSize); 

		//normalizeTargetVector();
		
		//This will go in the backprop function as the first step, then adjust weights, then do hidden layers
//		this.outputlayer.calculateDelta(this.hiddenLayers.getHiddenOutputLayer(), targetValueVector);
		// run(this.inputLayer.prepOutSignal());
		for(int i = 0; i < 10; i++)
		{
			CRSMatrix m1 = run(this.inputLayer.getInput());
			ArrayList<BasicVector> deltas = backProp(this.outputlayer);
		}

		CRSMatrix m2 = run(ImportCSV.normalizeData((CRSMatrix) ImportCSV.ImportData("./dummy/mnist_test.csv", targetColumn, 15)));
		BasicVector guesses = new BasicVector(m2.rows());
		for(int i = 0; i < m2.rows(); i++)
		{
			Vector a = m2.getRow(i);
			double max = 0;
			int col = -1;
			for (int j = 0; j < m2.columns(); j++)
			{
				if (a.get(j) >= max)
				{
					max = a.get(j);
					col = j;
				}
			}
			guesses.set(i, col);
		}
		int x = 0;
		x = x+1;
		
	}
	
	public CRSMatrix run(CRSMatrix input)
	{
//		CRSMatrix ms = new CRSMatrix(1,784);
//		ms.setRow(0, input.getRow(0));
		this.inputLayer.setInput(input);
		CRSMatrix m = this.inputLayer.prepOutSignal();
		this.hiddenLayers.setInput(m);
		m = this.hiddenLayers.processOutput();
		this.outputlayer.setInput(m);
		return this.outputlayer.prepOutSignal();
	}
	
	public ArrayList<BasicVector> backProp(Layer layer)
	{
		ArrayList<BasicVector> outputDeltas = new ArrayList<>();
		for(int i = 0; i < targetOutput.size(); i++)
		{
			outputDeltas.add(layer.calculateDelta(this.hiddenLayers.getHiddenOutputLayer(), targetOutput.get(i)));
		}
		
		ArrayList<BasicVector> errors = layer.getErrosList(targetOutput);
		ArrayList<BasicVector> dOutdNet = layer.getdOutdNetList();
		ArrayList<BasicVector> dOutdNetH = this.hiddenLayers.getHiddenOutputLayer().getdOutdNetList();
		
		ArrayList<BasicVector> dEdNet = new ArrayList<>();
		for(int i = 0; i < errors.size(); i++)
		{
			BasicVector node_dEdNet = new BasicVector(errors.get(0).length());
			for (int j = 0; j < errors.get(0).length(); j++)
			{
				node_dEdNet.set(j,-1 * errors.get(i).get(j)*dOutdNet.get(i).get(j));
			}
			dEdNet.add(node_dEdNet);
		}
		ArrayList<Double> oWeights = layer.getWeights();
		ArrayList<BasicVector> hiddenDeltas = new ArrayList<>();
		//ArrayList<ArrayList<BasicVector>> hDeltas = new ArrayList<>();
		for (int k = 0; k < dEdNet.get(0).length(); k++)
		{
			BasicVector nodeDelta = new BasicVector(oWeights.size()/dEdNet.size());

			for (int i = 0; i < oWeights.size()/dEdNet.size(); i++)
			{
				BasicVector v = new BasicVector(dEdNet.size());
				for(int j = 0; j < dEdNet.size(); j++)
				{
					v.set(j,oWeights.get(dEdNet.size() * i + j) * dEdNet.get(j).get(k));
				}

				nodeDelta.set(i, v.sum() * dOutdNetH.get(i).get(k));
			}
			
			hiddenDeltas.add(nodeDelta);
		//	hDeltas.add(nodeDeltas);
		}
		this.outputlayer.updateWeights(this.hiddenLayers.getHiddenOutputLayer(), learningRate, outputDeltas);
		this.hiddenLayers.getHiddenOutputLayer().updateWeights(inputLayer, learningRate, hiddenDeltas);
		return outputDeltas;
	}
	
	private ArrayList<BasicVector> buildtargetOutput(int outputLayerSize)
	{
		ArrayList<BasicVector> targetList = new ArrayList<>();
		for (int i = 0; i < this.targetValueVector.length(); i++)
		{
			BasicVector tmp = new BasicVector(outputLayerSize);
			for(int j = 0; j < outputLayerSize; j++)
			{
				if(targetValueVector.get(i) == j)
				{
					tmp.set(j, 1);
				}
				else
				{
					tmp.set(j, 0);
				}
				
			}
			targetList.add(tmp);
		}
		return targetList;
	}
	
	public CRSMatrix getNetworkOutput()
	{
		return this.outputlayer.prepOutSignal();
	}
	
	public CRSMatrix getSoftMaxOutput()
	{
		return this.outputlayer.softMax();
	}
	
	private void normalizeTargetVector()
	{
		this.targetValueVector = ImportCSV.normalizeData(this.targetValueVector);
	}
	
	public static double tanh(double in)
	{
		return Math.tanh(in);
	}
	
	public static double tanhPrime(double in)
	{
		return (1-(tanh(in)*tanh(in)));
	}
	
	public static BasicVector tanh(BasicVector in)
	{
		BasicVector out = new BasicVector(in.length());
		for(int i = 0; i < in.length(); i++)
		{
			out.set(i, tanh(in.get(i)));
		}
		return out;
	}
	
	public static BasicVector tanhPrime(BasicVector in)
	{
		BasicVector out = new BasicVector(in.length());
		for(int i = 0; i < in.length(); i++)
		{
			out.set(i, tanhPrime(in.get(i)));
		}
		return out;
	}
	
	public static BasicVector sigmoid(Vector in)
	{
		BasicVector out = new BasicVector(in.length());
		for (int i = 0; i < in.length(); i++)
		{
			out.set(i, sigmoid(in.get(i)));
		}
		return out;
	}
	
	public static BasicVector sigmoidPrime(Vector in)
	{
		BasicVector out = new BasicVector(in.length());
		
		for (int i = 0; i < in.length(); i++)
		{
			out.set(i, sigmoidPrime(in.get(i)));
		}
		return out;
	}
	
	public static double sigmoid(double in)
	{
		double out = 1d/(1+ Math.exp(-in));
		return out;
	}
	
	public static double sigmoidPrime(double in)
	{
		double out = Math.exp(in)/Math.pow((1 +Math.exp(in)),2);
		return out;
	}
	
	public double meanSquareError(Vector actual)
	{
		double mse = 0;
		for (int i = 0; i < targetValueVector.length(); i++)
		{
			mse += Math.pow(targetValueVector.get(i) - actual.get(i), 2);
		}
		mse = targetValueVector.length() > 0? mse/(2*targetValueVector.length()):-1;
		return mse;
	}
	
//	public ArrayList<BasicVector> getOutputErrors()
//	{
//		return this.outputlayer.getErrosList(targetOutput);
//	}
//	
//	public BasicVector getAverageErrors()
//	{
//		return this.outputlayer.getAverageError(targetValueVector);
//	}
	
	public BasicVector getTargetValueVector()
	{
		return targetValueVector;
	}
	
//	//Thanks to "Welch Labs" tutorials on youtube for help with gradient descent and back propagation. 
//	//Found at 
//	public CRSMatrix costFunctionPrime(Vector targetValues)
//	{
//		CRSMatrix yHat = this.outputlayer.prepOutSignal();
//		for (int i = 0; i < targetValues.length(); i++)
//		{
//			
//		}
//		return CRSMatrix.zero(1, 1);
//	}
	
	//Back prop equation: deltaW.sub(t) = -Epsilon * dE/dW.sub(t) + alpha deltaWsub(t-1)
	//where:
	//	alpha = momentum
	//	epsilon = learning rate
	//	dE/dW.sub(t) = partial derivative of E with respect to W.sub(t) 
}
