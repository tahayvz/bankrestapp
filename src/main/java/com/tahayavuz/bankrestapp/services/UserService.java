package com.tahayavuz.bankrestapp.services;


import com.tahayavuz.bankrestapp.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User save(User user);

//    User findByUsername(String username);
}