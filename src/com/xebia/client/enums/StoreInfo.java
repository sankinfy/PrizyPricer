package com.xebia.client.enums;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * maintains the table info store wise holding the products details.
 * @author T_SankaraS1
 *
 */
public enum StoreInfo {

	STORE_1(1,"FirstStore",
			new LinkedHashSet<ProductInfo>(Arrays.asList(ProductInfo.APPLE,ProductInfo.ORANGE)));

	private int storeId;
	private String storeName;
	private Set<ProductInfo> productsList;


	private StoreInfo(int storeId, String storeName,Set<ProductInfo> productsList) {
		this.storeId = storeId;
		this.storeName = storeName;
		this.productsList = productsList;
	}


	public String getStoreName() {
		return storeName;
	}


	public int getStoreId() {
		return storeId;
	}


	public Set<ProductInfo> getProductsList() {
		return productsList;
	}


	/**
	 * return the list of products present in a given store. 
	 * @param storeName
	 * @return
	 */
	static public Set<ProductInfo> getProductsForGivenStore(String storeName){
		Set<ProductInfo> productsList = null;
		for(StoreInfo storeInfo : StoreInfo.values()){
			if(storeName.equals(storeInfo.getStoreName())){
				productsList = storeInfo.getProductsList();
				break;
			}
		}
		return productsList;
	}


}
