package com.inventory.system.InventorySystem.exceptions.alreadyexists;

public class ItemTypeAlreadyExists extends RuntimeException {
	
	public int id;

	public ItemTypeAlreadyExists(int id) {
		super();
		this.id = id;
	}
	

}
