package com.portafolio_final;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.util.Objects;

public class MainApp extends Application {

    // datos
    public static final String NOMBRE_ALUMNO = "Miguel Angel Correa Martinez";
    public static final String GRUPO         = "2407";
    public static final String SEMESTRE      = "2025-1";
    public static final String MATERIA       = "Probabilidad y Estadística";

    // Ventana
    private static final double ANCHO  = 1150;
    private static final double ALTO   = 720;
    private static final double MIN_ANCHO = 900;
    private static final double MIN_ALTO  = 580;

    @Override
    public void start(Stage primaryStage) throws Exception {

        // --- Cargar el layout principal desde el archivo FXML ---
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("main-view.fxml")
        );

        if (loader.getLocation() == null) {
            throw new IllegalStateException(
                    "[ERROR] No se encontró main-view.fxml.\n"
            );
        }

        Scene scene = new Scene(loader.load(), ANCHO, ALTO);

        // --- Aplicar hojas de estilo ---
        // BootstrapFX aporta clases utilitarias de Bootstrap
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        // CSS personalizado del portafolio
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("main.css")).toExternalForm()
        );

        // --- Configurar la ventana principal ---
        primaryStage.setTitle("Portafolio Interactivo — " + MATERIA);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(MIN_ANCHO);
        primaryStage.setMinHeight(MIN_ALTO);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    /** Punto de entrada de Java */
    public static void main(String[] args) {
        launch(args);
    }
}