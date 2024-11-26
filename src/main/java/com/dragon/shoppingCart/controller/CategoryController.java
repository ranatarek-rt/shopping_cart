package com.dragon.shoppingCart.controller;

import com.dragon.shoppingCart.entity.Category;
import com.dragon.shoppingCart.model.CategoryDto;
import com.dragon.shoppingCart.response.ApiResponse;
import com.dragon.shoppingCart.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    CategoryService categoryService;
    CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAll(){
        List<Category> categoryList = categoryService.findAll();
        return ResponseEntity.ok(new ApiResponse("All categories are fetched successfully",categoryList));
    }
    @GetMapping("/{catId}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long catId){
        Category category = categoryService.findById(catId);
        return ResponseEntity.ok(new ApiResponse("The category is fetched successfully",category));
    }
    @GetMapping("/byName/{name}")
    public ResponseEntity<ApiResponse> findByName(@PathVariable String name){
        Category category = categoryService.findCategoryByName(name);
        return ResponseEntity.ok(new ApiResponse("The category is fetched successfully",category));
    }
    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestBody CategoryDto categoryDto){
        Category category =  categoryService.addCategory(categoryDto);
        return ResponseEntity.ok(new ApiResponse("The category is created successfully",category));
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable Long catId){
        categoryService.deleteCategory(catId);

    }
    @PutMapping("/{catId}")
    public ResponseEntity<ApiResponse> update(@RequestBody CategoryDto categoryDto, @PathVariable Long catId){
        Category category =  categoryService.updateExisitingCategory(categoryDto,catId);
        return ResponseEntity.ok(new ApiResponse("The category is updated successfully",category));
    }
}
