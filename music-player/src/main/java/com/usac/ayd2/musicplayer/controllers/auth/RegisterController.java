package com.usac.ayd2.musicplayer.controllers.auth;

import java.io.IOException;

import com.usac.ayd2.musicplayer.config.ThemeManager;
import com.usac.ayd2.musicplayer.dto.user.NewUserDTO;
import com.usac.ayd2.musicplayer.dto.user.UserDTO;
import com.usac.ayd2.musicplayer.patterns.singleton.Session;
import com.usac.ayd2.musicplayer.router.Router;
import com.usac.ayd2.musicplayer.services.user.UserServiceImpl;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class RegisterController {

    private final UserServiceImpl userServiceImpl = UserServiceImpl.getInstance();
    private final Session session = Session.getInstance();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button registerButton;

    @FXML
    private Hyperlink loginLink;

    @FXML
    public void initialize() {
        registerButton.setDisable(true);
        usernameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validateUsername();
                updateRegisterButton();
            }
        });
        passwordField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validatePassword();
                updateRegisterButton();
            }
        });
        confirmPasswordField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validateConfirmPassword();
                updateRegisterButton();
            }
        });
        usernameField.textProperty().addListener((obs, oldVal, newVal) -> updateRegisterButton());
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> updateRegisterButton());
        confirmPasswordField.textProperty().addListener((obs, oldVal, newVal) -> updateRegisterButton());
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

    private void validateConfirmPassword() {
        String pwd = passwordField.getText();
        String confirmPwd = confirmPasswordField.getText();

        if (confirmPwd == null || !confirmPwd.equals(pwd)) {
            confirmPasswordField.setStyle("-fx-border-color: red;");
            confirmPasswordField.setTooltip(new Tooltip("Las contraseñas no coinciden"));
        } else {
            confirmPasswordField.setStyle(null);
            confirmPasswordField.setTooltip(null);
        }
    }

    private void updateRegisterButton() {
        boolean valid = usernameField.getText() != null && !usernameField.getText().trim().isEmpty()
                && passwordField.getText() != null && !passwordField.getText().trim().isEmpty()
                && confirmPasswordField.getText() != null
                && confirmPasswordField.getText().equals(passwordField.getText());
        registerButton.setDisable(!valid);
    }

    @FXML
    private void handleRegister() throws IOException {
        validateUsername();
        validatePassword();
        validateConfirmPassword();
        if (!registerButton.isDisabled()) {
            final NewUserDTO registerData = new NewUserDTO(usernameField.getText(), passwordField.getText());
            final UserDTO userDTO = userServiceImpl.register(registerData);
            if (userDTO == null) return;
            session.setUserDTO(userDTO);
            final String pathFxml = "/fxml/user.fxml";
            Router.switchScene(pathFxml, 1000, 700, ThemeManager.getCurrentTheme(), true);
        }
    }

    @FXML
    private void handleLoginLink() {
        AuthLayoutController authController = (AuthLayoutController) usernameField.getScene().getUserData();
        if (authController != null) {
            authController.loadForm("login");
        }
    }
}
