package com.portafolio_final.topics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class JuegoCartasTres extends BaseTopicWindow {

    private ComboBox<String> comboObjetivo;
    private Label lblResultado;

    @Override
    public String getTitulo() {
        return "Programa #6 — Juego de Cartas (3 Eventos)";
    }

    @Override
    public String getNombreTema() {
        return "Probabilidad en 3 Eventos";
    }

    @Override
    public String getSubtitulo() {
        return "Regla multiplicativa extendida para juegos de azar";
    }

    @Override
    public String getIcono() {
        return "fas-layer-group";
    }

    @Override
    public String getDefinicion() {
        return "En un juego secuencial (como extraer 3 cartas sin reemplazo), la regla multiplicativa "
                + "se extiende para múltiples eventos dependientes en cascada.";
    }

    @Override
    public String getFormula() {
        return "P(A ∩ B ∩ C) = P(A) * P(B|A) * P(C|A ∩ B)";
    }

    @Override
    public String getDescFormula() {
        return "Cada extracción modifica el espacio muestral y los casos favorables disponibles para las siguientes extracciones.";
    }

    @Override
    public Node buildDemoSection() {
        VBox demo = new VBox(15);
        demo.setPadding(new Insets(10, 0, 10, 0));

        Label desc = new Label("Mazo de Poker de 52 cartas. Se extraen 3 cartas consecutivas sin reemplazo.");
        desc.getStyleClass().add("demo-instruction");

        HBox control = new HBox(15);
        control.setAlignment(Pos.CENTER_LEFT);

        comboObjetivo = new ComboBox<>();
        comboObjetivo.getItems().addAll("Sacar 3 Ases", "Sacar 3 Corazones", "Sacar 3 Figuras (J,Q,K)");
        comboObjetivo.setValue("Sacar 3 Ases");
        comboObjetivo.getStyleClass().add("demo-combo");

        Button btnCalcular = new Button("Calcular Probabilidad");
        btnCalcular.getStyleClass().add("btn-calcular");
        btnCalcular.setOnAction(e -> calcular());

        control.getChildren().addAll(comboObjetivo, btnCalcular);

        lblResultado = crearLabelResultado();

        demo.getChildren().addAll(desc, control, lblResultado);
        return demo;
    }

    private void calcular() {
        int totales = 52;
        int favorables = 0;
        String seleccion = comboObjetivo.getValue();

        switch (seleccion) {
            case "Sacar 3 Ases": favorables = 4; break;
            case "Sacar 3 Corazones": favorables = 13; break;
            case "Sacar 3 Figuras (J,Q,K)": favorables = 12; break;
        }

        double p1 = (double) favorables / totales;
        double p2 = (double) (favorables - 1) / (totales - 1);
        double p3 = (double) (favorables - 2) / (totales - 2);

        double probFinal = p1 * p2 * p3;
        double porcentaje = probFinal * 100;

        String formulaDetalle = String.format("P(1ra) * P(2da|1ra) * P(3ra|1,2) = (%d/%d) * (%d/%d) * (%d/%d)",
                favorables, totales, favorables - 1, totales - 1, favorables - 2, totales - 2);

        lblResultado.setText(formulaDetalle + String.format("\nProbabilidad: %.6f (%.4f%%)", probFinal, porcentaje));
        lblResultado.setStyle("-fx-text-fill: #6c63ff; -fx-font-weight: bold;");
    }
}