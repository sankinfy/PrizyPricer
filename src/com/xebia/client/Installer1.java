package com.xebia.client;

/**
 * an installer using the below logic to calculate the ideal price.
 * @author T_SankaraS1
 *
 */
public class Installer1 extends PrizyPricerCalculator {
	

	@Override
	public void calculate() {
		
		initialCopy();
		calculateHighest();
		calculateLowest();
		calculateAverage(true);
		noOfPricesCollected();
		
		
		//calculate ideal price
		removeHighest(2);
		removeLowest(2);
		calculateAverage(false);
		addPercentage(.2);
		
		
	}
	
	

}


/**
 * If we want to add another installer with different ideal price calculation logic, 
 * we can introduce corresponding installer sub class with its desired features calling the calculating methods as desired.   
 * 
 * */

