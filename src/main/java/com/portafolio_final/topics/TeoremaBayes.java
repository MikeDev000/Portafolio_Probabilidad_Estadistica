package com.portafolio_final.topics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class TeoremaBayes extends BaseTopicWindow {

    private TextField tfPM1, tfPM2, tfPM3;
    private TextField tfPD1, tfPD2, tfPD3;
    private ComboBox<String> comboMaquina;
    private Label lblResultado;

    @Override
    public String getTitulo() {
        return "Programa #8 — Teorema de Bayes";
    }

    @Override
    public String getNombreTema() {
        return "Teorema de Bayes";
    }

    @Override
    public String getSubtitulo() {
        return "Cálculo de probabilidad a posteriori";
    }

    @Override
    public String getIcono() {
        return "fas-balance-scale";
    }

    @Override
    public String getDefinicion() {
        return "El Teorema de Bayes permite calcular la probabilidad de una causa (a posteriori) "
                + "dado que ya observamos un efecto o resultado. Se usa para revertir la condicionalidad.";
    }

    @Override
    public String getFormula() {
        return "P(Mi|D) = [ P(Mi) * P(D|Mi) ] / P(D)\nDonde P(D) es la Probabilidad Total.";
    }

    @Override
    public String getDescFormula() {
        return "Calculamos la probabilidad de que una pieza provenga de la Máquina i dado que sabemos que es defectuosa (D).";
    }

    @Override
    public Node buildDemoSection() {
        VBox demo = new VBox(15);
        demo.setPadding(new Insets(10, 0, 10, 0));

        Label desc = new Label("Utilizando el caso de la fábrica con 3 máquinas. Se ha encontrado una pieza defectuosa (D).");
        desc.getStyleClass().add("demo-instruction");

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(10);
        
        grid.add(new Label("Máquina"), 0, 0);
        grid.add(new Label("% Producción (M)"), 1, 0);
        grid.add(new Label("% Defectos (D|M)"), 2, 0);

        grid.add(new Label("Máquina 1"), 0, 1);
        tfPM1 = crearInput("40"); tfPD1 = crearInput("2");
        grid.add(tfPM1, 1, 1); grid.add(tfPD1, 2, 1);

        grid.add(new Label("Máquina 2"), 0, 2);
        tfPM2 = crearInput("35"); tfPD2 = crearInput("4");
        grid.add(tfPM2, 1, 2); grid.add(tfPD2, 2, 2);

        grid.add(new Label("Máquina 3"), 0, 3);
        tfPM3 = crearInput("25"); tfPD3 = crearInput("1");
        grid.add(tfPM3, 1, 3); grid.add(tfPD3, 2, 3);

        HBox accion = new HBox(15);
        accion.setAlignment(Pos.CENTER_LEFT);
        
        comboMaquina = new ComboBox<>();
        comboMaquina.getItems().addAll("Máquina 1", "Máquina 2", "Máquina 3");
        comboMaquina.setValue("Máquina 1");
        comboMaquina.getStyleClass().add("demo-combo");

        Button btnCalcular = new Button("Calcular P(Mi | D)");
        btnCalcular.getStyleClass().add("btn-calcular");
        btnCalcular.setOnAction(e -> calcular());

        accion.getChildren().addAll(new Label("¿De qué máquina proviene?"), comboMaquina, btnCalcular);

        lblResultado = crearLabelResultado();

        demo.getChildren().addAll(desc, grid, accion, lblResultado);
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

            int index = comboMaquina.getSelectionModel().getSelectedIndex();
            double pMi = index == 0 ? pM1 : (index == 1 ? pM2 : pM3);
            double pDi = index == 0 ? pD1 : (index == 1 ? pD2 : pD3);

            double bayes = (pMi * pDi) / probTotal;

            lblResultado.setText(String.format("P(D) = %.4f\nP(%s|D) = (%.2f * %.2f) / %.4f = %.4f (%.2f%%)", 
                    probTotal, comboMaquina.getValue(), pMi, pDi, probTotal, bayes, bayes * 100));
            lblResultado.setStyle("-fx-text-fill: #6c63ff; -fx-font-weight: bold;");

        } catch (NumberFormatException e) {
            lblResultado.setText("Ingresa solo valores numéricos.");
            lblResultado.setStyle("-fx-text-fill: #ff6b6b;");
        }
    }
}