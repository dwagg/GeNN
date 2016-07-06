package basic;

import java.util.ArrayList;

import org.la4j.Matrix;
import org.la4j.matrix.*;

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
		 
	}
}
