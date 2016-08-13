/*
Copyright (c) 2016 David Wagg

This program is released under the "GNU GPL v3" License 
Please see the file License in this distribution for license terms
*/

package basic;
import java.io.File;
import java.lang.Math;
import java.util.ArrayList;

import org.la4j.matrix.sparse.CRSMatrix;
import org.la4j.vector.dense.BasicVector;
public class Main {
	//These number highly impacts how long execution takes. Accuracy vs Time tradeoff. 
	public final static int NUM_OF_TRAINING = 200;
	public final static int NUM_OF_GENERATIONS = 6;
	public final static String TRAINING_FILE_PATH = "./dummy/mnist_train.csv";
	public final static String TEST_FILE_PATH = "./dummy/mnist_test.csv";
	public static void main(String[] args) 
	{
		System.out.println("print");
		ArrayList<Integer> hiddenLayerSizes = new ArrayList<>();
		hiddenLayerSizes.add(200);

		Network net = new Network(TRAINING_FILE_PATH,TEST_FILE_PATH,0,1,hiddenLayerSizes,10,.001d, NUM_OF_TRAINING,NUM_OF_GENERATIONS);


	}
}
