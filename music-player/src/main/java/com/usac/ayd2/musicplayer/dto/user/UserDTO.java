package com.usac.ayd2.musicplayer.dto.user;

import com.usac.ayd2.musicplayer.models.user.Role;

public record UserDTO(
    Long id,
    String username,
    Role role
) {}
