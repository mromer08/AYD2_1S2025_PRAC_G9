package com.usac.ayd2.musicplayer.services.user;

import com.usac.ayd2.musicplayer.dto.user.NewUserDTO;
import com.usac.ayd2.musicplayer.dto.user.UserDTO;

public interface UserService {

    UserDTO register(NewUserDTO newUserDTO);
    UserDTO login(NewUserDTO newUserDTO);
    
}
