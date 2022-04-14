package com.banvien.fc.promotion.dto.rules.Enum;

public enum ActionType {
	GET_TO_CART(0),		//  Get promotion on Shopping Cart.
	GET_TO_ORDER(1);	// Get promotion to apply on Order.
	private final int value;

	ActionType(int value){
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}

