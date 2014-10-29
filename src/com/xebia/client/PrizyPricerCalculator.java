package com.xebia.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.xebia.client.ProductDataHolder.PriceCalculator;
import com.xebia.client.ProductDataHolder.ProductData;

abstract public class PrizyPricerCalculator {

	private ProductDataHolder productDataHolder;
	private int barCode;
	
	public void initialCopy(){
		Map<Integer,ProductData> productsMap = productDataHolder.productsMap;
		Map<Integer,PriceCalculator> calculatorMap = productDataHolder.calculatorMap;
		List<Double> priceCalculatorList = new ArrayList<Double>();

		if(productsMap.containsKey(barCode)){
			ProductData existingData = productsMap.get(barCode);
			for(double price : existingData.pricesList){
				priceCalculatorList.add(price);
			}
			PriceCalculator priceCalculator = new ProductDataHolder().new PriceCalculator();
			priceCalculator.priceCalculatorList = priceCalculatorList;
			calculatorMap.put(barCode, priceCalculator);
		}

		

	}


	public void calculateHighest(){

		Map<Integer,PriceCalculator> calculatorMap = productDataHolder.calculatorMap;

		double highest = 0;

		if(calculatorMap.containsKey(barCode)){
			List<Double> pricesList = calculatorMap.get(barCode).priceCalculatorList;
			Collections.sort(pricesList);
			Collections.reverse(pricesList);
			highest = pricesList.get(0);
			calculatorMap.get(barCode).highest = highest;
		}

	}

	public void calculateLowest(){

		Map<Integer,PriceCalculator> calculatorMap = productDataHolder.calculatorMap;

		double lowest = 0;

		if(calculatorMap.containsKey(barCode)){
			List<Double> pricesList = calculatorMap.get(barCode).priceCalculatorList;
			Collections.sort(pricesList);
			lowest = pricesList.get(0);
			calculatorMap.get(barCode).lowest = lowest;
		}

	}
	
	public void calculateAverage(boolean isInitial){

		Map<Integer,PriceCalculator> calculatorMap = productDataHolder.calculatorMap;

		double avg = 0;
		if(calculatorMap.containsKey(barCode)){
			List<Double> pricesList = calculatorMap.get(barCode).priceCalculatorList;
			double total = sumAllPrices(pricesList);
			int count = pricesList.size();
			avg =  total / count;
			if(Double.isNaN(avg)){
				avg = 0.0;
			}
			avg = Math.round(avg);
			if(isInitial){
				calculatorMap.get(barCode).average = avg;
			}else{
				calculatorMap.get(barCode).finalAverage = avg;
			}
		}

	}

	
	public void noOfPricesCollected(){
		
		Map<Integer,PriceCalculator> calculatorMap = productDataHolder.calculatorMap;
		int count = 0;
		
		if(calculatorMap.containsKey(barCode)){
			count =  calculatorMap.get(barCode).priceCalculatorList.size();
			calculatorMap.get(barCode).noOfPrices = count;
		}
		
	}


	public void removeHighest(int count){

		Map<Integer,PriceCalculator> calculatorMap = productDataHolder.calculatorMap;

		if(calculatorMap.containsKey(barCode)){
			List<Double> pricesList = calculatorMap.get(barCode).priceCalculatorList;
			if(pricesList.size() >= count){
				Collections.sort(pricesList);

				for(int i=count;i>0;i--){
					pricesList.remove(pricesList.get(i-1));
				}
			}
			
		}

	}

	public void removeLowest(int count){

		Map<Integer,PriceCalculator> calculatorMap = productDataHolder.calculatorMap;

		if(calculatorMap.containsKey(barCode)){
			List<Double> pricesList = calculatorMap.get(barCode).priceCalculatorList;
			if(pricesList.size() >= count){
				Collections.sort(pricesList);
				Collections.reverse(pricesList);
				
				for(int i=count;i>0;i--){
					pricesList.remove(pricesList.get(i-1));
				}
			}
			
		}
	}


	public void addPercentage(double percent){

		Map<Integer,PriceCalculator> calculatorMap = productDataHolder.calculatorMap;

		double amount = 0;
		if(calculatorMap.containsKey(barCode)){
			double avg = calculatorMap.get(barCode).finalAverage;
			amount = avg + ( avg * percent );
			calculatorMap.get(barCode).idealPrice = amount;
		}
		
	}
	
	public double sumAllPrices(List<Double> pricesList){

		double total = 0;

		for(double price : pricesList){
			total = total + price;
		}

		return total;

	}

	abstract public void calculate();
	
	public int getBarCode() {
		return barCode;
	}


	public void setBarCode(int barCode) {
		this.barCode = barCode;
	}

	public ProductDataHolder getProductDataHolder() {
		return productDataHolder;
	}


	public void setProductDataHolder(ProductDataHolder productDataHolder) {
		this.productDataHolder = productDataHolder;
	}


}
