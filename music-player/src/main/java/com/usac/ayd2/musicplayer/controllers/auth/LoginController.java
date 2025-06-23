package com.usac.ayd2.musicplayer.controllers.auth;

import java.io.IOException;

import com.usac.ayd2.musicplayer.config.ThemeManager;
import com.usac.ayd2.musicplayer.dto.user.NewUserDTO;
import com.usac.ayd2.musicplayer.dto.user.UserDTO;
import com.usac.ayd2.musicplayer.models.user.Role;
import com.usac.ayd2.musicplayer.patterns.singleton.Session;
import com.usac.ayd2.musicplayer.router.Router;
import com.usac.ayd2.musicplayer.services.user.UserServiceImpl;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class LoginController {

    private final UserServiceImpl userServiceImpl = UserServiceImpl.getInstance();
    private final Session session = Session.getInstance();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink registerLink;

    @FXML
    public void initialize() {
        loginButton.setDisable(true);
        usernameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validateUsername();
                checkEnableLogin();
            }
        });
        passwordField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validatePassword();
                checkEnableLogin();
            }
        });
        usernameField.textProperty().addListener((obs, oldVal, newVal) -> checkEnableLogin());
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> checkEnableLogin());
    }

    private void validateUsername() {
        if (usernameField.getText() == null || usernameField.getText().trim().isEmpty()) {
            usernameField.setStyle("-fx-border-color: red;");
            usernameField.setTooltip(new Tooltip("El usuario es obligatorio"));
        } else {
            usernameField.setStyle(null);
            usernameField.setTooltip(null);
        }
    }

    private void validatePassword() {
        if (passwordField.getText() == null || passwordField.getText().trim().isEmpty()) {
            passwordField.setStyle("-fx-border-color: red;");
            passwordField.setTooltip(new Tooltip("La contraseña es obligatoria"));
        } else {
            passwordField.setStyle(null);
            passwordField.setTooltip(null);
        }
    }

    private void checkEnableLogin() {
        boolean valid = (usernameField.getText() != null && !usernameField.getText().trim().isEmpty())
                && (passwordField.getText() != null && !passwordField.getText().trim().isEmpty());
        loginButton.setDisable(!valid);
    }

    @FXML
    private void handleLogin() throws IOException {
        validateUsername();
        validatePassword();
        if (!loginButton.isDisabled()) {
            final NewUserDTO loginData = new NewUserDTO(usernameField.getText(), passwordField.getText());
            final UserDTO userDTO = userServiceImpl.login(loginData);
            if (userDTO == null) return;
            session.setUserDTO(userDTO);
            final String pathFxml = "/fxml/" + session.getUserDTO().role().toString().toLowerCase()  + ".fxml";
            final boolean resizable = session.getUserDTO().role().equals(Role.USER);
            Router.switchScene(pathFxml, resizable ? 1000 : 600, 700, ThemeManager.getCurrentTheme(), resizable);
        }
    }

    @FXML
    private void handleRegisterLink() {
        AuthLayoutController authController = (AuthLayoutController) usernameField.getScene().getUserData();
        if (authController != null) {
            authController.loadForm("register");
        }
    }
}
