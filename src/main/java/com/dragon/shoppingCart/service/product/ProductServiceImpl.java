package com.dragon.shoppingCart.service.product;
import com.dragon.shoppingCart.entity.Category;
import com.dragon.shoppingCart.entity.Product;
import com.dragon.shoppingCart.exception.DuplicatedProductException;
import com.dragon.shoppingCart.exception.ProductNotFoundException;
import com.dragon.shoppingCart.model.ProductDto;
import com.dragon.shoppingCart.repository.CategoryRepo;
import com.dragon.shoppingCart.repository.ImageRepo;
import com.dragon.shoppingCart.repository.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


//we can use this lombok annotation for constructor injection
// instead of doing it , and you should set the value to be injected as final
//@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{

    //inject the product repo
    ProductRepo productRepo;
    ModelMapper modelMapper;
    CategoryRepo categoryRepo;
    ImageRepo imageRepo;
    @Autowired
    ProductServiceImpl(ProductRepo productRepo,ModelMapper modelMapper,
                       CategoryRepo categoryRepo,ImageRepo imageRepo){
        this.productRepo = productRepo;
        this.modelMapper = modelMapper;
        this.categoryRepo = categoryRepo;
        this.imageRepo = imageRepo;


    }
    @Override
    public Product addProduct(ProductDto productDto) {
        Optional<Product> existingProduct = productRepo.findByNameAndBrand(productDto.getName(),productDto.getBrand());
        if(existingProduct.isPresent()){
            throw new DuplicatedProductException("there is a product exists with same name and brand");
        }
        // Find the category by name
        Optional<Category> optionalCat = categoryRepo.findCategoryByName(productDto.getCategory().getName());
        Product product = modelMapper.map(productDto, Product.class);
        // Check if the category exists
        if (optionalCat.isPresent()) {
            // If category exists set it on the product
            product.setCategory(optionalCat.get());
        } else {
            // If the category doesn't exist save the new category
            Category newCategory = new Category(productDto.getCategory().getName());
            categoryRepo.save(newCategory);
            product.setCategory(newCategory);
        }
        //after finishing save the product in the db and return
        productRepo.save(product);

        return product;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long id) {
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));

        // Update basic product fields
        existingProduct.setName(productDto.getName());
        existingProduct.setBrand(productDto.getBrand());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setInventoryQuantity(productDto.getInventoryQuantity());

        // Handle category
        if (productDto.getCategory() != null) {
            String categoryName = productDto.getCategory().getName();
            Category category = categoryRepo.findCategoryByName(categoryName)
                    .orElseGet(() -> categoryRepo.save(new Category(categoryName)));  // Create if not exists
            existingProduct.setCategory(category); // Set the category to the product
        }

        Product product = productRepo.save(existingProduct);
        // Save the updated product
        return modelMapper.map(product,ProductDto.class);
    }


    //*********************** search, get all, getById,DeleteById ********************
    //find all products
    @Override
    public List<ProductDto> findAll() {
        return productRepo.findAll().stream().map(product -> modelMapper.map(product,ProductDto.class)).toList();
    }

    @Override
    public List<ProductDto> findAllProductsByCategory(String category) {
        return productRepo.findByCategoryName(category).stream().map(product -> modelMapper.map(product,ProductDto.class)).toList();
    }

    @Override
    public List<ProductDto> findAllProductsByBrand(String brand) {
        return productRepo.findAllByBrand(brand).stream().map(product -> modelMapper.map(product,ProductDto.class)).toList();
    }

    @Override
    public List<ProductDto> findProductsByCategoryAndBrand(String category, String brand) {
        return productRepo.findAllByCategoryNameAndBrand(category,brand).stream().map(product -> modelMapper.map(product,ProductDto.class)).toList();
    }

    @Override
    public List<ProductDto> findProductsByName(String productName) {
        return productRepo.findAllByName(productName).stream().map(product -> modelMapper.map(product,ProductDto.class)).toList();
    }

    @Override
    public List<ProductDto> findProductsByBrandAndName(String brand, String productName) {
        return productRepo.findAllByBrandAndName(brand,productName).stream().map(product -> modelMapper.map(product,ProductDto.class)).toList();
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String productName) {
        return  productRepo.countAllByBrandAndName(brand,productName);
    }

    @Override
    public ProductDto findById(Long id) {
        Optional<Product> product = productRepo.findById(id);
        if(product.isPresent()){
            return modelMapper.map(product.get(),ProductDto.class);
        }
        throw new ProductNotFoundException("product not found");
    }

    @Override
    public void deleteProductById(Long id) {
        //we can use this commented section if further steps needed in the "product found" or "not found" cases in the future
//        Optional<Product> product = productRepo.findById(id);
//        product.ifPresentOrElse(
//                value -> productRepo.delete(value),
//                () -> { throw new ProductNotFoundException("Product not found"); }
//        );

        Product product = productRepo
                .findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product not found"));
        productRepo.delete(product);
    }


}
