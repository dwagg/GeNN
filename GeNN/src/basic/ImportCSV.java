/*
Copyright (c) 2016 David Wagg

This program is released under the "GNU GPL v3" License 
Please see the file License in this distribution for license terms
*/

package basic;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.matrix.sparse.CRSMatrix;
import org.la4j.vector.dense.BasicVector;

public class ImportCSV {
	protected String [] data;
	
	//Importing data from a csv file with relative path fileName
	public static Matrix ImportData(String filePath, int targetColumn)
	{
		int numOfColumns=0;
		int numOfRows = 0;
		CRSMatrix matrix = new CRSMatrix(0, 0);
		ArrayList<String> data = new ArrayList<String>();
		URL url = ClassLoader.getSystemClassLoader().getResource(filePath);
		try {
			//Find the url to the file from the filePAth
			
			File inFile = new File(url.toURI());
			//Make file scanner
			Scanner fileScanner = new Scanner(inFile);
			fileScanner.useDelimiter("\n");
			
			//While file has another line grab line to process
			while (fileScanner.hasNext())
			{
				//splitting all of the values from the line into the separate values
				String [] individual = fileScanner.next().split(",");
				if (numOfColumns == 0)
					numOfColumns = individual.length - 1;
				if (individual.length != 0)
					numOfRows++;
				//for all data extracted, add it row-wise into a list 
				for (int i = 0; i < numOfColumns + 1; i++)
				{
					if (i != targetColumn)
					{
						//depending on platform's new line policy remove carriage returns
						if (individual[i].endsWith("\r"))
							individual[i] = individual[i].substring(0, individual[i].length()-1);
						data.add(individual[i]);
					}
				}

			}
		fileScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("The file: " + filePath + " could not be found.");
			e.printStackTrace();
		} 
		catch (URISyntaxException e) {
		 	System.out.println("The file path: " + url.toString() + " caused a syntax exception.");
			e.printStackTrace();
		}
		//Get row-wise data from list into array for
		double [] dataConverted = new double[numOfColumns * numOfRows];
		for (int i = 0; i < numOfColumns * numOfRows; i++){
			dataConverted[i] = Double.parseDouble(data.get(i));
		}
		matrix = CRSMatrix.from1DArray(numOfRows,numOfColumns, dataConverted);
		return matrix;
	}
	
	public static CRSMatrix normalizeData(Matrix matrix)
	{
		ArrayList<Double> norms = new ArrayList<Double>();
		ArrayList<Double> stdDevs = new ArrayList<Double>();
		ArrayList<Double> aves = new ArrayList<Double>();
		//First get the averages of the columns and the standard deviation of each column
		for (int i = 0; i < matrix.columns(); i++)
		{
			double ave = 0;
			Vector colVector = matrix.getColumn(i);
			ave += colVector.sum() / colVector.length();
			aves.add(ave);
			double stdDev = 0;
			for (int j = 0; j < colVector.length(); j++)
			{
				stdDev += Math.pow(colVector.get(j) - ave,2);
			}
			stdDev = Math.sqrt(stdDev/colVector.length());
			stdDevs.add(stdDev);
		}
		for (int j = 0; j < matrix.rows(); j++)
		{	
			for (int i = 0; i < matrix.columns(); i++)
			{
				double tmp = matrix.getColumn(i).get(j) - aves.get(i);
				if (stdDevs.get(i) != 0)
					tmp /= stdDevs.get(i);
				norms.add(tmp);
			}	
		}
		double [] arrNorms = new double [norms.size()];
		for (int i = 0; i < norms.size(); i++)
		{
			arrNorms[i] = norms.get(i);
		}
		return CRSMatrix.from1DArray(matrix.rows(), matrix.columns(), arrNorms);
	}
	
	public static BasicVector normalizeData(BasicVector vector)
	{
		ArrayList<Double> norms = new ArrayList<Double>();
	//	ArrayList<Double> stdDevs = new ArrayList<Double>();
		double ave = 0;
		//First get the averages of the columns and the standard deviation of each column
		
		ave += vector.sum() / vector.length();

		double stdDev = 0;
		for (int i = 0; i < vector.length(); i++)
		{
			stdDev += Math.pow(vector.get(i) - ave,2);
		}
		stdDev = Math.sqrt(stdDev/vector.length());
	//	stdDevs.add(stdDev);
		
		for (int j = 0; j < vector.length(); j++)
		{	

			double tmp = vector.get(j) - ave;
			if (stdDev != 0)
				tmp /= stdDev;
			norms.add(tmp);
				
		}
		double [] arrNorms = new double [norms.size()];
		for (int i = 0; i < norms.size(); i++)
		{
			arrNorms[i] = norms.get(i);
		}
		return new BasicVector(arrNorms);
	}
	
	public static int getNumOfVars(String filePath)
	{
		int sizeOfInput = 0;
		
		URL url = ClassLoader.getSystemClassLoader().getResource(filePath);
		try {
			//Find the url to the file from the filePAth
			
			File inFile = new File(url.toURI());
			//Make file scanner
			Scanner fileScanner = new Scanner(inFile);
			fileScanner.useDelimiter("\n");
			
			if (fileScanner.hasNext())
			{
				String [] inputs = fileScanner.next().split(",");
				sizeOfInput = inputs.length;
			}
			
			fileScanner.close();
			} catch (FileNotFoundException e) {
				System.out.println("The file: " + filePath + " could not be found.");
				e.printStackTrace();
			} 
			catch (URISyntaxException e) {
			 	System.out.println("The file path: " + url.toString() + " caused a syntax exception.");
				e.printStackTrace();
			}
		return sizeOfInput;
	}
	
	public static BasicVector getTargetVector(String filePath, int targetColumn)
	{
		ArrayList<Double> targetValueList = new ArrayList<>();
		URL url = ClassLoader.getSystemClassLoader().getResource(filePath);
		try {
			
			File inFile = new File(url.toURI());
			Scanner scanner = new Scanner(inFile);
			scanner.useDelimiter("\n");
			int numOfVars = getNumOfVars(filePath);
			while(scanner.hasNext())
			{
				String tmp = scanner.next();
				String [] valueArr = new String [numOfVars];
				valueArr = tmp.split(",");
				targetValueList.add(Double.valueOf(valueArr[targetColumn]));
			}
			scanner.close();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double [] targetArr = new double[targetValueList.size()];
		for (int i =0; i < targetValueList.size(); i++)
		{
			targetArr[i] = targetValueList.get(i);
		}
		return new BasicVector(targetArr);

	}
	
}
