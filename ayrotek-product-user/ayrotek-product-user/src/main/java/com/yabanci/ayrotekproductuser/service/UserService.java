package com.yabanci.ayrotekproductuser.service;

import com.yabanci.ayrotekproductuser.converter.UserMapper;
import com.yabanci.ayrotekproductuser.dto.UserDto;
import com.yabanci.ayrotekproductuser.dto.request.UserSaveRequestDto;
import com.yabanci.ayrotekproductuser.dto.request.UserUpdateRequestDto;
import com.yabanci.ayrotekproductuser.enums.UserErrorMessage;
import com.yabanci.ayrotekproductuser.exception.ItemNotFoundException;
import com.yabanci.ayrotekproductuser.model.User;
import com.yabanci.ayrotekproductuser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDto> findAll(){
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = UserMapper.INSTANCE.convertToUserDtoList(users);
        return userDtoList;
    }
    public UserDto save(UserSaveRequestDto userSaveRequestDto){
        User user = UserMapper.INSTANCE.convertToUser(userSaveRequestDto);
        user = userRepository.save(user);
        UserDto userDto = UserMapper.INSTANCE.convertToUserDto(user);
        return userDto;
    }

    public UserDto update(UserUpdateRequestDto userUpdateRequestDto){
        User user = findById(userUpdateRequestDto.getId());
        user.setFirstName(userUpdateRequestDto.getFirstName());
        user.setLastName(userUpdateRequestDto.getLastName());
        user = userRepository.save(user);
        UserDto userDto = UserMapper.INSTANCE.convertToUserDto(user);
        return userDto;
    }

    public void delete(Long id){
        User user = findById(id);
        userRepository.delete(user);
    }
    public User findById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(UserErrorMessage.USER_NOT_FOUND));
        return user;
    }
}