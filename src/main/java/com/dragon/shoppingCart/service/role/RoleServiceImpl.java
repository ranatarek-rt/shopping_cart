package com.dragon.shoppingCart.service.role;

import com.dragon.shoppingCart.data.RoleRepo;
import com.dragon.shoppingCart.entity.Role;
import com.dragon.shoppingCart.model.RoleDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;


@Service
public class RoleServiceImpl implements RoleService{

    RoleRepo roleRepo;
    ModelMapper modelMapper;

    @Autowired
    RoleServiceImpl(RoleRepo roleRepo, ModelMapper modelMapper){
        this.roleRepo = roleRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public RoleDto addNewRole(String roleName) {
        if (roleRepo.findByName(roleName).isPresent()) {
            throw new IllegalArgumentException("Role already exists");
        }
        Role role = roleRepo.save(new Role(roleName));
        return modelMapper.map(role,RoleDto.class);
    }
}
