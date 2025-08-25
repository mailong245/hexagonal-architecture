package com.mailong245.hexagonalarchitecture.domain.port.persistence;

import com.mailong245.hexagonalarchitecture.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(String id);
    List<User> findAllUser();
    User save(User user);
    void deleteById(String id);

}
