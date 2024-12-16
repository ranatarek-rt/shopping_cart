package com.dragon.shoppingCart.controller;
import com.dragon.shoppingCart.model.RoleDto;
import com.dragon.shoppingCart.request.RoleRequest;
import com.dragon.shoppingCart.response.ApiResponse;
import com.dragon.shoppingCart.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }
    @PostMapping
    public ResponseEntity<ApiResponse> createRole(@RequestBody RoleRequest roleRequest){
        RoleDto roleDto = roleService.addNewRole(roleRequest.getName());
        return ResponseEntity.ok(new ApiResponse("the new role is added successfully",roleDto));
    }


}
