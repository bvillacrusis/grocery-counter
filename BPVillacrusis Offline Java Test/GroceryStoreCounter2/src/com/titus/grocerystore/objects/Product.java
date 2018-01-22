package com.titus.grocerystore.objects;

import com.titus.grocerystore.enums.SellingType;
import com.titus.grocerystore.utils.GeneralUtil;

public class Product {
	private String code;				// unique identifier of the product or similar products
	private String description; 		// a short name or description of the product
	private double price;				// price per piece, or per unit weight, unit of measure
										// for weight is assumed to be the same as its weight
										// otherwise, a conversion is needed when computing the product price
	private SellingType sellingType;	// either BY_PIECE, BY_BULK or ON_SALE
	private double weight;				// weight of the product to be sold by bulk

	
	/** Returns the product code.
	 * @return The product code, a unique identifier of the product.
	 */
	public String getCode() {
		return code;
	}

	/** Sets the product code.
	 * @param code The unique identifier of the product.
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/** Returns the brief description of the product.
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/** Sets the brief description of the product.
	 * @param description The brief description of the product.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/** Returns the price of the product.
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/** Sets the price of the product.
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/** Returns the selling type of the product.
	 * @return the sellingType (BY_PIECE, BY_BULK, ON_SALE)
	 */
	public SellingType getSellingType() {
		return sellingType;
	}

	/** Sets the selling type of the product.
	 * @param sellingType The sellingType to set either BY_PIECE, BY_BULK or ON_SALE.
	 */
	public void setSellingType(SellingType sellingType) {
		this.sellingType = sellingType;
	}

	/** Returns the weight of the product.
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString(){
		if(sellingType == SellingType.BY_PIECE){
			return description+String.format("%15s",GeneralUtil.formatPrice(price));
		}else{
			return description+"\t"+String.format("%15s",GeneralUtil.formatPrice(weight))+" "+String.format("%15s",GeneralUtil.formatPrice(price*weight));
		}
		
	}

}
