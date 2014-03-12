package edu.sjsu.cmpe282.domain;

public class Product {
	private String name;
	private String price;
	private String URL;
	private String category;
	private String productid;
	public Product(String name, String price, String uRL, String category, String productid) {
		this.name = name;
		this.price = price;
		this.URL = uRL;
		this.category = category;
		this.productid = productid;
	}
	public String getName() {
		return name;
	}
	public String getPrice() {
		return price;
	}
	public String getURL() {
		return URL;
	}
	public String getCategory() {
		return category;
	}
	public String getProductid() {
		return productid;
	}
	
	

}
