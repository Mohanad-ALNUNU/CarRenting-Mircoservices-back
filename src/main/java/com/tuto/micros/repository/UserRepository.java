package com.tuto.micros.repository;

import com.tuto.micros.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    List<User> findByName(String name);
    List<User> findByAge(int age);

}
