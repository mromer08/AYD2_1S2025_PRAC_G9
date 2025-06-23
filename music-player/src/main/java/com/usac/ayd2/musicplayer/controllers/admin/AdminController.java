package com.usac.ayd2.musicplayer.controllers.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import com.usac.ayd2.musicplayer.config.PersistenceManager;
import com.usac.ayd2.musicplayer.config.ThemeManager;
import com.usac.ayd2.musicplayer.dto.music.NewSongDTO;
import com.usac.ayd2.musicplayer.dto.music.SongDTO;
import com.usac.ayd2.musicplayer.patterns.singleton.Session;
import com.usac.ayd2.musicplayer.router.Router;
import com.usac.ayd2.musicplayer.services.music.SongServiceImpl;
import com.usac.ayd2.musicplayer.utils.AlertUtil;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;

public class AdminController {

    private FontIcon sunIcon = new FontIcon(FontAwesomeSolid.SUN);
    private FontIcon moonIcon = new FontIcon(FontAwesomeSolid.MOON);
    private boolean isDark = false;

    @FXML
    private Button switcher;

    @FXML
    private Tooltip switcherT;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtArtist;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtFilePath;

    @FXML
    private Button btnBrowse;

    @FXML
    private Button btnSave;

    private File selectedFile;

    private final SongServiceImpl songServiceImpl = SongServiceImpl.getInstance();

    @FXML
    private void initialize() {
        sunIcon.setFill(Color.WHITE);
        sunIcon.setIconSize(16);
        moonIcon.setFill(Color.WHITE);
        moonIcon.setIconSize(16);
        switcher.setGraphic(moonIcon);
        switcherT.setText("Tema Oscuro");
        switcherT.setShowDelay(new Duration(100));
        switcher.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setUserData(this);
            }
        });
        colTitle.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().title()));
        colArtist.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().artist()));
        colDuration.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().duration()));

        loadSongs();
    }

    @FXML
    private void toggleTheme(ActionEvent event) {
        Scene scene = switcher.getScene();
        ThemeManager.toggleTheme(scene);
        isDark = !isDark;
        if (isDark) {
            switcher.setGraphic(sunIcon);
            switcherT.setText("Tema Claro");
        } else {
            switcher.setGraphic(moonIcon);
            switcherT.setText("Tema Oscuro");
        }
    }

    @FXML
    private void handleChooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar canción");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos de audio", "*.mp3", "*.wav", "*.aac"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedFile = file;
            txtFilePath.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void handleSave(ActionEvent event) {
        if (selectedFile == null || txtTitle.getText().isBlank() || txtArtist.getText().isBlank()) {
            AlertUtil.showAlert(AlertType.ERROR, "Completa todos los campos.");
            return;
        }
        try {
            String copiedPath = copySongToPersistenceFolder(selectedFile);

            NewSongDTO newSong = new NewSongDTO(
                    txtTitle.getText(),
                    txtArtist.getText(),
                    txtDuration.getText(),
                    copiedPath);
            songServiceImpl.saveSong(newSong);
            AlertUtil.showAlert(AlertType.INFORMATION, "Cancion guardada con exito.");
            clearForm();
            loadSongs();
        } catch (IOException e) {
            AlertUtil.showAlert(AlertType.ERROR, "Error al copiar el archivo.");
        }
    }

    private void clearForm() {
        txtTitle.clear();
        txtArtist.clear();
        txtDuration.clear();
        txtFilePath.clear();
        selectedFile = null;
    }

    private String copySongToPersistenceFolder(File sourceFile) throws IOException {
        Path destinationDir = PersistenceManager.getSongsDir();
        String extension = "";
        String originalName = sourceFile.getName();
        int dotIndex = originalName.lastIndexOf('.');
        if (dotIndex != -1) {
            extension = originalName.substring(dotIndex);
        }
        String uniqueFileName = UUID.randomUUID().toString() + extension;
        Path destination = destinationDir.resolve(uniqueFileName);
        Files.copy(sourceFile.toPath(), destination);
        return destination.toAbsolutePath().toString();
    }

    @FXML
    private TableView<SongDTO> songTable;
    @FXML
    private TableColumn<SongDTO, String> colTitle;
    @FXML
    private TableColumn<SongDTO, String> colArtist;
    @FXML
    private TableColumn<SongDTO, String> colDuration;

    private void loadSongs() {
        List<SongDTO> songs = SongServiceImpl.getInstance().getAllSongs();
        songTable.setItems(FXCollections.observableArrayList(songs));
    }

    @FXML
    private void logout() throws IOException {
        Session.getInstance().logout();
        Router.switchScene("/fxml/auth-layout.fxml", 600, 400, ThemeManager.getCurrentTheme(), false);
    }

}
