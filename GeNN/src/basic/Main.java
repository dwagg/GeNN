/*
Copyright (c) 2016 David Wagg

This program is released under the "GNU GPL v3" License 
Please see the file License in this distribution for license terms
*/

package basic;
import java.io.File;
import java.lang.Math;

import org.la4j.matrix.sparse.CRSMatrix;
public class Main {

	public static void main(String[] args) 
	{
		System.out.println("print");
		InputLayer inLayer = new InputLayer("./dummy/data.csv");
		Layer l = new Layer(4,inLayer.getInput().columns(), inLayer.getInput());
		CRSMatrix lOut = l.prepOutSignal();
		ImportCSV.ImportData("./dummy/data.csv");
	}
}
