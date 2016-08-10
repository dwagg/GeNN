/*
Copyright (c) 2016 David Wagg

This program is released under the "GNU GPL v3" License 
Please see the file License in this distribution for license terms
*/

package basic;

import java.util.ArrayList;

import org.la4j.matrix.sparse.CRSMatrix;
import org.la4j.vector.dense.BasicVector;

public class OutputLayer extends Layer{

	public OutputLayer(int numOfNeurons, Layer hiddenOutputLayer){
		super(numOfNeurons, hiddenOutputLayer.prepOutSignal().columns());
	}
	
//	public CRSMatrix prepOutSignal()
//	{
//		CRSMatrix out = super.prepOutSignal();
//		neurons.get(0).nodeDelta(.1);
//		return out;
//	}
	
	public CRSMatrix softMax()
	{
		ArrayList<BasicVector> vectorList = new ArrayList<>();
		CRSMatrix matrix = prepOutSignal();
		double denominator = 0;
		
		for(int i = 0; i < matrix.columns(); i++)
		{
			BasicVector tmp = new BasicVector(matrix.rows());
			double value = 0;
			denominator = 0;
			for (int j = 0; j < matrix.rows(); j++ )
			{
				denominator += Math.exp(matrix.get(j, i));	
				
			}
			for(int k = 0; k < tmp.length(); k++)
			{
				value = Math.exp(matrix.get(k, i)) / denominator;
				tmp.set(k, value);
			}
			vectorList.add(tmp);
		}
		CRSMatrix toReturn = CRSMatrix.zero(matrix.rows(), matrix.columns());
		
		for (int i = 0; i < vectorList.size(); i++)
		{
			toReturn.setColumn(i,vectorList.get(i));
		}
		
		return toReturn;
	}
	
	public boolean isOutputLayer()
	{
		return true;
	}
}
