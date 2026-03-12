package com.portafolio_final.controller;

import com.portafolio_final.MainApp;
import com.portafolio_final.topics.BarajasAleatoridad;
import com.portafolio_final.topics.EjemploTema;
import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    // ── Labels del Dashboard ─────────────────────────────────────
    @FXML private Label lblNombreAlumno;   // Nombre en la tarjeta de bienvenida
    @FXML private Label lblGrupoAlumno;    // Grupo en la tarjeta de bienvenida
    @FXML private Label lblSemestre;       // Semestre/periodo
    @FXML private Label lblFecha;          // Fecha actual
    @FXML private Label lblTotalTemas;     // Contador de temas cubiertos

    // ── Labels del footer del sidebar ───
    @FXML private Label lblNombreSidebar;
    @FXML private Label lblGrupoSidebar;

    // ── Botones del menú lateral ───
    @FXML private Button btnInicio;
    @FXML private Button btnTema1;
    @FXML private Button btnTema2;

    // ── Paso 2/3: Declara aquí el botón de cada nuevo tema ───────
    // @FXML private Button btnTema2;

    // inicialización:
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblNombreAlumno.setText(MainApp.NOMBRE_ALUMNO);
        lblGrupoAlumno.setText(MainApp.GRUPO);
        lblSemestre.setText(MainApp.SEMESTRE);
        lblNombreSidebar.setText(MainApp.NOMBRE_ALUMNO);
        lblGrupoSidebar.setText(MainApp.GRUPO + "  •  " + MainApp.SEMESTRE);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", new Locale("es", "MX"));
        lblFecha.setText(LocalDate.now().format(fmt));

        // Actualizar conforme los temas q se vayan agregando
        lblTotalTemas.setText("2");

        setActiveButton(btnInicio);
    }

    // metodos de navegación

    // back a la pantalla de bienvenida
    @FXML
    private void mostrarInicio() {
        setActiveButton(btnInicio);
    }

    // temas
    @FXML
    private void abrirTema1() {
        setActiveButton(btnTema1);
        new EjemploTema().show();
    }

    // agregar metodo nuevo por cada tema
     @FXML
     private void abrirTema2() {
         setActiveButton(btnTema2);
         new BarajasAleatoridad().show();
     }

    /*
    metodo utilitario: gestiona el boton activo
    @param activeButton Botón que debe marcarse como activo
     */

    private void setActiveButton(Button activeButton) {
        Button[] allButtons = { btnInicio, btnTema1 /* ,btntema2, etc*/};

        for (Button btn : allButtons) {
            btn.getStyleClass().remove("nav-button-active");
        }
        if (!activeButton.getStyleClass().contains("nav-button-active")) {
            activeButton.getStyleClass().add("nav-button-active");
        }
    }

}