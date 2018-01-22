package com.titus.grocerystore.products;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.titus.grocerystore.enums.SellingType;
import com.titus.grocerystore.objects.Product;
import com.titus.grocerystore.objects.Products;
import com.titus.grocerystore.utils.GeneralUtil;

public class SoldByBulkPricingTest {
	private static Products sampleProductsTobeScanned = new Products();
	private double preComputedTotalPrice = 0.0;
	
	
	@BeforeClass
	public static void displayTestDescription(){
		// displays a 
		System.out.println("#######################################################################################");
		System.out.println("\tThe following scenario shows a simple grocery checkout counter" +
				"\n\twherein a product SOLD BY BULK will be scanned and the total "
				+ "\n\tprice of all the scanned items will be displayed.");
		System.out.println("#######################################################################################");
	}
	
	@Before
	public void generateSampleListOfProducts(){
		// generates 10 different products available in the store
		// this will serve as a non-persistent DB of available items in the store
		int numberOfDifferentPoductsAvailable = 10;		
		String[] unitOfMeasures ={"kg","lb","g","mg"};
		Products products = new Products();
		for(int i=1; i<=numberOfDifferentPoductsAvailable; i++){
			Product product = new Product();
			double randomPrice = Math.round((Math.random()*10000))/100.0;
			product.setCode("code"+i);			
			product.setPrice(randomPrice);			
			product.setSellingType(SellingType.BY_BULK);		
			int randomNum2 = (int) (Math.round((Math.random()*30))/10.0); // random number from 0 to 3
			product.setDescription("Prod Descri "+i+" @"+product.getPrice()+"/"+unitOfMeasures[randomNum2]);
			products.add(product);				
		}
		
		// generate 20 sample set of products to be scanned
		// product to be included will be randomly selected from the
		// available set of products
		
		for(int i=1; i<=20;i++){
			int randomPosition = (int) (Math.random()*(numberOfDifferentPoductsAvailable-1));
			Product product = products.get(randomPosition);
			Product soldProduct = new Product();			
			soldProduct.setCode(product.getCode());			
			soldProduct.setPrice(product.getPrice());
			soldProduct.setSellingType(product.getSellingType());				
			int randomNum1 = (int) (Math.round((Math.random()*70))/10.0); // random number from 0 to 5
			soldProduct.setWeight(0.25*(randomNum1)+0.25);
			soldProduct.setDescription(product.getDescription());
			sampleProductsTobeScanned.add(soldProduct);
			preComputedTotalPrice += soldProduct.getPrice()*soldProduct.getWeight(); // to be used for testing the computed price
		}
		
	}

	@Test
	public void scanItems() {
		System.out.println("Scanning items in the list one by one...");
		System.out.println(String.format("%90s","Item Weight \tItem Price ($) \tTotal Price ($)"));
		
		// adding item on the list of grocery items, one-by-one
		// dispalys current total price of the grocery items
		Products groceryItems = new Products();
		for(Product product: sampleProductsTobeScanned){
			System.out.print("Adding Item: "+product.toString());
			groceryItems.add(product);
			System.out.println("\t"+String.format("%15s",GeneralUtil.formatPrice(groceryItems.getTotalPrice())));
			
		}		
		System.out.println("Pre-computed Total Price:  $ "+ GeneralUtil.formatPrice(preComputedTotalPrice));
		System.out.println("Scanning of items completed.");
		System.out.println("#######################################################################################");
		
		// tests the total price if same with pre-computed total price
		Assert.assertEquals(preComputedTotalPrice, groceryItems.getTotalPrice(),4);	
	}
	
	@AfterClass
	public static void displayEndMessage(){
		sampleProductsTobeScanned.clear();
	}

}
