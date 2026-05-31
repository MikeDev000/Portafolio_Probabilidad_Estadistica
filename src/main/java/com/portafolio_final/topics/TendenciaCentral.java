package com.portafolio_final.topics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TendenciaCentral extends BaseTopicWindow {

    private TextField tfDatos;
    private Label lblResultado;

    @Override
    public String getTitulo() {
        return "Programa #9 — Medidas de Tendencia Central";
    }

    @Override
    public String getNombreTema() {
        return "Tendencia Central";
    }

    @Override
    public String getSubtitulo() {
        return "Media, Mediana y Moda";
    }

    @Override
    public String getIcono() {
        return "fas-chart-line";
    }

    @Override
    public String getDefinicion() {
        return "Las medidas de tendencia central son estadísticas que describen el centro o punto medio de un conjunto de datos.\n\n"
                + "• Media: El promedio aritmético.\n"
                + "• Mediana: El valor central cuando los datos están ordenados.\n"
                + "• Moda: El valor que más se repite.";
    }

    @Override
    public String getFormula() {
        return "Media (x̄) = (Σxi) / n\n"
                + "Mediana = Valor en la posición (n+1)/2 si es impar, o promedio de los centrales si es par.\n"
                + "Moda = Frecuencia máxima(xi)";
    }

    @Override
    public String getDescFormula() {
        return "Para el cálculo correcto, los valores atípicos afectan mucho la media, pero no la mediana.";
    }

    @Override
    public Node buildDemoSection() {
        VBox demo = new VBox(15);
        demo.setPadding(new Insets(10, 0, 10, 0));

        Label desc = new Label("Ingresa un conjunto de números separados por comas:");
        desc.getStyleClass().add("demo-instruction");

        tfDatos = new TextField("12, 15, 12, 18, 20, 22, 12, 15");
        tfDatos.getStyleClass().add("demo-input");
        tfDatos.setPromptText("Ej. 5, 8, 12, 5, 3");

        Button btnCalcular = new Button("Calcular Medidas");
        btnCalcular.getStyleClass().add("btn-calcular");
        btnCalcular.setOnAction(e -> calcular());

        lblResultado = crearLabelResultado();

        demo.getChildren().addAll(desc, tfDatos, btnCalcular, lblResultado);
        return demo;
    }

    private void calcular() {
        try {
            String[] parts = tfDatos.getText().split(",");
            double[] datos = new double[parts.length];
            for (int i = 0; i < parts.length; i++) {
                datos[i] = Double.parseDouble(parts[i].trim());
            }

            if (datos.length == 0) return;

            // Media
            double suma = 0;
            for (double d : datos) suma += d;
            double media = suma / datos.length;

            // Mediana
            Arrays.sort(datos);
            double mediana;
            int n = datos.length;
            if (n % 2 == 0) {
                mediana = (datos[n/2 - 1] + datos[n/2]) / 2.0;
            } else {
                mediana = datos[n/2];
            }

            // Moda
            Map<Double, Long> frecuencias = Arrays.stream(datos).boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            
            long maxFreq = Collections.max(frecuencias.values());
            String modas = frecuencias.entrySet().stream()
                .filter(e -> e.getValue() == maxFreq)
                .map(e -> String.valueOf(e.getKey()))
                .collect(Collectors.joining(", "));

            String resultado = String.format("Datos ordenados: %s\n\n"
                    + "• Media (Promedio): %.4f\n"
                    + "• Mediana (Centro): %.4f\n"
                    + "• Moda (Más frecuente): %s (Aparece %d veces)",
                    Arrays.toString(datos), media, mediana, modas, maxFreq);

            lblResultado.setText(resultado);
            lblResultado.setStyle("-fx-text-fill: #6c63ff; -fx-font-weight: bold;");

        } catch (Exception e) {
            lblResultado.setText("Formato inválido. Usa solo números separados por comas.");
            lblResultado.setStyle("-fx-text-fill: #ff6b6b;");
        }
    }
}