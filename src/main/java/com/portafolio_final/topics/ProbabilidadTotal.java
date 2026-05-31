package com.portafolio_final.topics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ProbabilidadTotal extends BaseTopicWindow {

    private TextField tfPM1, tfPM2, tfPM3;
    private TextField tfPD1, tfPD2, tfPD3;
    private Label lblResultado;

    @Override
    public String getTitulo() {
        return "Programa #7 — Teorema de Probabilidad Total";
    }

    @Override
    public String getNombreTema() {
        return "Probabilidad Total";
    }

    @Override
    public String getSubtitulo() {
        return "Cálculo de probabilidad considerando múltiples escenarios";
    }

    @Override
    public String getIcono() {
        return "fas-sitemap";
    }

    @Override
    public String getDefinicion() {
        return "El teorema de probabilidad total permite calcular la probabilidad de un evento que puede ocurrir "
                + "bajo distintas condiciones mutuamente excluyentes y exhaustivas. Se suma el producto de la probabilidad "
                + "de cada escenario por la probabilidad del evento dado ese escenario.";
    }

    @Override
    public String getFormula() {
        return "P(D) = P(M1)*P(D|M1) + P(M2)*P(D|M2) + ... + P(Mn)*P(D|Mn)";
    }

    @Override
    public String getDescFormula() {
        return "Donde M son las distintas máquinas/escenarios y D es el evento de interés (ej. pieza defectuosa).";
    }

    @Override
    public Node buildDemoSection() {
        VBox demo = new VBox(15);
        demo.setPadding(new Insets(10, 0, 10, 0));

        Label desc = new Label("Una fábrica tiene 3 máquinas. Ingresa la % de producción de cada una y su % de defectos.");
        desc.getStyleClass().add("demo-instruction");

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(10);
        
        grid.add(new Label("Máquina"), 0, 0);
        grid.add(new Label("% Producción (M)"), 1, 0);
        grid.add(new Label("% Defectos (D|M)"), 2, 0);

        grid.add(new Label("Máquina 1"), 0, 1);
        tfPM1 = crearInput("40");
        tfPD1 = crearInput("2");
        grid.add(tfPM1, 1, 1);
        grid.add(tfPD1, 2, 1);

        grid.add(new Label("Máquina 2"), 0, 2);
        tfPM2 = crearInput("35");
        tfPD2 = crearInput("4");
        grid.add(tfPM2, 1, 2);
        grid.add(tfPD2, 2, 2);

        grid.add(new Label("Máquina 3"), 0, 3);
        tfPM3 = crearInput("25");
        tfPD3 = crearInput("1");
        grid.add(tfPM3, 1, 3);
        grid.add(tfPD3, 2, 3);

        Button btnCalcular = new Button("Calcular Probabilidad Total de Defecto P(D)");
        btnCalcular.getStyleClass().add("btn-calcular");
        btnCalcular.setOnAction(e -> calcular());

        lblResultado = crearLabelResultado();

        demo.getChildren().addAll(desc, grid, btnCalcular, lblResultado);
        return demo;
    }

    private TextField crearInput(String def) {
        TextField tf = new TextField(def);
        tf.setPrefWidth(80);
        tf.getStyleClass().add("demo-input");
        return tf;
    }

    private void calcular() {
        try {
            double pM1 = Double.parseDouble(tfPM1.getText()) / 100;
            double pM2 = Double.parseDouble(tfPM2.getText()) / 100;
            double pM3 = Double.parseDouble(tfPM3.getText()) / 100;

            if (Math.abs((pM1 + pM2 + pM3) - 1.0) > 0.01) {
                lblResultado.setText("Error: La suma de la producción debe ser exactamente 100%.");
                lblResultado.setStyle("-fx-text-fill: #ff6b6b;");
                return;
            }

            double pD1 = Double.parseDouble(tfPD1.getText()) / 100;
            double pD2 = Double.parseDouble(tfPD2.getText()) / 100;
            double pD3 = Double.parseDouble(tfPD3.getText()) / 100;

            double probTotal = (pM1 * pD1) + (pM2 * pD2) + (pM3 * pD3);

            lblResultado.setText(String.format("P(D) = (%.2f * %.2f) + (%.2f * %.2f) + (%.2f * %.2f)\nProbabilidad Total P(D): %.4f (%.2f%%)", 
                    pM1, pD1, pM2, pD2, pM3, pD3, probTotal, probTotal * 100));
            lblResultado.setStyle("-fx-text-fill: #6c63ff; -fx-font-weight: bold;");

        } catch (NumberFormatException e) {
            lblResultado.setText("Ingresa solo valores numéricos.");
            lblResultado.setStyle("-fx-text-fill: #ff6b6b;");
        }
    }
}