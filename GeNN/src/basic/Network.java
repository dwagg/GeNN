/*
Copyright (c) 2016 David Wagg

This program is released under the "GNU GPL v3" License 
Please see the file License in this distribution for license terms
*/

package basic;
import java.io.File;
import java.util.ArrayList;
import org.la4j.Matrix;
import org.la4j.matrix.*;
import org.la4j.matrix.sparse.CRSMatrix;

public class Network {

	protected InputLayer inputLayer;
	protected HiddenLayers hiddenLayers;
	protected OutputLayer outputlayer;
	
	//Constructor takes an input layer, hidden layers and an output layer as well as a file name where the data is
	public Network(String filePath, int numOfHiddenLayers,ArrayList<Integer> hiddenLayerSizes, int outputLayerSize)
	{
		
		this.inputLayer = new InputLayer(ImportCSV.getNumOfVars(filePath));
		this.hiddenLayers = new HiddenLayers(numOfHiddenLayers,hiddenLayerSizes,this.inputLayer);
		this.outputlayer = new OutputLayer(1,this.hiddenLayers.getHiddenOutputLayer());
	}
	
	public static double tanh(double in)
	{
		return Math.tanh(in);
	}
	
	public static double tanhPrime(double in)
	{
		return (1-(tanh(in)*tanh(in)));
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
}
