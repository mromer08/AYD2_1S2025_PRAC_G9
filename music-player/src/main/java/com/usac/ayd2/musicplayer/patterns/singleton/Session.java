package com.usac.ayd2.musicplayer.patterns.singleton;

import com.usac.ayd2.musicplayer.dto.user.UserDTO;

public class Session {

    public static Session instance;

    private UserDTO userDTO;

    private Session() { }

    public static Session getInstance() {
        if (instance == null) instance = new Session();
        return instance;
    }

    public UserDTO getUserDTO() {
        return this.userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public void logout() {
        this.userDTO = null;
    }
    
}
