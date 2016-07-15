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
		String filePath = "./dummy/data.csv";
		System.out.println("print");
		InputLayer inLayer = new InputLayer(3);
		inLayer.initLayer((CRSMatrix) ImportCSV.ImportData(filePath,1));
//		Layer l = new Layer(4,inLayer.getInput().columns());
//		l.initLayer(inLayer.getInput());
		ArrayList<Integer> hiddenLayerSizes = new ArrayList<>();
		hiddenLayerSizes.add(4);
		HiddenLayers hid = new HiddenLayers(1,hiddenLayerSizes,inLayer);
		CRSMatrix hOut = hid.processOutput();
	//	CRSMatrix lOut = l.prepOutSignal();
		OutputLayer outLayer = new OutputLayer(1,hid.getHiddenOutputLayer());
		outLayer.initLayer(hOut);
		//CRSMatrix out = outLayer.prepOutSignal();
		BasicVector targetValues = ImportCSV.getTargetVector(filePath, 1);
		Network net = new Network(filePath,1,1,hiddenLayerSizes,2);
		CRSMatrix out = net.getSoftMaxOutput();
		for (int i = 0; i < out.rows(); i++)
		{
			for (int j = 0; j < out.columns(); j++)
			{
				System.out.print(out.get(i, j) + " ");
			}
			System.out.println("");
		}

		ImportCSV.ImportData("./dummy/data.csv",1);
	}
}
