package com.mailong245.hexagonalarchitecture.infrastructure.persistence.adapter.jpa;


import com.mailong245.hexagonalarchitecture.domain.model.User;
import com.mailong245.hexagonalarchitecture.domain.port.persistence.UserRepository;
import com.mailong245.hexagonalarchitecture.infrastructure.persistence.entity.UserEntity;
import com.mailong245.hexagonalarchitecture.infrastructure.persistence.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRepositoryJpaAdapter implements UserRepository {

    private final UserEntityRepository userEntityRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<User> findById(String id) {
        return userEntityRepository.findById(Long.valueOf(id)).map(userEntity -> modelMapper.map(userEntity, User.class));
    }

    @Override
    public List<User> findAllUser() {
        return userEntityRepository.findAll().stream().map(userEntity -> modelMapper.map(userEntity, User.class)).toList();
    }

    @Override
    public User save(User user) {
        UserEntity userEntity =  userEntityRepository.save(modelMapper.map(user, UserEntity.class));
        return modelMapper.map(userEntity, User.class);
    }

    @Override
    public void deleteById(String id) {
        userEntityRepository.deleteById(Long.valueOf(id));
    }
}
