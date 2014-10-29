package com.xebia.client;

import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.xebia.client.ProductDataHolder.PriceCalculator;
import com.xebia.client.constants.ProductConstants;
import com.xebia.client.enums.ProductInfo;

public class ProductViewer {
	
	static PrizyPricerCalculator prizyPricerCalculator ;
	
	static ProductDataHolder productDataHolder;

	static public void set(ProductDataHolder productDataHolderVO){
		productDataHolder = productDataHolderVO;
	}

	static public void setInstaller(PrizyPricerCalculator prizyPricerCalculatorVO){
		prizyPricerCalculator = prizyPricerCalculatorVO;
	}
	
	static public Widget get(){
		
		
		final ListBox productsList = new ListBox();
		productsList.setWidth("150px");
		productsList.setVisibleItemCount(ProductConstants.ITEMS_IN_VIEW); 
		
		for(ProductInfo productInfo : ProductInfo.values()){
			productsList.addItem(productInfo.getProductBarCode() + " = " + productInfo.getProductDesc());
		}
		
		
		final Label barCode = new Label("BarCode");
		final Label barCodeValue = new Label();
		
		final Label prodDesc = new Label("ProductDescription");
		final Label prodDescValue = new Label();
		
		final Label avgPrice = new Label("Average Price");
		final Label avgPriceValue = new Label();

		final Label lowPrice = new Label("Lowest Price");
		final Label lowPriceValue = new Label();
		
		final Label highPrice = new Label("Highest Price");
		final Label highPriceValue = new Label();
		
		final Label idealPrice = new Label("Ideal Price");
		final Label idealPriceValue = new Label();
		
		final Label noOfPrices = new Label("No: of Prices Collected");
		final Label noOfPricesValue = new Label();
		
		hideAndDisplay(barCode, barCodeValue, prodDesc, prodDescValue,
				avgPrice, avgPriceValue, lowPrice, lowPriceValue, highPrice,
				highPriceValue, idealPrice, idealPriceValue, noOfPrices,
				noOfPricesValue,false);
		
		
		productsList.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				
				String selectedItem = productsList.getItemText(productsList.getSelectedIndex());
				selectedItem = selectedItem.substring(0,selectedItem.indexOf('=')).trim();
				int bCode = Integer.parseInt(selectedItem);
				
				prizyPricerCalculator.setProductDataHolder(productDataHolder);
				prizyPricerCalculator.setBarCode(bCode);
				
				prizyPricerCalculator.calculate();
				
				Map<Integer,PriceCalculator> calculatorMap = productDataHolder.calculatorMap;
				barCodeValue.setText(String.valueOf(bCode));
				prodDescValue.setText(ProductInfo.getProdDescByCode(bCode));
				
				if(calculatorMap.containsKey(bCode)){
					avgPriceValue.setText(String.valueOf(calculatorMap.get(bCode).average));
					lowPriceValue.setText(String.valueOf(calculatorMap.get(bCode).lowest));
					highPriceValue.setText(String.valueOf(calculatorMap.get(bCode).highest));
					idealPriceValue.setText(String.valueOf(calculatorMap.get(bCode).idealPrice));
					noOfPricesValue.setText(String.valueOf(calculatorMap.get(bCode).noOfPrices));
				}else{
					avgPriceValue.setText(ProductConstants.NIL_AMOUNT_TO_DISPLAY);
					lowPriceValue.setText(ProductConstants.NIL_AMOUNT_TO_DISPLAY);
					highPriceValue.setText(ProductConstants.NIL_AMOUNT_TO_DISPLAY);
					idealPriceValue.setText(ProductConstants.NIL_AMOUNT_TO_DISPLAY);
					noOfPricesValue.setText(ProductConstants.NIL_AMOUNT_TO_DISPLAY);
				}
				
				
				
				hideAndDisplay(barCode, barCodeValue, prodDesc, prodDescValue,
						avgPrice, avgPriceValue, lowPrice, lowPriceValue, highPrice,
						highPriceValue, idealPrice, idealPriceValue, noOfPrices,
						noOfPricesValue,true);
				
			}
		});
		
		
		
		FlexTable innerTable = new FlexTable();
		innerTable.setWidget(0, 0, barCode);
		innerTable.setWidget(0, 1, barCodeValue);
		innerTable.setWidget(1,0,prodDesc);
		innerTable.setWidget(1,1,prodDescValue);
		innerTable.setWidget(2,0,avgPrice);
		innerTable.setWidget(2,1,avgPriceValue);
		innerTable.setWidget(3,0,lowPrice);
		innerTable.setWidget(3,1,lowPriceValue);
		innerTable.setWidget(4,0,highPrice);
		innerTable.setWidget(4,1,highPriceValue);
		innerTable.setWidget(5,0,idealPrice);
		innerTable.setWidget(5,1,idealPriceValue);
		innerTable.setWidget(6,0,noOfPrices);
		innerTable.setWidget(6,1,noOfPricesValue);
		
		FlexTable table = new FlexTable();
		table.setWidget(0, 0, productsList);
		table.setWidget(0, 1, innerTable);
		
		
		
		
		return table;
	}


	/**
	 * @param barCode
	 * @param barCodeValue
	 * @param prodDesc
	 * @param prodDescValue
	 * @param avgPrice
	 * @param avgPriceValue
	 * @param lowPrice
	 * @param lowPriceValue
	 * @param highPrice
	 * @param highPriceValue
	 * @param idealPrice
	 * @param idealPriceValue
	 * @param noOfPrices
	 * @param noOfPricesValue
	 */
	private static void hideAndDisplay(Label barCode, Label barCodeValue,
			Label prodDesc, Label prodDescValue, Label avgPrice,
			Label avgPriceValue, Label lowPrice, Label lowPriceValue,
			Label highPrice, Label highPriceValue, Label idealPrice,
			Label idealPriceValue, Label noOfPrices, Label noOfPricesValue, boolean value) {
		barCode.setVisible(value); barCodeValue.setVisible(value);
		prodDesc.setVisible(value); prodDescValue.setVisible(value);
		avgPrice.setVisible(value); avgPriceValue.setVisible(value);
		lowPrice.setVisible(value); lowPriceValue.setVisible(value);
		highPrice.setVisible(value); highPriceValue.setVisible(value);
		idealPrice.setVisible(value); idealPriceValue.setVisible(value);
		noOfPrices.setVisible(value); noOfPricesValue.setVisible(value);
	}
	

}
