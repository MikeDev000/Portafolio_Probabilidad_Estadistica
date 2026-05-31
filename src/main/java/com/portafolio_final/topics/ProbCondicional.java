package com.portafolio_final.topics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ProbCondicional extends BaseTopicWindow {

    private TextField tfHombresFuman;
    private TextField tfHombresNoFuman;
    private TextField tfMujeresFuman;
    private TextField tfMujeresNoFuman;
    private Label lblResultado;

    @Override
    public String getTitulo() {
        return "Programa #4 — Probabilidad Condicional";
    }

    @Override
    public String getNombreTema() {
        return "Probabilidad Condicional";
    }

    @Override
    public String getSubtitulo() {
        return "P(A|B) - Probabilidad dado que ya ocurrió un evento";
    }

    @Override
    public String getIcono() {
        return "fas-filter";
    }

    @Override
    public String getDefinicion() {
        return "La probabilidad condicional es la medida de la probabilidad de un evento A dado que "
                + "ha ocurrido un evento B. Restringe el espacio muestral a solo los casos donde B es verdadero.";
    }

    @Override
    public String getFormula() {
        return "P(A|B) = P(A ∩ B) / P(B)";
    }

    @Override
    public String getDescFormula() {
        return "Donde P(B) > 0. En una tabla de contingencia, se calcula dividiendo la celda específica entre el total de la fila o columna condicionante.";
    }

    @Override
    public Node buildDemoSection() {
        VBox demo = new VBox(15);
        demo.setPadding(new Insets(10, 0, 10, 0));

        Label desc = new Label("Ingresa los datos de la encuesta poblacional (Tabla de Contingencia):");
        desc.getStyleClass().add("demo-instruction");

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER_LEFT);

        grid.add(new Label("Fuman"), 1, 0);
        grid.add(new Label("No Fuman"), 2, 0);

        grid.add(new Label("Hombres"), 0, 1);
        tfHombresFuman = crearInput("30");
        tfHombresNoFuman = crearInput("70");
        grid.add(tfHombresFuman, 1, 1);
        grid.add(tfHombresNoFuman, 2, 1);

        grid.add(new Label("Mujeres"), 0, 2);
        tfMujeresFuman = crearInput("20");
        tfMujeresNoFuman = crearInput("80");
        grid.add(tfMujeresFuman, 1, 2);
        grid.add(tfMujeresNoFuman, 2, 2);

        HBox botones = new HBox(10);
        botones.setAlignment(Pos.CENTER_LEFT);

        Button btnFumaDadoHombre = new Button("P(Fuma | Hombre)");
        btnFumaDadoHombre.setOnAction(e -> calcularCondicional(tfHombresFuman, tfHombresFuman, tfHombresNoFuman, "Hombre", "Fuma"));
        btnFumaDadoHombre.getStyleClass().add("btn-calcular");

        Button btnMujerDadoNoFuma = new Button("P(Mujer | No Fuma)");
        btnMujerDadoNoFuma.setOnAction(e -> calcularCondicional(tfMujeresNoFuman, tfHombresNoFuman, tfMujeresNoFuman, "No Fuma", "Mujer"));
        btnMujerDadoNoFuma.getStyleClass().add("btn-calcular");

        botones.getChildren().addAll(btnFumaDadoHombre, btnMujerDadoNoFuma);

        lblResultado = crearLabelResultado();

        demo.getChildren().addAll(desc, grid, botones, lblResultado);
        return demo;
    }

    private TextField crearInput(String textoDefecto) {
        TextField tf = new TextField(textoDefecto);
        tf.setPrefWidth(80);
        tf.getStyleClass().add("demo-input");
        return tf;
    }

    private void calcularCondicional(TextField tfFavorables, TextField tfTotal1, TextField tfTotal2, String condicional, String objetivo) {
        try {
            double fav = Double.parseDouble(tfFavorables.getText());
            double total = Double.parseDouble(tfTotal1.getText()) + Double.parseDouble(tfTotal2.getText());

            if (total == 0) {
                lblResultado.setText("Error: El total del evento condicionante no puede ser cero.");
                lblResultado.setStyle("-fx-text-fill: #ff6b6b;");
                return;
            }

            double prob = fav / total;
            lblResultado.setText(String.format("P(%s | %s) = %.0f / %.0f = %.4f", objetivo, condicional, fav, total, prob));
            lblResultado.setStyle("-fx-text-fill: #6c63ff; -fx-font-weight: bold;");

        } catch (NumberFormatException e) {
            lblResultado.setText("Ingresa solo valores numéricos.");
            lblResultado.setStyle("-fx-text-fill: #ff6b6b;");
        }
    }
}