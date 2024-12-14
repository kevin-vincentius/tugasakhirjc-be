package com.bcaf.tugasakhir.controller;

import com.bcaf.tugasakhir.dto.ReqChangePasswordDTO;
import com.bcaf.tugasakhir.dto.ReqLoginDTO;
import com.bcaf.tugasakhir.dto.ReqRegisterDTO;
import com.bcaf.tugasakhir.model.MstUser;
import com.bcaf.tugasakhir.repo.UserRepo;
import com.bcaf.tugasakhir.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsers(HttpServletRequest request){
        return userService.getAllUsers(request);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserDetail(@PathVariable(value = "userId") Long userId, HttpServletRequest request){
        return userService.getUserDetail(userId, request);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody ReqLoginDTO loginDTO){
        return userService.login(loginDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody ReqRegisterDTO registerDTO, HttpServletRequest request){
        return userService.register(registerDTO, request);
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<Object> changePassword(@PathVariable(value = "id") Long userId, @RequestBody ReqChangePasswordDTO reqChangePasswordDTO, HttpServletRequest request){
        return userService.changePassword(userId, reqChangePasswordDTO, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long userId, HttpServletRequest request){
        return userService.delete(userId, request);
    }



}
