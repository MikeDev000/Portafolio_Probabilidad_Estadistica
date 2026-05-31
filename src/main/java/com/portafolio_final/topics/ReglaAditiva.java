package com.portafolio_final.topics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.ArrayList;
import java.util.List;

public class ReglaAditiva extends BaseTopicWindow {

    private ComboBox<Integer> comboEventos;
    private VBox containerInputs;
    private VBox containerDiagrama;
    private Label lblResultado;
    private List<TextField> inputsProbabilidades = new ArrayList<>();

    @Override
    public String getTitulo() {
        return "Programa #3 — Regla Aditiva";
    }

    @Override
    public String getNombreTema() {
        return "Regla Aditiva";
    }

    @Override
    public String getSubtitulo() {
        return "Probabilidad de la Unión de Eventos";
    }

    @Override
    public String getIcono() {
        return "fas-plus-circle";
    }

    @Override
    public String getDefinicion() {
        return "La regla aditiva permite calcular la probabilidad de que ocurra al menos uno de varios eventos (la unión). "
                + "Se basa en el principio de inclusión-exclusión: sumamos las probabilidades individuales, "
                + "restamos las intersecciones de dos en dos, sumamos las de tres en tres, y así sucesivamente.";
    }

    @Override
    public String getFormula() {
        return "P(A ∪ B) = P(A) + P(B) - P(A ∩ B)\n\n"
                + "P(A ∪ B ∪ C) = P(A) + P(B) + P(C) - [P(A∩B) + P(A∩C) + P(B∩C)] + P(A∩B∩C)";
    }

    @Override
    public String getDescFormula() {
        return "La fórmula se extiende para n eventos alternando signos suma y resta para compensar las áreas de intersección contadas múltiples veces.";
    }

    @Override
    public Node buildDemoSection() {
        VBox demo = new VBox(20);
        demo.setPadding(new Insets(10, 0, 10, 0));

        HBox seleccion = new HBox(15);
        seleccion.setAlignment(Pos.CENTER_LEFT);
        Label lblSel = new Label("Número de eventos:");
        lblSel.getStyleClass().add("input-label");
        
        comboEventos = new ComboBox<>();
        comboEventos.getItems().addAll(2, 3, 4);
        comboEventos.setValue(2);
        comboEventos.getStyleClass().add("demo-combo");
        comboEventos.setOnAction(e -> actualizarInterfaz());

        seleccion.getChildren().addAll(lblSel, comboEventos);

        containerInputs = new VBox(10);
        containerDiagrama = new VBox(15);
        containerDiagrama.setAlignment(Pos.CENTER);
        
        lblResultado = crearLabelResultado();
        
        Button btnCalcular = new Button("Calcular Unión");
        btnCalcular.getStyleClass().add("btn-calcular");
        btnCalcular.setOnAction(e -> calcular());

        Button btnRandom = new Button("Valores Aleatorios");
        btnRandom.getStyleClass().add("btn-limpiar"); // Reutilizando estilo para consistencia
        btnRandom.setOnAction(e -> generarAleatorios());

        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.getStyleClass().add("btn-limpiar");
        btnLimpiar.setOnAction(e -> limpiarCampos());

        HBox filaBotones = new HBox(10);
        filaBotones.setAlignment(Pos.CENTER_LEFT);
        filaBotones.getChildren().addAll(btnCalcular, btnRandom, btnLimpiar);

        actualizarInterfaz();

        demo.getChildren().addAll(seleccion, containerInputs, filaBotones, lblResultado, containerDiagrama);
        return demo;
    }

    private void generarAleatorios() {
        int n = comboEventos.getValue();
        int numRegiones = (int) Math.pow(2, n);
        double[] regiones = new double[numRegiones];
        double suma = 0;

        for (int i = 0; i < numRegiones; i++) {
            regiones[i] = Math.random();
            suma += regiones[i];
        }
        for (int i = 0; i < numRegiones; i++) {
            regiones[i] /= (suma * 1.2); // Garantiza que la unión sea < 1
        }

        int idx = 0;
        // Individuales
        for (int i = 0; i < n; i++) {
            double p = 0;
            for (int r = 0; r < numRegiones; r++) {
                if (((r >> i) & 1) == 1) p += regiones[r];
            }
            inputsProbabilidades.get(idx++).setText(String.format("%.3f", p));
        }

        // Intersecciones de 2
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double p = 0;
                for (int r = 0; r < numRegiones; r++) {
                    if (((r >> i) & 1) == 1 && ((r >> j) & 1) == 1) p += regiones[r];
                }
                inputsProbabilidades.get(idx++).setText(String.format("%.3f", p));
            }
        }

        // Intersecciones de 3
        if (n >= 3) {
            int combinaciones = n == 3 ? 1 : 4;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    for (int k = j + 1; k < n; k++) {
                        double p = 0;
                        for (int r = 0; r < numRegiones; r++) {
                            if (((r >> i) & 1) == 1 && ((r >> j) & 1) == 1 && ((r >> k) & 1) == 1) p += regiones[r];
                        }
                        inputsProbabilidades.get(idx++).setText(String.format("%.3f", p));
                        if (n == 3) break;
                    }
                    if (n == 3) break;
                }
            }
        }

        // Intersección de 4
        if (n == 4) {
            double p = regiones[numRegiones - 1]; // Región donde todos están (1111)
            inputsProbabilidades.get(idx++).setText(String.format("%.3f", p));
        }
        
        calcular();
    }

    private void limpiarCampos() {
        for (TextField tf : inputsProbabilidades) {
            tf.setText("0.0");
        }
        lblResultado.setText("— Ingresa los datos y presiona calcular -");
        lblResultado.setStyle("-fx-text-fill: #8b8fa8;");
    }

    private void actualizarInterfaz() {
        containerInputs.getChildren().clear();
        inputsProbabilidades.clear();
        int n = comboEventos.getValue();

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(10);

        int row = 0;
        // Individuales
        for (int i = 0; i < n; i++) {
            char letra = (char) ('A' + i);
            addInput(grid, "P(" + letra + "):", row++);
        }

        // Intersecciones de 2
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                addInput(grid, "P(" + (char)('A'+i) + " ∩ " + (char)('A'+j) + "):", row++);
            }
        }

        // Intersecciones de 3
        if (n >= 3) {
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    for (int k = j + 1; k < n; k++) {
                        addInput(grid, "P(" + (char)('A'+i) + "∩" + (char)('A'+j) + "∩" + (char)('A'+k) + "):", row++);
                    }
                }
            }
        }

        // Intersección de 4
        if (n == 4) {
            addInput(grid, "P(A ∩ B ∩ C ∩ D):", row++);
        }

        containerInputs.getChildren().add(grid);
        dibujarVenn(n);
    }

    private void addInput(GridPane grid, String label, int row) {
        Label lbl = new Label(label);
        lbl.getStyleClass().add("input-label");
        TextField tf = new TextField("0.0");
        tf.getStyleClass().add("demo-input");
        tf.setPrefWidth(80);
        inputsProbabilidades.add(tf);
        grid.add(lbl, row % 2 == 0 ? 0 : 2, row / 2);
        grid.add(tf, row % 2 == 0 ? 1 : 3, row / 2);
    }

    private void dibujarVenn(int n) {
        containerDiagrama.getChildren().clear();
        Pane canvas = new Pane();
        canvas.setPrefSize(300, 220);
        canvas.setMaxSize(300, 220);

        Color[] colores = {
            Color.web("#6c63ff", 0.4),
            Color.web("#ff6b6b", 0.4),
            Color.web("#4ecdc4", 0.4),
            Color.web("#ffe66d", 0.4)
        };

        if (n == 2) {
            drawCircle(canvas, 110, 110, 70, colores[0], "A");
            drawCircle(canvas, 190, 110, 70, colores[1], "B");
        } else if (n == 3) {
            drawCircle(canvas, 150, 80, 70, colores[0], "A");
            drawCircle(canvas, 100, 150, 70, colores[1], "B");
            drawCircle(canvas, 200, 150, 70, colores[2], "C");
        } else if (n == 4) {
            drawCircle(canvas, 110, 80, 65, colores[0], "A");
            drawCircle(canvas, 190, 80, 65, colores[1], "B");
            drawCircle(canvas, 110, 140, 65, colores[2], "C");
            drawCircle(canvas, 190, 140, 65, colores[3], "D");
        }

        Label info = new Label("Representación visual (Diagrama de Venn)");
        info.setStyle("-fx-font-size: 11px; -fx-text-fill: #8b8fa8;");
        containerDiagrama.getChildren().addAll(canvas, info);
    }

    private void drawCircle(Pane p, double x, double y, double r, Color c, String text) {
        Circle circ = new Circle(x, y, r, c);
        circ.setStroke(c.darker());
        circ.setStrokeWidth(2);
        
        Label lbl = new Label(text);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 14));
        lbl.setLayoutX(x - 5);
        lbl.setLayoutY(y - 10);
        
        p.getChildren().addAll(circ, lbl);
    }

    private void calcular() {
        try {
            int n = comboEventos.getValue();
            double total = 0;
            int idx = 0;

            // Sumar individuales
            for (int i = 0; i < n; i++) {
                total += Double.parseDouble(inputsProbabilidades.get(idx++).getText());
            }

            // Restar intersecciones de 2
            int inter2 = (n * (n - 1)) / 2;
            for (int i = 0; i < inter2; i++) {
                total -= Double.parseDouble(inputsProbabilidades.get(idx++).getText());
            }

            // Sumar intersecciones de 3
            if (n >= 3) {
                int inter3 = n == 3 ? 1 : 4;
                for (int i = 0; i < inter3; i++) {
                    total += Double.parseDouble(inputsProbabilidades.get(idx++).getText());
                }
            }

            // Restar intersección de 4
            if (n == 4) {
                total -= Double.parseDouble(inputsProbabilidades.get(idx++).getText());
            }

            if (total < 0 || total > 1.0000001) {
                lblResultado.setText("P(Unión) = " + String.format("%.4f", total) + " (Aviso: Supera límites 0-1)");
                lblResultado.setStyle("-fx-text-fill: #ff6b6b;");
            } else {
                lblResultado.setText("P(Unión) = " + String.format("%.4f", Math.min(1.0, total)));
                lblResultado.setStyle("-fx-text-fill: #6c63ff; -fx-font-weight: bold;");
            }

        } catch (Exception ex) {
            lblResultado.setText("Error: Ingresa valores numéricos válidos.");
            lblResultado.setStyle("-fx-text-fill: #ff6b6b;");
        }
    }
}
