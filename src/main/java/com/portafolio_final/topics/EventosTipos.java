package com.portafolio_final.topics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.List;

public class EventosTipos extends BaseTopicWindow {

    private ComboBox<String> comboEventoA;
    private ComboBox<String> comboEventoB;
    private Label lblResultado;

    @Override
    public String getTitulo() {
        return "Programa #2 — Eventos Compuestos, Complementarios y Disjuntos";
    }

    @Override
    public String getNombreTema() {
        return "Tipos de Eventos";
    }

    @Override
    public String getSubtitulo() {
        return "Relaciones entre subconjuntos del espacio muestral";
    }

    @Override
    public String getIcono() {
        return "fas-project-diagram";
    }

    @Override
    public String getDefinicion() {
        return "Un evento compuesto consta de dos o más eventos simples (intersección o unión). "
                + "Un evento complementario contiene todos los resultados del espacio muestral que NO están en el evento original. "
                + "Dos eventos son disjuntos (o mutuamente excluyentes) si no pueden ocurrir al mismo tiempo (intersección nula).";
    }

    @Override
    public String getFormula() {
        return "Complementario: P(A') = 1 - P(A)\n"
                + "Compuesto (Intersección): P(A ∩ B)\n"
                + "Disjuntos: P(A ∩ B) = 0  =>  P(A ∪ B) = P(A) + P(B)";
    }

    @Override
    public String getDescFormula() {
        return "El espacio muestral analizado es el lanzamiento de un dado estándar de 6 caras: Ω = {1, 2, 3, 4, 5, 6}.";
    }

    @Override
    public Node buildDemoSection() {
        VBox demo = new VBox(15);
        demo.setPadding(new Insets(10, 0, 10, 0));

        HBox selectores = new HBox(15);
        selectores.setAlignment(Pos.CENTER_LEFT);

        comboEventoA = new ComboBox<>();
        comboEventoA.getItems().addAll("Número Par", "Número Impar", "Mayor que 4", "Menor que 3");
        comboEventoA.setValue("Número Par");
        comboEventoA.getStyleClass().add("demo-combo");

        comboEventoB = new ComboBox<>();
        comboEventoB.getItems().addAll("Número Par", "Número Impar", "Mayor que 4", "Menor que 3");
        comboEventoB.setValue("Mayor que 4");
        comboEventoB.getStyleClass().add("demo-combo");

        selectores.getChildren().addAll(
                new Label("Evento A:"), comboEventoA,
                new Label("Evento B:"), comboEventoB
        );

        HBox botones = new HBox(10);
        botones.setAlignment(Pos.CENTER_LEFT);

        Button btnComplemento = new Button("Complementario de A");
        btnComplemento.setOnAction(e -> calcularComplementario());
        btnComplemento.getStyleClass().add("btn-calcular");

        Button btnCompuesto = new Button("Compuesto (A ∩ B)");
        btnCompuesto.setOnAction(e -> calcularCompuesto());
        btnCompuesto.getStyleClass().add("btn-calcular");

        Button btnDisjunto = new Button("Verificar Disjuntos");
        btnDisjunto.setOnAction(e -> verificarDisjuntos());
        btnDisjunto.getStyleClass().add("btn-calcular");

        botones.getChildren().addAll(btnComplemento, btnCompuesto, btnDisjunto);

        lblResultado = crearLabelResultado();

        demo.getChildren().addAll(selectores, botones, lblResultado);
        return demo;
    }

    private List<Integer> obtenerConjunto(String evento) {
        return switch (evento) {
            case "Número Par" -> java.util.List.of(2, 4, 6);
            case "Número Impar" -> java.util.List.of(1, 3, 5);
            case "Mayor que 4" -> java.util.List.of(5, 6);
            case "Menor que 3" -> java.util.List.of(1, 2);
            default -> new java.util.ArrayList<>();
        };
    }

    private void calcularComplementario() {
        List<Integer> a = obtenerConjunto(comboEventoA.getValue());
        List<Integer> comp = new java.util.ArrayList<>(java.util.List.of(1, 2, 3, 4, 5, 6));
        comp.removeAll(a);
        
        double prob = comp.size() / 6.0;
        lblResultado.setText(String.format("A' = %s\nP(A') = %d/6 = %.3f", comp, comp.size(), prob));
        lblResultado.setStyle("-fx-text-fill: #6c63ff;");
    }

    private void calcularCompuesto() {
        List<Integer> a = obtenerConjunto(comboEventoA.getValue());
        List<Integer> b = obtenerConjunto(comboEventoB.getValue());
        
        List<Integer> interseccion = new java.util.ArrayList<>(a);
        interseccion.retainAll(b);
        
        double prob = interseccion.size() / 6.0;
        lblResultado.setText(String.format("A ∩ B = %s\nP(A ∩ B) = %d/6 = %.3f", interseccion, interseccion.size(), prob));
        lblResultado.setStyle("-fx-text-fill: #6c63ff;");
    }

    private void verificarDisjuntos() {
        List<Integer> a = obtenerConjunto(comboEventoA.getValue());
        List<Integer> b = obtenerConjunto(comboEventoB.getValue());
        
        List<Integer> interseccion = new java.util.ArrayList<>(a);
        interseccion.retainAll(b);
        
        if (interseccion.isEmpty()) {
            lblResultado.setText("A y B SON DISJUNTOS (Mutuamente excluyentes).\nNo hay elementos en común. P(A ∩ B) = 0.");
            lblResultado.setStyle("-fx-text-fill: #4ecdc4; -fx-font-weight: bold;");
        } else {
            lblResultado.setText("A y B NO SON DISJUNTOS.\nElementos en común: " + interseccion);
            lblResultado.setStyle("-fx-text-fill: #ff6b6b; -fx-font-weight: bold;");
        }
    }
}