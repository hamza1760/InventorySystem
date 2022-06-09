package com.inventory.system.InventorySystem.exceptions.alreadyexists;

public class BrandAlreadyExists extends RuntimeException {
	
	public int id;

	public BrandAlreadyExists(int id) {
		super();
		this.id = id;
	}
	

}
