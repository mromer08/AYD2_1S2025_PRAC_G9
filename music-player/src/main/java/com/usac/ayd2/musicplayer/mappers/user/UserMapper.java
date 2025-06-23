package com.usac.ayd2.musicplayer.mappers.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.usac.ayd2.musicplayer.dto.user.NewUserDTO;
import com.usac.ayd2.musicplayer.dto.user.UserDTO;
import com.usac.ayd2.musicplayer.models.user.User;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toUserDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toUser(NewUserDTO newUserDTO);
    
}
