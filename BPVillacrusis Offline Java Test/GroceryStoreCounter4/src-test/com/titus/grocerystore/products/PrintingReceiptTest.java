package com.titus.grocerystore.products;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.titus.grocerystore.enums.PromotionType;
import com.titus.grocerystore.enums.SellingType;
import com.titus.grocerystore.objects.Product;
import com.titus.grocerystore.objects.Products;

public class PrintingReceiptTest {
	private static Products sampleProductsTobeScanned = new Products();	
	
	@BeforeClass
	public static void displayTestDescription(){
		// displays a 
		System.out.println("#######################################################################################");
		System.out.println("\tThe following scenario shows a simple grocery checkout counter" +
				"\n\twherein a set of products will be scanned and the receipt"
				+ "\n\tfor all the items will be displayed (short and long version).");
		System.out.println("#######################################################################################\n\n");
	}
	
	@Before
	public void generateSampleListOfProducts(){
		// generates 10 different products available in the store
		// this will serve as a non-persistent DB of available items in the store
		int numberOfDifferentPoductsAvailable = 10;		
		
		Products products = new Products();
		for(int i=1; i<=numberOfDifferentPoductsAvailable; i++){
			Product product = new Product();
			double randomPrice = Math.round((Math.random()*10000))/100.0;
			product.setCode("code"+i);			
			product.setPrice(randomPrice);
			product.setDescription("Prod Descri "+i);
			double randomNum = Math.random();			
			if(randomNum < 0.33){
				product.setSellingType(SellingType.BY_PIECE);	
			}else if(randomNum >= 0.33 && randomNum < 0.67){
				product.setSellingType(SellingType.BY_BULK);	
				int randomNum2 = (int) (Math.round((Math.random()*30))/10.0); // random number from 0 to 3
				String[] unitOfMeasures ={"kg","lb","g","mg"};
				product.setDescription("Prod Descri "+i+" @"+product.getPrice()+"/"+unitOfMeasures[randomNum2]);
			}else{
				product.setSellingType(SellingType.ON_SALE);	
				if(Math.random() > 0.5){
					product.setPromotionType(PromotionType.BUY_ONE_GET_ONE_FREE);
				}else{
					product.setPromotionType(PromotionType.BUY_TWO_GET_ONE_FREE);
				}	
			}			
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
			soldProduct.setDescription(product.getDescription());
			soldProduct.setPromotionType(product.getPromotionType());
			if(product.getSellingType() == SellingType.BY_BULK){
				int randomNum1 = (int) (Math.round((Math.random()*70))/10.0); // random number from 0 to 5
				soldProduct.setWeight(0.25*(randomNum1)+0.25);
			}				
			sampleProductsTobeScanned.add(soldProduct);
		}
		
	}

	@Test
	public void scanItems() {		
		// adding item on the list of grocery items, one-by-one
		// displays the receipt (long and short version)
		Products groceryItems = new Products();
		for(Product product: sampleProductsTobeScanned){
			groceryItems.add(product);			
		}		
		groceryItems.printReceipt(true);
		System.out.println("\n\n ***Below is a detailed and long version of the receipt.*****\n\n");
		groceryItems.printReceipt(false);	
	}
	
	@AfterClass
	public static void displayEndMessage(){
		sampleProductsTobeScanned.clear();
	}
}
