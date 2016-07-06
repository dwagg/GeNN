/*
Copyright (c) David Wagg 2016

This program is released under the "GNU GPL v3" License
Please see the file License in this distribution for license terms
*/

package basic;

import java.util.ArrayList;

public class HiddenLayers {
	private int numOfLayers;
	private ArrayList<Integer> sizeOfLayers;
	
	public HiddenLayers(int numOfLayers, int[] sizeOfLayers)
	{
		assert (numOfLayers == sizeOfLayers.length);
		this.sizeOfLayers = new ArrayList<Integer>();
		this.numOfLayers = numOfLayers;
		for (int i = 0; i < numOfLayers; i++)
		{
			
		}
		
	}
}
