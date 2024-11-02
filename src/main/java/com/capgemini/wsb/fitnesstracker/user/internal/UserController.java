package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;


    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {
        try {
            User user = userMapper.toEntity(userDto);
            userService.createUser(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot add user with email: " + userDto.email() + " with error: " + e.getMessage());
        }

        return null;
    }

    @GetMapping("/simple")
    public List<UserSimple> getAllUserSimple() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toUserSimple)
                .toList();
    }
    //Wyszykiwanie użytkowników po ID
    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    //Usuwanie użytkowników o ID
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot delete user with ID: " + userId + " with error: " + e.getMessage());
        }
    }

    //Szukanie emaila
    @GetMapping("/email")
    public List<UserEmailSimpleDto> getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmailIgnoreCase(email)
                .stream()
                .map(UserMapper::toEmailSimpleDto)
                .toList();
    }

    //Szukanie po wieku
    @GetMapping("/older/{birthDate}")
    public List<UserDto> getUsersOlderThan(@PathVariable String birthDate) {
        return userService.findOlderThan(birthDate)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }
    //Edycja rekordu
    @PutMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUser(userId, userMapper.toEntity(userDto));
        return userMapper.toDto(updatedUser);
    }

}