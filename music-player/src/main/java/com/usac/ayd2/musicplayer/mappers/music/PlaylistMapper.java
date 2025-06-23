package com.usac.ayd2.musicplayer.mappers.music;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.usac.ayd2.musicplayer.dto.music.NewPlaylistDTO;
import com.usac.ayd2.musicplayer.dto.music.PlaylistDTO;
import com.usac.ayd2.musicplayer.models.music.Playlist;

@Mapper
public interface PlaylistMapper {

    PlaylistMapper INSTANCE = Mappers.getMapper(PlaylistMapper.class);

    PlaylistDTO toPlaylistDTO(Playlist playlist);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "songs", ignore = true)
    Playlist toPlaylist(NewPlaylistDTO newPlayListDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "songs", ignore = true)
    void updatePlaylistFromDTO(NewPlaylistDTO newPlaylistDTO, @MappingTarget Playlist playlist);
    
}
