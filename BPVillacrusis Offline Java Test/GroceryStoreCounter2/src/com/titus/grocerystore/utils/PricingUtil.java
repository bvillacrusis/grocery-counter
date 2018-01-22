package com.titus.grocerystore.utils;

import com.titus.grocerystore.enums.SellingType;
import com.titus.grocerystore.objects.Products;
import com.titus.grocerystore.objects.Product;

public class PricingUtil {
	
	/**
	 * Returns the total price of a set of products.
	 * @param products The list of products where total price will be computed.
	 * @return
	 */
	public static double computeTotalPrice(Products products){		
		return computeTotalPrice(products.getItemsBySellingType(SellingType.BY_PIECE),SellingType.BY_PIECE)
			+ computeTotalPrice(products.getItemsBySellingType(SellingType.BY_BULK),SellingType.BY_BULK);
	}
	
	/**
	 * Returns the total price of aset of products with the
	 * same selling type.
	 * @param products The list of products where total price will be computed
	 * @param sellingType Selling type of the product. Either BY_PIECE, BY_BULK or ON_SALE.
	 * @return Total price of the products.
	 */
	private static double computeTotalPrice(Products products, SellingType sellingType){
		if(sellingType == SellingType.BY_PIECE){			
			return computeTotalPriceSoldByPiece(products);			
		}else if(sellingType == SellingType.BY_BULK){
			return computeTotalPriceSoldByBulk(products);
		}else{
			return 0.0;
		}	
	}
	
	/**
	 * Returns the total price of the products sold by piece.
	 * @param products List of products sold by piece.
	 * @return Total price of the products.
	 */
	private static double computeTotalPriceSoldByPiece(Products products){
		double totalPrice = 0.0;
		for(Product product : products){
			if(product.getSellingType().equals(SellingType.BY_PIECE)){
				totalPrice += product.getPrice();
			}
		}
		return totalPrice;
	}
	
	/**
	 * Returns the total price of the products sold by bulk.
	 * @param products List of products sold by bulk.
	 * @return Total price of the products.
	 */
	private static double computeTotalPriceSoldByBulk(Products products){
		double totalPrice = 0.0;
		for(Product product : products){
			if(product.getSellingType().equals(SellingType.BY_BULK)){
				totalPrice += product.getPrice()*product.getWeight();
			}
		}
		return totalPrice;
	}
	

}