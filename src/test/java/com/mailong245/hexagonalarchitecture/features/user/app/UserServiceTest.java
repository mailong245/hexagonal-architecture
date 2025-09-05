package com.mailong245.hexagonalarchitecture.features.user.app;

import com.mailong245.hexagonalarchitecture.domain.model.User;
import com.mailong245.hexagonalarchitecture.domain.port.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void testGetAllUser() {
        List<User> users = List.of(new User(), new User());
        when(userRepository.findAllUser()).thenReturn(users);

        List<User> result = userService.getAllUser();

        assertEquals(2, result.size());
        verify(userRepository).findAllUser();
    }

    @Test
    void testGetUserById_found() {
        User user = new User();
        when(userRepository.findById("1")).thenReturn(java.util.Optional.of(user));

        User result = userService.getUserById("1");

        assertNotNull(result);
        verify(userRepository).findById("1");
    }

    @Test
    void testGetUserById_notFound() {
        when(userRepository.findById("2")).thenReturn(java.util.Optional.empty());

        User result = userService.getUserById("2");

        assertNull(result);
        verify(userRepository).findById("2");
    }

    @Test
    void testCreateUser() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertEquals(user, result);
        verify(userRepository).save(user);
    }

    @Test
    void testDeleteUserById() {
        doNothing().when(userRepository).deleteById("3");

        userService.deleteUserById("3");

        verify(userRepository).deleteById("3");
    }

}
