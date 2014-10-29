package com.xebia.client.enums;

/**
 * similar to a database table with product info with unique bar codes.
 * @author T_SankaraS1
 *
 */
public enum ProductInfo {

	APPLE(1,"Apple"),
	ORANGE(2,"Orange"),
	BANANA(3,"Banana"),
	MANGO(4,"Mango"),
	PEN(5,"Pen"),
	PENCIL(6,"Pencil"),
	ERASER(7,"Eraser"),
	NOTEBOOK(8,"Notebook"),
	BLADE(9,"Blade"),
	SCISSORS(10,"Scissors"),
	KNIFE(11,"Knife"),
	MOBILE(12,"Mobile"),
	WATCH(13,"Watch");
	

	private int productBarCode;
	private String productDesc;


	private ProductInfo(int productCode,String productDesc)
	{
		this.productBarCode = productCode;
		this.productDesc = productDesc;
	}


	public int getProductBarCode() {
		return productBarCode;
	}


	public String getProductDesc() {
		return productDesc;
	}


	/**
	 * return the product description for a given bar code. 
	 * @param barCode
	 * @return
	 */
	static public String getProdDescByCode(int barCode){
		String desc = null;
		for(ProductInfo productInfo : ProductInfo.values()){
			if(barCode == productInfo.getProductBarCode()){
				desc = productInfo.getProductDesc();
				break;
			}
		}
		return desc;
	}
	

	
}
