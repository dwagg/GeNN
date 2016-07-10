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
	protected Matrix matrix;
	protected InputLayer inputLayer;
	protected HiddenLayers hiddenLayers;
	protected OutputLayer outputlayer;
	
	//Constructor takes an input layer, hidden layers and an output layer as well as a file name where the data is
	public Network(InputLayer inputLayer, HiddenLayers hiddenLayers, OutputLayer outputLayer, String dataFileName)
	{
		this.inputLayer = inputLayer;
		this.hiddenLayers = hiddenLayers;
		this.outputlayer = outputLayer;
		
		/*
		char in = 'x';
		do {
			matrix << file;
		}while(x != file.eof());
		 */
		//construct the matrices for the layers using data file and the size of the layers/connections 
	}
	
	public Matrix buildInputDataMatrix(File dataFile, InputLayer inputLayer)
	{
		CRSMatrix m = CRSMatrix.zero(10, 10);
		return m;
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
