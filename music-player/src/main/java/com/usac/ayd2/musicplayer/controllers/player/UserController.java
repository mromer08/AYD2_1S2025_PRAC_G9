package com.usac.ayd2.musicplayer.controllers.player;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import com.usac.ayd2.musicplayer.config.ThemeManager;
import com.usac.ayd2.musicplayer.dto.music.NewPlaylistDTO;
import com.usac.ayd2.musicplayer.dto.music.PlaylistDTO;
import com.usac.ayd2.musicplayer.dto.music.SongDTO;
import com.usac.ayd2.musicplayer.patterns.facade.MusicPlayerFacade;
import com.usac.ayd2.musicplayer.patterns.facade.MusicPlayerFacadeImpl;
import com.usac.ayd2.musicplayer.patterns.singleton.Session;
import com.usac.ayd2.musicplayer.router.Router;
import com.usac.ayd2.musicplayer.utils.AlertUtil;
import com.usac.ayd2.musicplayer.utils.InputUtil;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class UserController {

    private final MusicPlayerFacade musicFacade = new MusicPlayerFacadeImpl();

    private List<SongDTO> currentPlaylistSongs;
    private int currentPlaylistIndex = 0;
    private SongDTO currentSongDTO;
    private MediaPlayer mediaPlayer;

    private final FontIcon sunIcon = new FontIcon(FontAwesomeSolid.SUN);
    private final FontIcon moonIcon = new FontIcon(FontAwesomeSolid.MOON);
    private final FontIcon playIcon = new FontIcon(FontAwesomeSolid.PLAY);
    private final FontIcon pauseIcon = new FontIcon(FontAwesomeSolid.PAUSE);
    private final FontIcon stopIcon = new FontIcon(FontAwesomeSolid.STOP);

    private boolean isDark = false;

    @FXML
    private Button switcher;
    @FXML
    private Tooltip switcherT;
    @FXML
    private TableView<SongDTO> libraryTableView, songTableView;
    @FXML
    private TableColumn<SongDTO, String> colLibTitle, colLibArtist, colLibDuration;
    @FXML
    private TableColumn<SongDTO, String> colTitle, colArtist, colDuration;
    @FXML
    private ListView<PlaylistDTO> playlistListView;
    @FXML
    private Button btnAddToPlaylist, btnRemoveSongFromPlaylist, btnPlay, btnPause, btnStop;
    @FXML
    private Label lblCurrentSong;

    @FXML
    private void initialize() {
        setIcons();
        btnPlay.setGraphic(playIcon);
        btnPause.setGraphic(pauseIcon);
        btnStop.setGraphic(stopIcon);
        switcher.setGraphic(moonIcon);
        switcherT.setText("Tema Oscuro");
        switcherT.setShowDelay(new Duration(100));
        switcher.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setUserData(this);
            }
        });
        setView();
        loadLibrarySongs();
        loadPlaylists();
        playlistListView.setOnMouseClicked(this::handlePlaylistSelection);
    }

    private void setView() {
        colLibTitle.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().title()));
        colLibArtist.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().artist()));
        colLibDuration.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().duration()));
        colTitle.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().title()));
        colArtist.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().artist()));
        colDuration.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().duration()));
        setListView();

        songTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                SongDTO selectedSong = songTableView.getSelectionModel().getSelectedItem();
                if (selectedSong != null) {
                    playSingleSong(selectedSong);
                }
            }
        });

        libraryTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                SongDTO selectedSong = libraryTableView.getSelectionModel().getSelectedItem();
                if (selectedSong != null) {
                    playSingleSong(selectedSong);
                }
            }
        });
    }

    private void setListView() {
        playlistListView.setCellFactory(lv -> new ListCell<>() {
            private final Button playButton = new Button();
            private final Label nameLabel = new Label();
            private final Region spacer = new Region();
            private final HBox container = new HBox(nameLabel, spacer, playButton);

            {
                FontIcon playIcon = new FontIcon(FontAwesomeSolid.PLAY);
                playIcon.setIconSize(14);
                playIcon.setFill(Color.WHITE);
                playButton.setGraphic(playIcon);
                playButton.getStyleClass().add("circular-button");
                HBox.setHgrow(spacer, Priority.ALWAYS);
                container.setAlignment(Pos.CENTER_LEFT);
                container.setSpacing(10);
                playButton.setOnAction(e -> {
                    PlaylistDTO playlist = getItem();
                    if (playlist != null) {
                        playPlaylist(playlist);
                    }
                });
            }

            @Override
            protected void updateItem(PlaylistDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    nameLabel.setText(item.name());
                    setGraphic(container);
                }
            }
        });
    }

    @FXML
    private void toggleTheme(ActionEvent event) {
        Scene scene = switcher.getScene();
        ThemeManager.toggleTheme(scene);
        isDark = !isDark;
        switcher.setGraphic(isDark ? sunIcon : moonIcon);
        switcherT.setText(isDark ? "Tema Claro" : "Tema Oscuro");
    }

    private void setIcons() {
        sunIcon.setFill(Color.WHITE);
        moonIcon.setFill(Color.WHITE);
        playIcon.setFill(Color.WHITE);
        pauseIcon.setFill(Color.WHITE);
        stopIcon.setFill(Color.WHITE);
        sunIcon.setIconSize(16);
        moonIcon.setIconSize(16);
        playIcon.setIconSize(16);
        pauseIcon.setIconSize(16);
        stopIcon.setIconSize(16);
    }

    private void loadLibrarySongs() {
        libraryTableView.setItems(FXCollections.observableArrayList(musicFacade.getAllSongs()));
    }

    private void loadPlaylists() {
        var playlists = musicFacade.getPlaylistsByUser(Session.getInstance().getUserDTO().id());
        playlistListView.setItems(FXCollections.observableArrayList(playlists));
    }

    private void loadSongsFromPlaylist(PlaylistDTO playlistDTO) {
        var songs = musicFacade.getSongsInPlaylist(playlistDTO.id());
        songTableView.setItems(FXCollections.observableArrayList(songs));
    }

    @FXML
    private void handlePlaylistSelection(MouseEvent event) {
        PlaylistDTO selected = playlistListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            loadSongsFromPlaylist(selected);
        }
    }

    @FXML
    private void handlePlayLibrarySong() {
        SongDTO selectedSong = libraryTableView.getSelectionModel().getSelectedItem();
        if (selectedSong != null && !selectedSong.equals(currentSongDTO)) {
            playSingleSong(selectedSong);
        }
    }

    @FXML
    private void btnPlay() {
        SongDTO selectedSong = libraryTableView.getSelectionModel().getSelectedItem();
        if (selectedSong == null) {
            lblCurrentSong.setText("Selecciona una canción para reproducir");
            return;
        }
        if (!selectedSong.equals(currentSongDTO)) {
            playSingleSong(selectedSong);
            return;
        }
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    @FXML
    private void btnPause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @FXML
    private void btnStop() {
        lblCurrentSong.setText("Reproduciendo:");
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            currentSongDTO = null;
        }
    }

    @FXML
    private void btnAddToPlaylist() {
        PlaylistDTO selectedPlaylist = playlistListView.getSelectionModel().getSelectedItem();
        SongDTO selectedSong = libraryTableView.getSelectionModel().getSelectedItem();
        if (selectedPlaylist != null && selectedSong != null) {
            musicFacade.addSongToPlaylist(selectedPlaylist.id(), selectedSong.id());
            loadSongsFromPlaylist(selectedPlaylist);
        }
    }

    @FXML
    private void btnRemoveSongFromPlaylist() {
        PlaylistDTO selectedPlaylist = playlistListView.getSelectionModel().getSelectedItem();
        SongDTO selectedSong = songTableView.getSelectionModel().getSelectedItem();
        if (selectedPlaylist != null && selectedSong != null) {
            musicFacade.removeSongFromPlaylist(selectedPlaylist.id(), selectedSong.id());
            loadSongsFromPlaylist(selectedPlaylist);
        }
    }

    @FXML
    private void bntCreatePlaylist() {
        TextInputDialog dialog = InputUtil.returnStyle("Crear Playlist", "Nombre de la Playlist");
        dialog.showAndWait().ifPresent(name -> {
            String newName = name.trim();
            if (newName.isEmpty()) {
                System.out.println("El nombre no puede estar vacío.");
                return;
            }
            boolean exists = playlistListView.getItems().stream()
                    .anyMatch(p -> p.name().equalsIgnoreCase(newName));
            if (exists) {
                System.out.println("Ya existe una playlist con ese nombre.");
                return;
            }
            NewPlaylistDTO newPlaylist = new NewPlaylistDTO(
                    newName,
                    Session.getInstance().getUserDTO().username());
            musicFacade.savePlaylist(newPlaylist);
            loadPlaylists();
        });
    }

    @FXML
    private void btnRenamePlaylist() {
        PlaylistDTO selectedPlaylist = playlistListView.getSelectionModel().getSelectedItem();
        if (selectedPlaylist == null) {
            AlertUtil.showAlert(AlertType.INFORMATION, "No se ha seleccionado ninguna playlist");
            return;
        }
        TextInputDialog dialog = InputUtil.returnStyle("Editar Playlist", "Nombre Nuevo");
        dialog.showAndWait().ifPresent(newName -> {
            if (!newName.trim().isEmpty() && !newName.equals(selectedPlaylist.name())) {
                musicFacade.renamePlaylist(selectedPlaylist.id(), newName.trim());
                loadPlaylists();
            }
        });
    }

    @FXML
    private void btnDeletePlaylist() {
        PlaylistDTO selectedPlaylist = playlistListView.getSelectionModel().getSelectedItem();
        if (selectedPlaylist != null) {
            musicFacade.deletePlaylist(selectedPlaylist.id());
            loadPlaylists();
            songTableView.getItems().clear();
        }
    }

    private void playSingleSong(SongDTO song) {
        currentSongDTO = song;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        try {
            Media media = new Media(new File(song.filePath()).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            lblCurrentSong.setText("Reproduciendo: " + song.title() + " - " + song.artist() + " - " + song.duration());
        } catch (Exception e) {
            e.printStackTrace();
            lblCurrentSong.setText("Error al reproducir la canción");
        }
    }

    @FXML
    private void logout() throws IOException {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
        Session.getInstance().logout();
        Router.switchScene("/fxml/auth-layout.fxml", 600, 400, ThemeManager.getCurrentTheme(), false);
    }

    private void playPlaylist(PlaylistDTO playlist) {
        currentPlaylistSongs = musicFacade.getSongsInPlaylist(playlist.id());
        if (currentPlaylistSongs.isEmpty()) {
            lblCurrentSong.setText("Playlist vacía.");
            return;
        }
        currentPlaylistIndex = 0;
        playSongFromPlaylist(currentPlaylistSongs.get(currentPlaylistIndex));
    }

    private void playSongFromPlaylist(SongDTO song) {
        currentSongDTO = song;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
        try {
            Media media = new Media(new File(song.filePath()).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnEndOfMedia(() -> {
                currentPlaylistIndex++;
                if (currentPlaylistSongs != null && currentPlaylistIndex < currentPlaylistSongs.size()) {
                    playSongFromPlaylist(currentPlaylistSongs.get(currentPlaylistIndex));
                } else {
                    lblCurrentSong.setText("Fin de la playlist.");
                    mediaPlayer.dispose();
                }
            });
            mediaPlayer.play();
            lblCurrentSong.setText("Reproduciendo: " + song.title() + " - " + song.artist() + " - " + song.duration());
        } catch (Exception e) {
            e.printStackTrace();
            lblCurrentSong.setText("Error al reproducir la canción");
        }
    }
}
