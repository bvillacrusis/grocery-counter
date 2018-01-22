package com.titus.grocerystore.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.titus.grocerystore.enums.SellingType;
import com.titus.grocerystore.objects.Product;
import com.titus.grocerystore.objects.Products;

public class GeneralUtil {
	
	private static DecimalFormat priceFormat= new DecimalFormat("#,##0.00");
	private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyy HH:mm:ss");
	
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
	
	public static void printReceipt(Products products){
		System.out.println("\t#############################################################");
		System.out.println("\t\t\t       OFFICIAL RECEIPT");
		System.out.println("\t\t\t       SM Megamall Supermarket");
		System.out.println("\t\t\t        OPD by: SM Corporation");
		System.out.println("\t\t\t  G/F, Lot 5, Block 13-A, San Antonio");
		System.out.println("\t\t\t      Orticas, Metro Manila, Phil.");	
		System.out.println("\t\t\t         TIN: 005-215-077-341");
		System.out.println("");
		LocalDateTime now = LocalDateTime.now();
		System.out.println("\t"+dateFormat.format(now));
		System.out.println("\t--------------------------------------------------------------");
		System.out.println("\t\t\t Item \t     Price \tWeight \t        Amount");
		for(Product product: products){
			
			double amount = product.getPrice();				
			if(product.getSellingType() == SellingType.BY_BULK){
				amount = product.getPrice()*product.getWeight();
			}
		
			if(product.getSellingType() == SellingType.ON_SALE){				
				System.out.println("\t"+product.toString());
			}else if(product.getSellingType() == SellingType.BY_BULK){
				String weight =  String.format("%10s",GeneralUtil.formatPrice(product.getWeight()));
				System.out.println("\t"+product.toString()+weight+"\t\t"+String.format("%1s",GeneralUtil.formatPrice(amount)));
			}else{
				System.out.println("\t"+product.toString()+"\t\t"+String.format("%13s",GeneralUtil.formatPrice(amount)));
			}			
		}
		System.out.println("\t--------------------------------------------------------------");
		System.out.println("\tTotal Amt:  \t\t"+String.format("%37s",GeneralUtil.formatPrice(products.getTotalPrice())));
		System.out.println("\tNumber of Items:  \t"+String.format("%37s",products.size()));
		
		System.out.println("");
		System.out.println("");
		System.out.println("\t\tTHIS SERVES AS YOUR OFFICIAL RECEIPT ");
		System.out.println("\t#############################################################");
		
		
	}
	public static void printReceiptAggregated(Products products){
		System.out.println("\t***************************************************************");
		System.out.println("\t\t\t       OFFICIAL RECEIPT");
		System.out.println("\t\t\t       SM Megamall Supermarket");
		System.out.println("\t\t\t        OPD by: SM Corporation");
		System.out.println("\t\t\t  G/F, Lot 5, Block 13-A, San Antonio");
		System.out.println("\t\t\t      Orticas, Metro Manila, Phil.");	
		System.out.println("\t\t\t         TIN: 005-215-077-341");
		System.out.println("");
		LocalDateTime now = LocalDateTime.now();
		System.out.println("\t"+dateFormat.format(now));
		System.out.println("\t--------------------------------------------------------------");
		System.out.println("\t\t\t Item \t     Price \tWgt/Qty \tAmount");
		Map<String, Products> aggregatedProducts = aggregateSimilarProducts(products);
		for (Map.Entry<String, Products> entry : aggregatedProducts.entrySet()){
			Products similarProducts = aggregatedProducts.get(entry.getKey());				
			int productSize = similarProducts.size();
			if(productSize > 0){
				Product productsRepresentative = similarProducts.get(0);
				double amount = PricingUtil.computeTotalPrice(similarProducts);	
				if(productsRepresentative.getSellingType() != SellingType.BY_BULK){
					System.out.println("\t"+productsRepresentative.toString()+String.format("%12s",productSize)+"\t\t"+String.format("%1s",GeneralUtil.formatPrice(amount)));
				}else{
					System.out.println("\t"+productsRepresentative.toString()+String.format("%12s",getTotalWeight(similarProducts)+"/"+productSize)+"\t\t"+String.format("%1s",GeneralUtil.formatPrice(amount)));
				}
				
			}
		}		
		
		System.out.println("\t--------------------------------------------------------------");
		System.out.println("\tTotal Amt:  \t\t"+String.format("%37s",GeneralUtil.formatPrice(products.getTotalPrice())));
		System.out.println("\tNumber of Items:  \t"+String.format("%37s",products.size()));
		
		System.out.println("");
		System.out.println("");
		System.out.println("\t\tTHIS SERVES AS YOUR OFFICIAL RECEIPT ");
		System.out.println("\t***************************************************************");		
	}
	
	/**
	 * Computes the total weight of a set of products.
	 * @param products Products to be weighed.
	 * @return Total weight.
	 */
	public static double getTotalWeight(Products products){
		double total = 0.0;
		for(Product prod : products){
			total += prod.getWeight();
		}
		return total;
	}
}
