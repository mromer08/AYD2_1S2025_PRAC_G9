package com.usac.ayd2.musicplayer.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class PersistenceManager {

    private static Path baseDir;
    private static final String CONFIG_FILE = "config.properties";
    private static final Path APP_DATA_FILE = Paths.get(System.getProperty("user.home"), ".musicplayer", "path.properties");

    public static void initialize(Stage stage) throws IOException {
        if (Files.exists(APP_DATA_FILE)) {
            Properties props = new Properties();
            try (InputStream inputStream = Files.newInputStream(APP_DATA_FILE)) {
                props.load(inputStream);
                String pathStr = props.getProperty("baseDir");
                if (pathStr != null) {
                    baseDir = Paths.get(pathStr);
                } else {
                    throw new IOException("Ruta base no encontrada en path.properties");
                }
            }
        }
        if (baseDir == null || !Files.exists(baseDir)) {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Selecciona una carpeta para guardar los datos");
            File selected = chooser.showDialog(stage);
            if (selected == null) {
                throw new IOException("No se seleccionó ninguna carpeta de persistencia");
            }
            baseDir = selected.toPath();
            Files.createDirectories(baseDir.resolve("songs"));
            savePathToFile(baseDir);
            saveTheme("light");
        }
    }

    private static void savePathToFile(Path path) throws IOException {
        Files.createDirectories(APP_DATA_FILE.getParent());
        Properties props = new Properties();
        props.setProperty("baseDir", path.toString());
        try (OutputStream out = Files.newOutputStream(APP_DATA_FILE)) {
            props.store(out, "Ruta de persistencia del reproductor");
        }
    }

    public static Path getConfigPath() {
        if (baseDir == null) throw new IllegalStateException("PersistenceManager no ha sido inicializado.");
        return baseDir.resolve(CONFIG_FILE);
    }

    public static Path getSongsDir() {
        if (baseDir == null) throw new IllegalStateException("PersistenceManager no ha sido inicializado.");
        return baseDir.resolve("songs");
    }

    public static void saveTheme(String theme) throws IOException {
        Properties props = new Properties();
        props.setProperty("theme", theme);
        try (OutputStream out = Files.newOutputStream(getConfigPath())) {
            props.store(out, "Configuración del tema");
        }
    }

    public static String loadTheme() throws IOException {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(getConfigPath())) {
            props.load(in);
            return props.getProperty("theme", "light");
        }
    }

    public static Path getBaseDir() {
        return baseDir;
    }
    
}
