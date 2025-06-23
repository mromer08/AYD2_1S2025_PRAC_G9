package com.usac.ayd2.musicplayer;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import com.usac.ayd2.musicplayer.config.PersistenceManager;
import com.usac.ayd2.musicplayer.router.Router;
import com.usac.ayd2.musicplayer.utils.HibernateUtil;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        HibernateUtil.getSessionFactory();
        PersistenceManager.initialize(stage);
        Image icon = new Image(getClass().getResourceAsStream("/images/logo-1.png"));
        stage.getIcons().add(icon);
        Router.setPrimaryStage(stage);
        String theme = PersistenceManager.loadTheme();
        Router.switchScene("/fxml/auth-layout.fxml", 600, 400, theme, false);
    }

    @Override
    public void stop() throws Exception {
        HibernateUtil.shutdown();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}