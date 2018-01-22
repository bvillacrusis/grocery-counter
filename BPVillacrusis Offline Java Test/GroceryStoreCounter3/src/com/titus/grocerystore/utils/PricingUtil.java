package com.titus.grocerystore.utils;

import java.util.Map;

import com.titus.grocerystore.enums.PromotionType;
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
			+ computeTotalPrice(products.getItemsBySellingType(SellingType.BY_BULK),SellingType.BY_BULK)
			+ computeTotalPrice(products.getItemsBySellingType(SellingType.ON_SALE),SellingType.ON_SALE);
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
		}else if(sellingType == SellingType.ON_SALE){
			return computeTotalPriceSoldOnSale(products);
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
	
	/**
	 * Returns the total price of the products sold on sale.
	 * @param products List of products sold on sale.
	 * @return Total price of the products.
	 */
	private static double computeTotalPriceSoldOnSale(Products products){
		double totalPrice = 0.0;
		
		// segregate products by code
		Map<String,Products> aggregatedProducts = GeneralUtil.aggregateSimilarProducts(products);
		if(aggregatedProducts != null && aggregatedProducts.size() > 0){
			
			// iterate on each set of similar products 
			for (Map.Entry<String, Products> entry : aggregatedProducts.entrySet()){
				Products similarProducts = aggregatedProducts.get(entry.getKey());				
				int productSize = similarProducts.size();
				if(productSize > 0){
					Product productsRepresenative = similarProducts.get(0);
					if(productsRepresenative.getPromotionType() == PromotionType.BUY_ONE_GET_ONE_FREE){
						
						// get and add total price of products under BUY_ONE_GET_ONE_FREE promotion
						totalPrice += computeBuyOneGetOne(productSize,productsRepresenative.getPrice());
						
					}else if(productsRepresenative.getPromotionType() == PromotionType.BUY_TWO_GET_ONE_FREE){
						
						// get and add total price of products under BUY_TWO_GET_ONE_FREE promotion
						totalPrice += computeBuyTwoGetOne(productSize,productsRepresenative.getPrice());						
					}					
				}				
			}			
		}
		
		return totalPrice;
	}
	/**
	 * Returns total price of a set of products under
	 * buy-one-take-one promotion.
	 * @param size Total items.
	 * @param price Price of the products.
	 * @return Total price of the products.
	 */
	public static double computeBuyOneGetOne(int size, double price){		
		if(size%2 == 0){
			return (size/2.0)*price;
		} else{
			return (((size-1)/2.0)*price)
					+price ;
		}		
	}
	
	/**
	 * Returns total price of a set of products under
	 * buy-two-take-one promotion.
	 * @param size Total items.
	 * @param price Price of the products.
	 * @return Total price of the products.
	 */
	
	public static double computeBuyTwoGetOne(int size, double price){
		int quotient = (int) size/3;
		if(size%3 == 0){
			return (quotient*2)*price;
		} else{						
			return (quotient*2*price)
					+price*(size%3) ;
		}			
	}

}