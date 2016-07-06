/*
Copyright (c) David Wagg 2016

This program is released under the "GNU GPL v3" License 
Please see the file License in this distribution for license terms
*/

package basic;
import java.lang.Math;
public class Main {

	public static void main(String[] args) {

		System.out.println("test");
		double [][] x = new double[1][];
		x[0] = new double [3];
		for (int i =0; i < 3; i++)
		{
			x[0][i] = i+2;
		}
		
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
