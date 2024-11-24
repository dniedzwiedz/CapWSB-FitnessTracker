package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

@Component
class UserMapper {

    UserSimple toUserSimple(User user){
        return new UserSimple(user.getId(), user.getFirstName(), user.getLastName());
    }

    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                           user.getFirstName(),
                           user.getLastName(),
                           user.getBirthdate(),
                           user.getEmail());
    }

    User toEntity(UserDto userDto) {
        return new User(
                        userDto.firstName(),
                        userDto.lastName(),
                        userDto.birthdate(),
                        userDto.email());
    }

    static UserEmailSimpleDto toEmailSimpleDto(User user) {
        return new UserEmailSimpleDto(user.getId(), user.getEmail());
    }

}
