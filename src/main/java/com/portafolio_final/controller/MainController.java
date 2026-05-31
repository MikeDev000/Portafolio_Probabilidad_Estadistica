package com.portafolio_final.controller;

import com.portafolio_final.MainApp;
import com.portafolio_final.topics.BarajasAleatoridad;
import com.portafolio_final.topics.EjemploTema;
import com.portafolio_final.topics.ReglaAditiva;
import com.portafolio_final.topics.EventosTipos;
import com.portafolio_final.topics.ProbCondicional;
import com.portafolio_final.topics.ReglaMultiplicativa;
import com.portafolio_final.topics.JuegoCartasTres;
import com.portafolio_final.topics.ProbabilidadTotal;
import com.portafolio_final.topics.TeoremaBayes;
import com.portafolio_final.topics.TendenciaCentral;
import com.portafolio_final.topics.Dispersion;
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
    @FXML private Button btnTema4;
    @FXML private Button btnTema5;
    @FXML private Button btnTema6;
    @FXML private Button btnTema7;
    @FXML private Button btnTema8;
    @FXML private Button btnTema9;
    @FXML private Button btnTema10;
    @FXML private Button btnTema11;

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
        lblTotalTemas.setText("11");

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

     @FXML
     private void abrirTema4() { setActiveButton(btnTema4); new EventosTipos().show(); }
     @FXML
     private void abrirTema5() { setActiveButton(btnTema5); new ProbCondicional().show(); }
     @FXML
     private void abrirTema6() { setActiveButton(btnTema6); new ReglaMultiplicativa().show(); }
     @FXML
     private void abrirTema7() { setActiveButton(btnTema7); new JuegoCartasTres().show(); }
     @FXML
     private void abrirTema8() { setActiveButton(btnTema8); new ProbabilidadTotal().show(); }
     @FXML
     private void abrirTema9() { setActiveButton(btnTema9); new TeoremaBayes().show(); }
     @FXML
     private void abrirTema10() { setActiveButton(btnTema10); new TendenciaCentral().show(); }
     @FXML
     private void abrirTema11() { setActiveButton(btnTema11); new Dispersion().show(); }

    // @param activeButton Botón que debe marcarse como activo

    private void setActiveButton(Button activeButton) {
        Button[] allButtons = { btnInicio, btnTema1, btnTema2, btnTema3, btnTema4, btnTema5, btnTema6, btnTema7, btnTema8, btnTema9, btnTema10, btnTema11};

        for (Button btn : allButtons) {
            btn.getStyleClass().remove("nav-button-active");
        }
        if (!activeButton.getStyleClass().contains("nav-button-active")) {
            activeButton.getStyleClass().add("nav-button-active");
        }
    }
}