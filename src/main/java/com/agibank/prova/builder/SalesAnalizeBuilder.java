package com.agibank.prova.builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.agibank.prova.model.ItemSale;
import com.agibank.prova.model.Report;
import com.agibank.prova.model.Sale;

public class SalesAnalizeBuilder {

	private Report report;
	private List<Sale> sales;
	
	public SalesAnalizeBuilder() {
		report = new Report();
		sales = new ArrayList<Sale>();
	}
	
	public SalesAnalizeBuilder addCountClient(long count) {
		if (report == null) {
			report = new Report();
		}
		report.setCountClient(report.getCountClient() + count);
		
		return this;
	}
	
	public SalesAnalizeBuilder addCountSalesman(long count) {
		if (report == null) {
			report = new Report();
		}
		report.setCountSalesman(report.getCountSalesman() + count);
		
		return this;
	}
	
	public SalesAnalizeBuilder addSale(String line) {
		if (sales == null) {
			sales = new ArrayList<Sale>();
		}
		
		String[] saleData = line
				.replace("[", "")
				.replace("]", "")
				.split("ç");
		
		Sale sale = new Sale();
		sale.setId(Long.parseLong(saleData[1]));
		sale.setSalesman(saleData[3]);

		List<ItemSale> itemsSale = new ArrayList<>();
		
		for (String data : saleData[2].split(",")) {
			String[] itemData = data.split("-");
			
			ItemSale itemSale = new ItemSale();
			itemSale.setIdItem(Long.parseLong(itemData[0]));
			itemSale.setQtd(Long.parseLong(itemData[1]));
			itemSale.setPrice(new BigDecimal(itemData[2]));
			
			itemsSale.add(itemSale);
		}
		
		sale.setItems(itemsSale);
		sales.add(sale);
		
		return this;
	}
	
	public Report build() {
		if (report == null) {
			report = new Report();
		}
		
		Sale saleBiggerValue = new Sale();
		Sale saleSmallerValue = new Sale();
		
		BigDecimal biggerValue = new BigDecimal("0");
		BigDecimal smallerValue = new BigDecimal("0");
		
		for (Sale sale : sales) {
			BigDecimal valueTotal = new BigDecimal("0");
					
			for (ItemSale itemSale : sale.getItems()) {
				valueTotal = valueTotal.add(itemSale.getPrice().multiply(new BigDecimal(itemSale.getQtd())));
			}
			
			if (biggerValue.compareTo(valueTotal) < 0) {
				biggerValue = valueTotal;
				saleBiggerValue = sale;
			}
			
			if (smallerValue.compareTo(new BigDecimal(0)) == 0 || smallerValue.compareTo(valueTotal) >= 0) {
				smallerValue = valueTotal;
				saleSmallerValue = sale;
			}
		}
		
		report.setIdBiggerSalle(saleBiggerValue.getId() != null ? saleBiggerValue.getId() : 0);
		report.setSellerNameSmallerAmount(saleSmallerValue.getSalesman() != null ? saleSmallerValue.getSalesman() : "");
		
		return report;
	}

}
