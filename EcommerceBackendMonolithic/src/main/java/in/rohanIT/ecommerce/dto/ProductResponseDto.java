package in.rohanIT.ecommerce.dto;

import in.rohanIT.ecommerce.entity.ProductCategory;
import in.rohanIT.ecommerce.entity.Vendors;

public class ProductResponseDto {

    private int id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private CategoryDto category;     // Simple category
    private Vendors vendors;

    public ProductResponseDto() {}

    // Constructor to convert from Entity
    public ProductResponseDto(in.rohanIT.ecommerce.entity.Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.price = p.getPrice();
        this.quantity = p.getQuantity();
        this.vendors = p.getVendors();
        
        if (p.getCategory() != null) {
            this.category = new CategoryDto(p.getCategory().getCategoryId(), p.getCategory().getCategoryName());
        }
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public CategoryDto getCategory() { return category; }
    public void setCategory(CategoryDto category) { this.category = category; }

    public Vendors getVendors() { return vendors; }
    public void setVendors(Vendors vendors) { this.vendors = vendors; }
}

// Inner simple Category DTO
class CategoryDto {
    private int categoryId;
    private String categoryName;

    public CategoryDto(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public int getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; }
}