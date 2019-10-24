package com.tjlee.oauth2.mapper;

import com.tjlee.oauth2.domain.User;
import com.tjlee.oauth2.dto.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User entity);

    User toEntity(UserDTO dto);

    List<User> toListEntity(List<UserDTO> userDTOS);

    List<UserDTO> toListDTO(List<User> userDTOS);

    default User postFromId(Long id) {
        if (id == null) {
            return null;
        }
        User that = new User();
        that.setId(id);
        return that;
    }
}
