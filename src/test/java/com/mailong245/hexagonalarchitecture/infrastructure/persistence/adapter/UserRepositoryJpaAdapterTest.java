package com.mailong245.hexagonalarchitecture.infrastructure.persistence.adapter;


import com.mailong245.hexagonalarchitecture.domain.model.User;
import com.mailong245.hexagonalarchitecture.infrastructure.persistence.adapter.jpa.UserRepositoryJpaAdapter;
import com.mailong245.hexagonalarchitecture.infrastructure.persistence.entity.UserEntity;
import com.mailong245.hexagonalarchitecture.infrastructure.persistence.repository.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryJpaAdapterTest {

    @InjectMocks
    private UserRepositoryJpaAdapter userRepositoryJpaAdapter;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private UserEntityRepository userEntityRepository;

    @Test
    void testFindById() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userEntityRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        User user = User.builder().build();
        doReturn(user).when(modelMapper).map(userEntity, User.class);

        Optional<User> result = userRepositoryJpaAdapter.findById("1");
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindAllUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userEntityRepository.findAll()).thenReturn(Arrays.asList(userEntity));
        User user = User.builder().build();
        doReturn(user).when(modelMapper).map(userEntity, User.class);

        List<User> result = userRepositoryJpaAdapter.findAllUser();
        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
    }

    @Test
    void testSave() {
        User user = User.builder().build();
        UserEntity userEntity = new UserEntity();
        doReturn(userEntity).when(modelMapper).map(user, UserEntity.class);
        when(userEntityRepository.save(userEntity)).thenReturn(userEntity);
        doReturn(user).when(modelMapper).map(userEntity, User.class);

        User result = userRepositoryJpaAdapter.save(user);
        assertEquals(user, result);
    }

    @Test
    void testDeleteById() {
        userRepositoryJpaAdapter.deleteById("1");
        verify(userEntityRepository, times(1)).deleteById(1L);
    }


}
