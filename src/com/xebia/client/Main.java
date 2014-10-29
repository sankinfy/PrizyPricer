package com.xebia.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;

public class Main implements EntryPoint{
	
	@Override
	public void onModuleLoad() {
		
		/** single data holder object to be used across the application.  **/
		ProductDataHolder productDataHolder = new ProductDataHolder();
		
		ProductLoader.set(productDataHolder);
		ProductList.set(productDataHolder);
		ProductViewer.set(productDataHolder);

		
		//Use Installer 1
		ProductViewer.setInstaller(new Installer1());
		
		TabPanel master = new TabPanel();
		
		master.add(ProductLoader.get(), "ProductLoader");
		master.add(ProductList.get(), "ProductList");
		master.add(ProductViewer.get(),"ProductViewer");
		
		RootPanel.get().add(master);
		
	}

}
