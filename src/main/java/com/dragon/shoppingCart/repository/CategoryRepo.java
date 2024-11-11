package com.dragon.shoppingCart.repository;

import com.dragon.shoppingCart.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {

    Optional<Category> findCategoryByName(String name);
}
