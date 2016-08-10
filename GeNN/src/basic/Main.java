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

	public static void main(String[] args) 
	{
		String filePath = "./dummy/mnist_train.csv";
		System.out.println("print");
		ArrayList<Integer> hiddenLayerSizes = new ArrayList<>();
		hiddenLayerSizes.add(150);

		Network net = new Network(filePath,0,1,hiddenLayerSizes,10,.001d, 200);


	}
}
