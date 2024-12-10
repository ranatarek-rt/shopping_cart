package com.dragon.shoppingCart.service.category;

import com.dragon.shoppingCart.entity.Category;
import com.dragon.shoppingCart.entity.Product;
import com.dragon.shoppingCart.exception.CategoryNotFoundException;
import com.dragon.shoppingCart.exception.DuplicateCategoryException;
import com.dragon.shoppingCart.model.CategoryDto;
import com.dragon.shoppingCart.repository.CategoryRepo;
import com.dragon.shoppingCart.repository.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    ModelMapper modelMapper;
    //inject category repo
    CategoryRepo categoryRepo;
    ProductRepo productRepo;
    CategoryServiceImpl(CategoryRepo categoryRepo, ModelMapper modelMapper,ProductRepo productRepo){
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
        this.modelMapper = modelMapper;
    }


    @Override
    public Category findById(Long id) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);
        return optionalCategory.orElseThrow(()-> new CategoryNotFoundException("category not found"));
    }

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Category addCategory(CategoryDto categoryDto) {
        Optional<Category> existingCat = categoryRepo.findCategoryByName(categoryDto.getName());
        if(existingCat.isPresent()){
            throw new DuplicateCategoryException("This category already exists, please choose another name.");
        }
        Category cat = modelMapper.map(categoryDto,Category.class);
        return categoryRepo.save(cat);
    }

    @Override
    public Category findCategoryByName(String catName) {
        return categoryRepo.findCategoryByName(catName)
                .orElseThrow(()->new CategoryNotFoundException("There is no category found with that name"));
    }

    @Override
    public Category updateExisitingCategory(CategoryDto categoryDto, Long id) {
        //find the cat by id to be updated if not found throw exception
        Category existingCat = categoryRepo.findById(id).orElseThrow(()->
                new CategoryNotFoundException("the cat with id "+ id +" does not exist in the DB"));

        existingCat.setName(categoryDto.getName());

        return categoryRepo.save(existingCat);
    }

    @Override
    public void deleteCategory(Long id) {
        //find the cat by id to be updated if not found throw exception
        Category category = categoryRepo.findById(id).orElseThrow(()->
                new CategoryNotFoundException("the cat with id "+ id +" does not exist in the DB"));
        /*if the category found in the database this will
         delete the category and set the catId to null inside the product table*/

         List<Product> productList  = productRepo.findByCategoryName(category.getName());
         productRepo.deleteAll(productList);
         categoryRepo.deleteById(id);

    }
}
