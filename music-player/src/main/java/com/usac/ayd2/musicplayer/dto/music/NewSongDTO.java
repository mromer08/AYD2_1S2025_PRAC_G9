package com.usac.ayd2.musicplayer.dto.music;


public record NewSongDTO(

    String title,
    String artist,
    String duration,
    String filePath

) { }
