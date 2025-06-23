package com.usac.ayd2.musicplayer.mappers.music;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.usac.ayd2.musicplayer.dto.music.NewSongDTO;
import com.usac.ayd2.musicplayer.dto.music.SongDTO;
import com.usac.ayd2.musicplayer.models.music.Song;

@Mapper
public interface SongMapper {

    SongMapper INSTANCE = Mappers.getMapper(SongMapper.class);

    SongDTO toSongDTO(Song song);

    @Mapping(target = "id", ignore = true)
    Song toSong(NewSongDTO newSongDTO);
    
}
