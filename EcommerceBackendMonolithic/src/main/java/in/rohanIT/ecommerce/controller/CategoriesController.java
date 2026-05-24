package in.rohanIT.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.rohanIT.ecommerce.entity.ProductCategory;
import in.rohanIT.ecommerce.services.CategoryService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class CategoriesController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/categories")
	public List<ProductCategory> getAllCategories(){
		return categoryService.getCategories();
		
	}
}
