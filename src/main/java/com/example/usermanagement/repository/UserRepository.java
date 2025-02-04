package com.example.usermanagement.repository;

import com.example.usermanagement.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository {

    User save(User user);

    List<User> findAll();

    User findOne(String id);

    User findOneByEmail(String email);

    long delete(String id);

    User update(User user);

    User update(String id, Map<String, Object> updateMap);
}
