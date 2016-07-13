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
public class Main {

	public static void main(String[] args) 
	{
		System.out.println("print");
		InputLayer inLayer = new InputLayer(3);
		inLayer.initLayer(ImportCSV.normalizeData(ImportCSV.ImportData("./dummy/data.csv")));
//		Layer l = new Layer(4,inLayer.getInput().columns());
//		l.initLayer(inLayer.getInput());
		ArrayList<Integer> hiddenLayerSizes = new ArrayList<>();
		hiddenLayerSizes.add(4);
		HiddenLayers hid = new HiddenLayers(1,hiddenLayerSizes,inLayer);
		CRSMatrix hOut = hid.processOutput();
	//	CRSMatrix lOut = l.prepOutSignal();
		OutputLayer outLayer = new OutputLayer(1,hOut.columns());
		outLayer.initLayer(hOut);
		CRSMatrix out = outLayer.prepOutSignal();
		ImportCSV.ImportData("./dummy/data.csv");
	}
}
