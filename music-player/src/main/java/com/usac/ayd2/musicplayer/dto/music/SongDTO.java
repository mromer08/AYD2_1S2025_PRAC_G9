package com.usac.ayd2.musicplayer.dto.music;

public record SongDTO(

    Long id,
    String title,
    String artist,
    String duration,
    String filePath
    
) { }
