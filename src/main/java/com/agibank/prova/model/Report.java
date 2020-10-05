package com.agibank.prova.model;

public class Report {

	private long countClient;
	
	private long countSalesman;
	
	private long idBiggerSalle;
	
	private String sellerNameSmallerAmount;

	public Long getCountClient() {
		return countClient;
	}

	public void setCountClient(long countClient) {
		this.countClient = countClient;
	}

	public long getCountSalesman() {
		return countSalesman;
	}

	public void setCountSalesman(long countSalesman) {
		this.countSalesman = countSalesman;
	}

	public long getIdBiggerSalle() {
		return idBiggerSalle;
	}

	public void setIdBiggerSalle(long idBiggerSalle) {
		this.idBiggerSalle = idBiggerSalle;
	}

	public String getSellerNameSmallerAmount() {
		return sellerNameSmallerAmount;
	}

	public void setSellerNameSmallerAmount(String sellerNameSmallerAmount) {
		this.sellerNameSmallerAmount = sellerNameSmallerAmount;
	}

	@Override
	public String toString() {
		return "Quantidade de clientes no arquivo de entrada: " + countClient + 
				"\n" + "Quantidade de vendedores no arquivo de entrada: " + countSalesman +
				"\n" + "ID da venda mais cara: " + idBiggerSalle + 
				"\n" + "O pior vendedor: " + sellerNameSmallerAmount;
	}
	
}
