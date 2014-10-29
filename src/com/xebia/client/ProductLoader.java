package com.xebia.client;

import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.xebia.client.ProductDataHolder.ProductData;
import com.xebia.client.constants.ProductConstants;
import com.xebia.client.enums.ProductInfo;
import com.xebia.client.enums.StoreInfo;

public class ProductLoader {

	static ProductDataHolder productDataHolder;

	static public void set(ProductDataHolder productDataHolderVO){
		productDataHolder = productDataHolderVO;
	}

	static public Widget get(){

		Label store = new Label("Store");

		final ListBox storeList = new ListBox();
		storeList.setWidth("100px");
		storeList.addItem(ProductConstants.NONE_VALUE);
		for(StoreInfo storeInfo : StoreInfo.values()){
			storeList.addItem(storeInfo.getStoreName());
		}

		Label productBarCode = new Label("ProductBarCode");

		final ListBox productBarCodeList = new ListBox();
		productBarCodeList.addItem(ProductConstants.NONE_VALUE);
		productBarCodeList.setWidth("100px");

		storeList.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				String storeName = storeList.getValue(storeList.getSelectedIndex());
				if(ProductConstants.NONE_VALUE.equals(storeName)){
					productBarCodeList.clear();
					productBarCodeList.addItem(ProductConstants.NONE_VALUE);
				}else{
					productBarCodeList.clear();
					productBarCodeList.addItem(ProductConstants.NONE_VALUE);
					for(ProductInfo productInfo:StoreInfo.getProductsForGivenStore(storeName)){
						productBarCodeList.addItem(String.valueOf(productInfo.getProductBarCode()));
					}
				}

			}
		});

		final Label productDesc = new Label("Product Description");
		final Label prodName = new Label();
		productDesc.setVisible(false);prodName.setVisible(false);

		final Label price = new Label("Price");
		final TextBox priceValue = new TextBox();
		price.setVisible(false); priceValue.setVisible(false);

		final Label notes = new Label("Notes");
		final TextBox notesValue = new TextBox();
		notes.setVisible(false); notesValue.setVisible(false);

		final Button add = new Button("Add");
		add.setVisible(false);


		productBarCodeList.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				String barCodeValue = productBarCodeList.getValue(productBarCodeList.getSelectedIndex());
				if(!ProductConstants.NONE_VALUE.equals(barCodeValue)){
					int barCode = Integer.parseInt(productBarCodeList.getValue(productBarCodeList.getSelectedIndex()));
					prodName.setText(ProductInfo.getProdDescByCode(barCode));
					productDesc.setVisible(true);prodName.setVisible(true);
					price.setVisible(true); priceValue.setVisible(true);
					notes.setVisible(true); notesValue.setVisible(true);
					add.setVisible(true);
				}else{
					hideAll(productDesc, prodName, price, priceValue, notes,
							notesValue, add);
				}
			}

		});

		add.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String prodPrice = priceValue.getText();
				if(prodPrice == null || prodPrice.trim().length() == 0){
					Window.alert("Please enter price value.");
					return;
				}
				double amount = 0;
				try{
					amount = Double.parseDouble(prodPrice);
				}catch(NumberFormatException e){
					Window.alert("Entered price value is not valid.");
					priceValue.setValue(ProductConstants.EMPTY_QUOTES);
					priceValue.setFocus(true);
					return;
				}
				String notesVal = notesValue.getText();
				if(notesVal == null || notesVal.trim().length() == 0){
					notesVal = ProductConstants.NONE_VALUE;
				}

				int bCode = Integer.parseInt(productBarCodeList.getValue(productBarCodeList.getSelectedIndex()));
				populateProductDataHolder(bCode, amount, notesVal);
				Window.alert("Price added successfully for the barcode : "+bCode);
				priceValue.setValue(ProductConstants.EMPTY_QUOTES);
				notesValue.setValue(ProductConstants.EMPTY_QUOTES);
				storeList.setItemSelected(0, true);
				productBarCodeList.setItemSelected(0, true);
				hideAll(productDesc, prodName, price, priceValue, notes,
						notesValue, add);

			}
		});

		FlexTable table = new FlexTable();
		table.setHeight("100px");
		table.setWidget(0, 0, store);
		table.setWidget(0, 1, storeList);
		table.setWidget(1, 0, productBarCode);
		table.setWidget(1,1,productBarCodeList);
		table.setWidget(2,0,productDesc);
		table.setWidget(2,1,prodName);
		table.setWidget(3,0,price);
		table.setWidget(3,1,priceValue);
		table.setWidget(4,0,notes);
		table.setWidget(4,1,notesValue);
		table.setWidget(6, 1, add);


		return table;
	}

	/**
	 * hide all the elements when none is selected. 
	 * @param productDesc
	 * @param prodName
	 * @param price
	 * @param priceValue
	 * @param notes
	 * @param notesValue
	 * @param add
	 */
	private static void hideAll(final Label productDesc, final Label prodName,
			final Label price, final TextBox priceValue,
			final Label notes, final TextBox notesValue,
			final Button add) {
		productDesc.setVisible(false);prodName.setVisible(false);
		price.setVisible(false); priceValue.setVisible(false);
		notes.setVisible(false); notesValue.setVisible(false);
		add.setVisible(false);
	}

	/**
	 * add the amount & notes for a given product in the value object holder.
	 * @param barCode
	 * @param amount
	 * @param notes
	 */
	static public void populateProductDataHolder(int barCode, double amount, String notes){

		Map<Integer,ProductData> productsMap = productDataHolder.productsMap;

		if(productsMap.containsKey(barCode)){
			ProductData existingData = productsMap.get(barCode);
			existingData.notes.add(notes);
			existingData.pricesList.add(amount);
		}else{
			ProductData newData = new ProductDataHolder().new ProductData();
			newData.notes.add(notes);
			newData.pricesList.add(amount);
			productsMap.put(barCode, newData);
		}


	}

}
