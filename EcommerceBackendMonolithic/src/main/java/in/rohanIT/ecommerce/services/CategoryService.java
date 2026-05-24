package in.rohanIT.ecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.rohanIT.ecommerce.entity.ProductCategory;
import in.rohanIT.ecommerce.repo.ProductCategoryRepo;

@Service
public class CategoryService {

	@Autowired
	ProductCategoryRepo categoryRepo;
	
	public List<ProductCategory> getCategories() {
		// TODO Auto-generated method stub
		return categoryRepo.findAll();
	}

}
