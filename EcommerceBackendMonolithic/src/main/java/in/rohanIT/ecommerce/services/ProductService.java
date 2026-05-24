package in.rohanIT.ecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.rohanIT.ecommerce.entity.Product;
import in.rohanIT.ecommerce.repo.ProductRepo;

@Service
public class ProductService {

	@Autowired
	ProductRepo productRepo;
	
	public List<Product> getAllProducts(){
		return productRepo.findAll();
	}
	
	 public List<Product> getProductsByCategory(int categoryId) {
	        return productRepo.findByCategory_CategoryId(categoryId);
	    }
}

