package com.inventory.system.InventorySystem.exceptions.alreadyexists;

public class ItemAlreadyExists extends RuntimeException {
	
	public int id;

	public ItemAlreadyExists(int id) {
		super();
		this.id = id;
	}
	

}
