package com.dragon.shoppingCart.service.cart;

import com.dragon.shoppingCart.entity.Cart;
import com.dragon.shoppingCart.entity.CartItem;
import com.dragon.shoppingCart.entity.Product;
import com.dragon.shoppingCart.exception.CartNotFoundException;
import com.dragon.shoppingCart.exception.ProductNotFoundException;
import com.dragon.shoppingCart.model.AddItemToCartDto;
import com.dragon.shoppingCart.model.CartItemDto;
import com.dragon.shoppingCart.model.ProductDto;
import com.dragon.shoppingCart.model.RemoveItemFromCartDto;
import com.dragon.shoppingCart.repository.CartItemRepo;
import com.dragon.shoppingCart.repository.CartRepo;
import com.dragon.shoppingCart.repository.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService{
    CartItemRepo cartItemRepo;
    ProductRepo productRepo;
    CartRepo cartRepo;
    ModelMapper modelMapper;
    //inject cart item repo
    @Autowired
    CartItemServiceImpl(CartItemRepo cartItemRepo,ProductRepo productRepo,CartRepo cartRepo,ModelMapper modelMapper){
        this.cartItemRepo = cartItemRepo;
        this.productRepo = productRepo;
        this.cartRepo = cartRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CartItemDto findCartItemById(Long cartItemId) {
        CartItem cartItem  = cartItemRepo.findById(cartItemId).orElseThrow(()->new CartNotFoundException("no item found"));
        return modelMapper.map(cartItem,CartItemDto.class);
    }

    @Override
    public void addItemToCart(Long cartId, AddItemToCartDto itemToCartDto) {
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + cartId));

        Product product = productRepo.findById(itemToCartDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + itemToCartDto.getProductId()));

        if (itemToCartDto.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        // check if the item already exists inside the cart, if the item does not exist return a new cartItem
        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(itemToCartDto.getProductId()))
                .findFirst()
                .orElse(new CartItem());

         /*if the cartItem is not found and a new cartItem is created the id will be null
           so we need to update the newly created item with the correct information
        */
        if (cartItem.getId() == null) {
            //we need to check that the quantity required by the user does not exceed the inventory quantity
            if (itemToCartDto.getQuantity() > product.getInventoryQuantity()) {
                throw new IllegalArgumentException("Quantity exceeds available inventory.");
            }

            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(itemToCartDto.getQuantity());
            cartItem.setUnitPrice(product.getPrice());
        } else {

            if (product.getInventoryQuantity() < cartItem.getQuantity() + itemToCartDto.getQuantity()) {
                throw new IllegalArgumentException("Quantity exceeds available inventory.");
            }
            cartItem.setQuantity(cartItem.getQuantity() + itemToCartDto.getQuantity());
        }

        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cart.updateTotalAmount();
        cartItemRepo.save(cartItem);
        cart.setId(cartId);
        cartRepo.save(cart);
    }


    //this method for deleting cartItem regardless of its quantity
    @Override
    public void removeItemFromCart(Long cartId,Long productId) {
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + cartId));
        // check if the item already exists inside the cart, if the item does not exist return a new cartItem
        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("Product with id "+productId+ "does not exist inside the cart"));

        cart.removeItem(cartItem);
        // Delete the CartItem explicitly to avoid orphan issues
        cartItemRepo.delete(cartItem);
        cart.updateTotalAmount();
        cartRepo.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, AddItemToCartDto itemToCartDto) {
        if (itemToCartDto.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + cartId));

        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(itemToCartDto.getProductId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + itemToCartDto.getProductId() + " does not exist in the cart"));
        Product product = productRepo.findById(itemToCartDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + itemToCartDto.getProductId()));

        if (itemToCartDto.getQuantity() > product.getInventoryQuantity()) {
            throw new IllegalArgumentException("Quantity exceeds available inventory for product ID: " + itemToCartDto.getProductId());
        }

        // Update the quantity of the cart item and its total price
        cartItem.setQuantity(itemToCartDto.getQuantity());
        cartItem.setTotalPrice();
        // Save the updated cart item and cart
        cartItemRepo.save(cartItem);
        cart.updateTotalAmount();
        cartRepo.save(cart);
    }


    @Override
    public void reduceItemQuantity(Long cartId, RemoveItemFromCartDto removeItemFromCartDto) {
        if(removeItemFromCartDto.getQuantityToReduce()<=0){
            throw new IllegalArgumentException("Quantity to reduce must be greater than 0");
        }
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + cartId));
        // check if the item already exists inside the cart, if the item does not exist return a new cartItem
        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(removeItemFromCartDto.getProductId()))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("Product with id "+removeItemFromCartDto.getProductId()+ "does not exist inside the cart"));


        if(cartItem.getQuantity() <= removeItemFromCartDto.getQuantityToReduce()){
            cart.removeItem(cartItem);
            // Delete the CartItem explicitly to avoid orphan issues
            cartItemRepo.delete(cartItem);
        }else{
            cartItem.setQuantity(cartItem.getQuantity()- removeItemFromCartDto.getQuantityToReduce());
            cartItem.setTotalPrice();
            cartItemRepo.save(cartItem);
        }
        cart.updateTotalAmount();
        cartRepo.save(cart);

    }
}
