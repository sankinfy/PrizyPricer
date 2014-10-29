package com.xebia.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProductDataHolder {
	
	Map<Integer,ProductData> productsMap = new HashMap<Integer, ProductDataHolder.ProductData>(); 
	
	Map<Integer,PriceCalculator> calculatorMap = new HashMap<Integer, ProductDataHolder.PriceCalculator>();
	
	class ProductData {
		List<Double> pricesList = new ArrayList<Double>();
		Set<String> notes = new HashSet<String>();
	}
	
	class PriceCalculator{
		
		double average = 0;
		double highest = 0;
		double lowest = 0;
		int noOfPrices;
		
		List<Double> priceCalculatorList = new ArrayList<Double>();
		double finalAverage = 0;
		double idealPrice = 0;
		
	}

}
