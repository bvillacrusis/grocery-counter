package com.titus.grocerystore.enums;

public enum PromotionType {
	BUY_ONE_GET_ONE_FREE("Sale: B1-G1"),
	BUY_TWO_GET_ONE_FREE("Sale: B2-G1");
	
	private String promotion;
	PromotionType(String promotion){
		this.promotion = promotion;
	}
	
	public String getPromoDescription(){
		return promotion;
	}
	
}
