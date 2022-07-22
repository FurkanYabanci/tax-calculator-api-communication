package com.yabanci.ayrotekproductuser.controller;

import com.yabanci.ayrotekproductuser.dto.GeneralResponseDto;
import com.yabanci.ayrotekproductuser.dto.UserDto;
import com.yabanci.ayrotekproductuser.dto.request.UserSaveRequestDto;
import com.yabanci.ayrotekproductuser.dto.request.UserUpdateRequestDto;
import com.yabanci.ayrotekproductuser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity findAll(){
        List<UserDto> userDtoList = userService.findAll();
        return ResponseEntity.ok(GeneralResponseDto.of(userDtoList));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        UserDto userDto = userService.save(userSaveRequestDto);
        return ResponseEntity.ok(GeneralResponseDto.of(userDto));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody UserUpdateRequestDto userUpdateRequestDto){
        UserDto userDto = userService.update(userUpdateRequestDto);
        return ResponseEntity.ok(GeneralResponseDto.of(userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id){
        userService.delete(id);
        return ResponseEntity.ok(GeneralResponseDto.of("User Deleted!"));
    }
}