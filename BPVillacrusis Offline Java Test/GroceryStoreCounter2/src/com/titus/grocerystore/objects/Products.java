package com.titus.grocerystore.objects;

import java.util.ArrayList;

import com.titus.grocerystore.enums.SellingType;
import com.titus.grocerystore.utils.PricingUtil;

public class Products extends ArrayList<Product>{

	private static final long serialVersionUID = 1L;

	/**
	 *  Returns all the products on this list with the same selling type. 
	 * @param sellingType Selling type of the products to be returned.
	 * @return List of products with the same selling type.
	 */
	public Products getItemsBySellingType(SellingType sellingType){
		Products products = new Products();
		
		for(Product item : this){
			if(item.getSellingType() == sellingType){
				products.add(item);
			}
		}
		return products;
	}
	
	/**
	 * Returns the total price of all the products on the list.
	 * @return total price
	 */
	public double getTotalPrice(){
		return PricingUtil.computeTotalPrice(this);
	}
	
	
}
