package com.dragon.shoppingCart.controller;
import com.dragon.shoppingCart.request.RoleRequest;
import com.dragon.shoppingCart.request.UpdateUserRequest;
import com.dragon.shoppingCart.request.CreateUserRequest;
import com.dragon.shoppingCart.model.UserDto;
import com.dragon.shoppingCart.response.ApiResponse;
import com.dragon.shoppingCart.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user")
public class UserController {
    UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;

    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
        UserDto user = userService.findUserById(userId);
        return ResponseEntity.ok(new ApiResponse("the user is fetched successfully",user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
         userService.deleteUser(userId);
        return ResponseEntity.ok(new ApiResponse("the user is deleted successfully",null));
    }


    @PostMapping
    public ResponseEntity<ApiResponse> addNewUser(@RequestBody CreateUserRequest createUserRequest){
        UserDto user = userService.createNewUser(createUserRequest);
        return ResponseEntity.ok(new ApiResponse("the user is created successfully",user));
    }


    @PatchMapping
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest updateUserRequest,@RequestParam Long userId){
        UserDto user = userService.updateUser(updateUserRequest,userId);
        return ResponseEntity.ok(new ApiResponse("the user is updated successfully",user));
    }


    @PostMapping("/{userId}/roles")
    public ResponseEntity<ApiResponse> assignRoleToUser(
            @PathVariable Long userId,
            @RequestBody RoleRequest roleRequest) {
        userService.assignRoleToUser(userId, roleRequest.getName());
        return ResponseEntity.ok(new ApiResponse("Role assigned successfully",null));
    }

}
