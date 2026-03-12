package com.portafolio_final.topics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

public class BarajasAleatoridad extends BaseTopicWindow {

    // labels de resultado
    private Label lblResultado;
    private Label lblMensaje;

    @Override
    public String getTitulo() {
        return "Programa #1 — Probabilidad de un evento";
    }

    @Override
    public String getNombreTema() {
        return "*Programa de Baraja Española*";
    }

    @Override
    public String getSubtitulo() {
        return "Eventos";
    }

    @Override
    public String getIcono() {
        return "fas-random";
    }

    @Override
    public String getDefinicion() {
        return "Crea una baraja de cartas Españolas y muestra una carta al alzar, demostrando la aleatoreidad de un evento simple. \n\n"
                + "En este caso la probabilidad de que nos salga n combinación de cartas, ejemplo: 7 de espadas; es una probabilidad \n"
                + "combinada de la cantidad de números (1-10, J, Q, K, A) y los tipos (diamantes, corazones, espadas, tréboles).\n"
                + "La probabilidad de que salga una carta como la del ejemplo es de 1/52 ya que 52 son las cartas totales de la baraja.";
    }

    @Override
    public String getFormula() {
        return "P(A)    =   |A| /   T ";
    }

    @Override
    public String getDescFormula() {
        return "Donde: P(A) es la probabilidad del evento A. \n\n"
                + "|A| Es el resultado favorable del evento A. \n\n"
                + "T es la cantidad total de elementos que tiene el evento A.";
    }

    @Override
    public Node buildDemoSection() {
        VBox demo = new VBox(16);
        demo.setPadding(new Insets(4, 0, 4, 0));

        Label instruccion = new Label(
                "Da click en 'Generar' para ver una carta aleatoriamente de la Baraja Española."
        );
        instruccion.getStyleClass().add("demo-instrucción");
        instruccion.setWrapText(true);

        Button btnGenerar = new Button(" Generar ");
        btnGenerar.getStyleClass().add("btn-calcular");
        btnGenerar.setOnAction(e -> cartaAleatoria());

        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.getStyleClass().add("btn-limpiar");
        btnLimpiar.setOnAction(e -> limpiarCampos());

        HBox filaBotones = new HBox(10);
        filaBotones.setAlignment(Pos.CENTER_LEFT);
        filaBotones.getChildren().addAll(btnGenerar, btnLimpiar);

        VBox resultBox = new VBox(8);
        resultBox.getStyleClass().add("result-box");
        resultBox.setPadding(new Insets(14, 18 ,14, 18));
        resultBox.setVisible(false);

        Label lblTituloRes = new Label("Resultado:");
        lblTituloRes.getStyleClass().add("result-label");

        lblResultado = new Label();
        lblResultado.getStyleClass().add("result-value");

        resultBox.getChildren().addAll(lblTituloRes, lblResultado);

        lblMensaje = new Label();
        lblMensaje.getStyleClass().add("error-text");
        lblMensaje.setWrapText(true);
        lblMensaje.setVisible(false);

        btnGenerar.setUserData(resultBox);

        demo.getChildren().addAll(
                instruccion,
                filaBotones,
                resultBox,
                lblMensaje
        );

        return demo;
    }

    // logica de calculo
    private void cartaAleatoria() {
        ocultarResultados();

        try {
            Object[] numeros = {1,2,3,4,5,6,7,8,9,10,"J","Q","K","A"};
            String[] tipos = {"diamantes", "corazones", "espadas", "tréboles"};

            ArrayList<String> baraja = new ArrayList<>();

            for (Object numero : numeros) {
                for (String tipo: tipos) {
                    baraja.add(numero + " de " + tipo);
                }
            }

            Collections.shuffle(baraja);

            Random random = new Random();
            String carta = baraja.get(random.nextInt(baraja.size()));

            // mostrar resultado
            lblResultado.setText("Carta generada: " + carta);

            VBox parent = (VBox) lblResultado.getParent();
            parent.setVisible(true);
        } catch (Exception e) {
            mostrarError("Ha ocurrido un error inesperado en la generación aleatoria." + e);
        }
    }

    private void limpiarCampos() {
        ocultarResultados();
    }

    private void ocultarResultados() {
        lblMensaje.setVisible(false);
        if (lblResultado.getParent() != null) {
            lblResultado.getParent().setVisible(false);
        }
    }

    private void mostrarError(String mensaje) {
        lblMensaje.setText(mensaje);
        lblMensaje.setVisible(true);
    }
}
