package com.tahayavuz.bankrestapp.repositories;

import com.tahayavuz.bankrestapp.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByName(String Name);

    User findByEmail(String Email);
}
