package com.titus.grocerystore.utils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import com.titus.grocerystore.objects.Product;
import com.titus.grocerystore.objects.Products;

public class GeneralUtil {
	
	private static DecimalFormat priceFormat= new DecimalFormat("#,##0.00");
	
	/**
	 * Returns an aggregated set of products by project code.
	 * @param products List of products to be aggregated by code.	 * 
	 * @return A map of aggregated products. The key in the map is the code of 
	 * similar products and the value is the list of products with the same code.
	 */
	public static Map<String,Products> aggregateSimilarProducts(Products products){
		Map<String,Products> aggregatedProducts = new HashMap<String,Products>();
		
		for(Product product : products){
			if(aggregatedProducts.containsKey(product.getCode())){
				aggregatedProducts.get(product.getCode()).add(product);
			}else{
				Products prods = new Products();
				prods.add(product);
				aggregatedProducts.put(product.getCode(), prods);
			}
		}
		
		return aggregatedProducts;
	}

	/**
	 * Formats the double number into a two-decimal, comma-separated number.
	 * @param number
	 * @return A formatted number.
	 */
	public static String formatPrice(double number){
		return priceFormat.format(number);
	}
	
}


