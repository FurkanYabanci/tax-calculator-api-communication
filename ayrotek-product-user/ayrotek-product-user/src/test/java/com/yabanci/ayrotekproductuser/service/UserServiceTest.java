package com.yabanci.ayrotekproductuser.service;

import com.yabanci.ayrotekproductuser.dto.UserDto;
import com.yabanci.ayrotekproductuser.dto.request.UserSaveRequestDto;
import com.yabanci.ayrotekproductuser.dto.request.UserUpdateRequestDto;
import com.yabanci.ayrotekproductuser.exception.ItemNotFoundException;
import com.yabanci.ayrotekproductuser.model.User;
import com.yabanci.ayrotekproductuser.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Spy
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;


    @Test
    void shouldFindAll() {
        User user = mock(User.class);
        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(userRepository.findAll()).thenReturn(userList);

        List<UserDto> result = userService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void shouldFindAllWhenProductListIsEmpty() {
        List<User> userList = new ArrayList<>();

        when(userRepository.findAll()).thenReturn(userList);

        List<UserDto> result = userService.findAll();

        assertEquals(0, result.size());
    }

    @Test
    void shouldSave() {
        UserSaveRequestDto userSaveRequestDto = mock(UserSaveRequestDto.class);
        User user = mock(User.class);

        when(user.getFirstName()).thenReturn("Furkan");
        when(userRepository.save(any())).thenReturn(user);

        UserDto result = userService.save(userSaveRequestDto);

        assertEquals(result.getFirstName(),"Furkan");
    }

    @Test
    void shouldUpdate() {
        UserUpdateRequestDto userUpdateRequestDto = mock(UserUpdateRequestDto.class);
        User user = mock(User.class);

        doReturn(user).when(userService).findById(anyLong());

        when(user.getFirstName()).thenReturn("Furkan");

        when(userRepository.save(any())).thenReturn(user);

        UserDto result = userService.update(userUpdateRequestDto);

        assertEquals(result.getFirstName(),"Furkan");

    }

    @Test
    void shouldNotUpdateWhenCustomerDoesNotExist() {

        UserUpdateRequestDto userUpdateRequestDto = mock(UserUpdateRequestDto.class);

        when(userRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> userService.update(userUpdateRequestDto));

        verify(userRepository).findById(anyLong());
    }

    @Test
    void shouldDelete() {
        User user = mock(User.class);

        doReturn(user).when(userService).findById(anyLong());

        userService.delete(anyLong());

        verify(userRepository).delete(any());
        verify(userService).findById(anyLong());
    }

    @Test
    void shouldNotDeleteWhenIdDoesNotExist() {

        when(userRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> userService.delete(anyLong()));

        verify(userRepository).findById(anyLong());
    }

    @Test
    void shouldFindById() {
        User user = mock(User.class);

        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        User result = userService.findById(anyLong());

        assertEquals(result.getId(), user.getId());
    }

    @Test
    void shouldNotFindByIdWhenIdDoesNotExist(){
        when(userRepository.findById(anyLong())).thenThrow(ItemNotFoundException.class);
        assertThrows(ItemNotFoundException.class, () -> userService.findById(anyLong()));
        verify(userRepository).findById(anyLong());
    }
}