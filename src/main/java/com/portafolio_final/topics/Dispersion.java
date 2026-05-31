package com.portafolio_final.topics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Dispersion extends BaseTopicWindow {

    private TextField tfDatos;
    private RadioButton rbPoblacional;
    private RadioButton rbMuestral;
    private Label lblResultado;

    @Override
    public String getTitulo() {
        return "Programa #10 — Medidas de Dispersión";
    }

    @Override
    public String getNombreTema() {
        return "Dispersión";
    }

    @Override
    public String getSubtitulo() {
        return "Varianza y Desviación Estándar";
    }

    @Override
    public String getIcono() {
        return "fas-chart-area";
    }

    @Override
    public String getDefinicion() {
        return "Las medidas de dispersión indican qué tan alejados o dispersos están los datos respecto al centro (media).\n\n"
                + "• Varianza: Promedio de las diferencias al cuadrado respecto a la media.\n"
                + "• Desviación Estándar: Raíz cuadrada de la varianza. Regresa a las unidades originales.";
    }

    @Override
    public String getFormula() {
        return "Varianza Muestral (s²) = Σ(xi - x̄)² / (n - 1)\n"
                + "Varianza Poblacional (σ²) = Σ(xi - μ)² / n\n\n"
                + "Desviación Estándar = √Varianza";
    }

    @Override
    public String getDescFormula() {
        return "La muestra usa (n-1) como corrección de Bessel para dar un estimador insesgado de la población.";
    }

    @Override
    public Node buildDemoSection() {
        VBox demo = new VBox(15);
        demo.setPadding(new Insets(10, 0, 10, 0));

        Label desc = new Label("Ingresa los datos separados por comas y elige el tipo de conjunto:");
        desc.getStyleClass().add("demo-instruction");

        tfDatos = new TextField("12, 15, 18, 20, 22");
        tfDatos.getStyleClass().add("demo-input");

        ToggleGroup group = new ToggleGroup();
        rbMuestral = new RadioButton("Muestra (n-1)");
        rbMuestral.setToggleGroup(group);
        rbMuestral.setSelected(true);
        rbPoblacional = new RadioButton("Población (n)");
        rbPoblacional.setToggleGroup(group);

        HBox radios = new HBox(15);
        radios.getChildren().addAll(rbMuestral, rbPoblacional);

        Button btnCalcular = new Button("Calcular Dispersión");
        btnCalcular.getStyleClass().add("btn-calcular");
        btnCalcular.setOnAction(e -> calcular());

        lblResultado = crearLabelResultado();

        demo.getChildren().addAll(desc, tfDatos, radios, btnCalcular, lblResultado);
        return demo;
    }

    private void calcular() {
        try {
            String[] parts = tfDatos.getText().split(",");
            double[] datos = new double[parts.length];
            double suma = 0;
            for (int i = 0; i < parts.length; i++) {
                datos[i] = Double.parseDouble(parts[i].trim());
                suma += datos[i];
            }

            int n = datos.length;
            if (n < 2 && rbMuestral.isSelected()) {
                lblResultado.setText("Se requieren al menos 2 datos para varianza muestral.");
                lblResultado.setStyle("-fx-text-fill: #ff6b6b;");
                return;
            }

            double media = suma / n;
            double sumaCuadrados = 0;

            for (double x : datos) {
                sumaCuadrados += Math.pow(x - media, 2);
            }

            double varianza;
            if (rbMuestral.isSelected()) {
                varianza = sumaCuadrados / (n - 1);
            } else {
                varianza = sumaCuadrados / n;
            }

            double desvEstandar = Math.sqrt(varianza);

            String resultado = String.format("N = %d   |   Media = %.4f\n\n"
                    + "• Varianza (%s): %.4f\n"
                    + "• Desviación Estándar (%s): %.4f",
                    n, media, 
                    rbMuestral.isSelected() ? "s²" : "σ²", varianza,
                    rbMuestral.isSelected() ? "s" : "σ", desvEstandar);

            lblResultado.setText(resultado);
            lblResultado.setStyle("-fx-text-fill: #6c63ff; -fx-font-weight: bold;");

        } catch (Exception e) {
            lblResultado.setText("Formato inválido. Usa solo números separados por comas.");
            lblResultado.setStyle("-fx-text-fill: #ff6b6b;");
        }
    }
}