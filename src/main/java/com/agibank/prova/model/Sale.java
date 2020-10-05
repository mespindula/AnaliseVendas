package com.agibank.prova.model;

import java.util.List;

public class Sale {

	private Long id;

	private List<ItemSale> items;
	
	private String salesman;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ItemSale> getItems() {
		return items;
	}
	
	public void setItems(List<ItemSale> items) {
		this.items = items;
	}
	
	public String getSalesman() {
		return salesman;
	}
	
	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	
}
