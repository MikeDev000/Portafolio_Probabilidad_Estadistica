package com.portafolio_final.controller;

import com.portafolio_final.MainApp;
import com.portafolio_final.topics.BarajasAleatoridad;
import com.portafolio_final.topics.EjemploTema;
import com.portafolio_final.topics.ReglaAditiva;
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
    // Labels del Dashboard
    @FXML private Label lblNombreAlumno;
    @FXML private Label lblGrupoAlumno;
    @FXML private Label lblSemestre;
    @FXML private Label lblFecha;
    @FXML private Label lblTotalTemas;

    // Labels del footer del sidebar
    @FXML private Label lblNombreSidebar;
    @FXML private Label lblGrupoSidebar;

    // Botones del menú lateral
    @FXML private Button btnInicio;
    @FXML private Button btnTema1;
    @FXML private Button btnTema2;
    @FXML private Button btnTema3;

    // inicialización
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
        lblTotalTemas.setText("3");

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

     @FXML
     private void abrirTema2() {
         setActiveButton(btnTema2);
         new BarajasAleatoridad().show();
     }

     @FXML
     private void abrirTema3() {
        setActiveButton(btnTema3);
        new ReglaAditiva().show();
     }

    // @param activeButton Botón que debe marcarse como activo

    private void setActiveButton(Button activeButton) {
        Button[] allButtons = { btnInicio, btnTema1, btnTema2, btnTema3};

        for (Button btn : allButtons) {
            btn.getStyleClass().remove("nav-button-active");
        }
        if (!activeButton.getStyleClass().contains("nav-button-active")) {
            activeButton.getStyleClass().add("nav-button-active");
        }
    }

}