/*
Copyright (c) 2016 David Wagg

This program is released under the "GNU GPL v3" License 
Please see the file License in this distribution for license terms
*/

package basic;

import org.la4j.matrix.sparse.CRSMatrix;
import org.la4j.vector.functor.VectorAccumulator;

public class InputLayer extends Layer{
	
	public InputLayer(String filePath)
	{
		super(ImportCSV.getNumOfVars(filePath), ImportCSV.getNumOfVars(filePath),ImportCSV.normalizeData((CRSMatrix) ImportCSV.ImportData(filePath)));
	}
}
