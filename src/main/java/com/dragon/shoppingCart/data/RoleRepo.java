package com.dragon.shoppingCart.data;

import com.dragon.shoppingCart.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String roleAdmin);
}

