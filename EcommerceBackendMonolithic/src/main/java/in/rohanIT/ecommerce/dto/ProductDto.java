package in.rohanIT.ecommerce.dto;

import in.rohanIT.ecommerce.entity.ProductCategory;
import in.rohanIT.ecommerce.entity.Vendors;

public class ProductDto {

	private int id;

    private String name;
    private String description;
    private double price;
    private int quantity;
    private ProductCategory category;
    private Vendors vendors;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public ProductCategory getCategory() {
		return category;
	}
	public void setCategory(ProductCategory category) {
		this.category = category;
	}
	public Vendors getVendors() {
		return vendors;
	}
	public void setVendors(Vendors vendors) {
		this.vendors = vendors;
	}
    
    
}
