package com.tahayavuz.bankrestapp.controllers;

import com.tahayavuz.bankrestapp.models.User;
import com.tahayavuz.bankrestapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController extends CommonMethods {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping({"/","/login"})
    private ResponseEntity<HttpStatus> login(@RequestBody User user) throws Exception {
        Authentication authentication;
        try {
            authentication=authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e){
            throw new Exception("invalid credentials");

        }
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    @PostMapping("register")
    public User addCustomer(@RequestBody User user) {
        return userService.save(user);
    }


}
