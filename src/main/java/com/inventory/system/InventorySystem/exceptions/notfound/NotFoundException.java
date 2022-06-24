package com.inventory.system.InventorySystem.exceptions.notfound;

public class NotFoundException extends RuntimeException {

	public String message;
	public int id;

	public NotFoundException(String message, int id) {
		this.message = message;
		this.id = id;
	}
}
