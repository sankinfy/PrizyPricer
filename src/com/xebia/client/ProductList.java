package com.xebia.client;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.xebia.client.constants.ProductConstants;
import com.xebia.client.enums.ProductInfo;

public class ProductList {
	
	static ProductDataHolder productDataHolder;

	static public void set(ProductDataHolder productDataHolderVO){
		productDataHolder = productDataHolderVO;
	}
	
	static public Widget get(){
		
		final ListBox productsList = new ListBox();
		productsList.setWidth("200px");
		productsList.setVisibleItemCount(ProductConstants.ITEMS_TO_DISPLAY); 
		
		for(ProductInfo productInfo : ProductInfo.values()){
			productsList.addItem(productInfo.getProductBarCode() + " = " + productInfo.getProductDesc());
		}
		
		Label bCodeLabel = new Label("Search by bar code");
		final TextBox bCodeValue = new TextBox();
		
		bCodeValue.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				String value = bCodeValue.getText();
				if(value != null && value.trim().length() > 0){
					int bcode = 0;
					try{
						bcode = Integer.parseInt(value);
					}catch(NumberFormatException e){
						Window.alert("Enter valid bar code.");
						bCodeValue.setText(ProductConstants.EMPTY_QUOTES);
						disableSelection(productsList);
						return;
					}
					if(productsList.getItemCount() < bcode){
						Window.alert("No item with this bar code");
						bCodeValue.setText(ProductConstants.EMPTY_QUOTES);
						disableSelection(productsList);
						return;
					}else{
						productsList.setSelectedIndex(bcode-1);
					}
					
				}else{
					disableSelection(productsList);
				}
				
			}

			/**
			 * @param productsList
			 */
			private void disableSelection(final ListBox productsList) {
				if(productsList.getSelectedIndex() != -1){
					productsList.setItemSelected(productsList.getSelectedIndex(), false);
				}
			}
		});
		
		Label pageSearch = new Label("Page Search");
		final TextBox pageValue = new TextBox();
		final Label pageDisplay = new Label();
		int pages = productsList.getItemCount() / ProductConstants.ITEMS_TO_DISPLAY;
		if(productsList.getItemCount() % ProductConstants.ITEMS_TO_DISPLAY > 0){
			pages = pages + 1;
		}
		final int totalPages = pages;
		
		pageValue.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				
				pageNavigation(productsList, bCodeValue, pageValue,
						pageDisplay, totalPages);
			}
			
		});
		
		pageValue.addKeyDownHandler(new KeyDownHandler() {
			
			@Override
			public void onKeyDown(KeyDownEvent event) {
				
				pageNavigation(productsList, bCodeValue, pageValue,
						pageDisplay, totalPages);
						
			}
			
		});
		
		pageValue.addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				pageNavigation(productsList, bCodeValue, pageValue,
						pageDisplay, totalPages);
										
			}
		});
		
		FlexTable table = new FlexTable();
		table.setHeight("100px");
		
		table.setWidget(0, 0, productsList);
		table.setWidget(0,1,bCodeLabel);
		table.setWidget(0,2,bCodeValue);
		table.setWidget(1,0,pageSearch);
		table.setWidget(1,1,pageValue);
		table.setWidget(1,2,pageDisplay);
		
		table.getFlexCellFormatter().setRowSpan(0, 0, 2);
		return table;
	}
	
	/**
	 * @param pageValue
	 * @param pageDisplay
	 */
	private static void retry(final TextBox pageValue, final Label pageDisplay) {
		pageValue.setText(ProductConstants.EMPTY_QUOTES);
		pageDisplay.setText(ProductConstants.EMPTY_QUOTES);pageDisplay.setVisible(false);
		return;
	}
	
	private static void displayPageDetails(final Label pageDisplay,int start,int end,final ListBox productsList){
		pageDisplay.setText("Displaying "+start+" - "+end+" of "+productsList.getItemCount());
	}
	
	/**
	 * @param productsList
	 * @param bCodeValue
	 * @param pageValue
	 * @param pageDisplay
	 * @param totalPages
	 */
	private static void pageNavigation(final ListBox productsList,
			final TextBox bCodeValue, final TextBox pageValue,
			final Label pageDisplay, final int totalPages) {
		bCodeValue.setText(ProductConstants.EMPTY_QUOTES);
		productsList.setVisibleItemCount(ProductConstants.ITEMS_TO_DISPLAY); 
		String value = pageValue.getText();
		if(value != null && value.trim().length() > 0){
			int pageNum = 1;
			
			try{
				pageNum = Integer.parseInt(value);
			}catch(NumberFormatException e){
				Window.alert("Enter valid page number.");
				retry(pageValue, pageDisplay);
				return;
			}
			
			if(pageNum > totalPages){
				Window.alert("Number exceeds the max no: of pages.");
				retry(pageValue, pageDisplay);
				return;
			}
			
			if(pageNum == 0 ){
				Window.alert("Page starts from 1");
				retry(pageValue, pageDisplay);
				return;
			}
			
			int firstRec = (pageNum -1) * ProductConstants.ITEMS_TO_DISPLAY;
			int lastRec = firstRec+ProductConstants.ITEMS_TO_DISPLAY;
			if(lastRec > productsList.getItemCount()){
				lastRec =  productsList.getItemCount() ;
			}
			
			pageDisplay.setVisible(true);
			displayPageDetails(pageDisplay, firstRec+1, lastRec,productsList);	
			
			int pageNavigator = firstRec;
			productsList.setSelectedIndex(pageNavigator);
			productsList.setItemSelected(pageNavigator, false);
			
			pageNavigator = firstRec + ProductConstants.ITEMS_TO_DISPLAY - 1 ;
			if(pageNavigator > productsList.getItemCount()){
				pageNavigator =  productsList.getItemCount() - 1  ;
				productsList.setSelectedIndex(pageNavigator);
				productsList.setItemSelected(pageNavigator, false);
				productsList.setVisibleItemCount(productsList.getItemCount() % ProductConstants.ITEMS_TO_DISPLAY);
			}else{
				productsList.setSelectedIndex(pageNavigator);
				productsList.setItemSelected(pageNavigator, false);
			}
			
			
			
		}else{
			pageValue.setText(ProductConstants.EMPTY_QUOTES);
			pageDisplay.setText(ProductConstants.EMPTY_QUOTES);pageDisplay.setVisible(false);
			productsList.setSelectedIndex(0);
			productsList.setItemSelected(0, false);
		}
	}
	

}
