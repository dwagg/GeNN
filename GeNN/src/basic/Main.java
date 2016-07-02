package basic;
import java.lang.Math;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("Testing of functions\n" + tanh(Math.PI) + '\n' + sigmoid(Math.PI));
		
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
