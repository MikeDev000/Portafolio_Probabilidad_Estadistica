package com.portafolio_final.topics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ReglaMultiplicativa extends BaseTopicWindow {

    private TextField tfBolasRojas;
    private TextField tfBolasAzules;
    private RadioButton rbConReemplazo;
    private RadioButton rbSinReemplazo;
    private Label lblResultado;

    @Override
    public String getTitulo() {
        return "Programa #5 — Regla Multiplicativa";
    }

    @Override
    public String getNombreTema() {
        return "Regla Multiplicativa";
    }

    @Override
    public String getSubtitulo() {
        return "Extracción de múltiples elementos (Eventos dependientes e independientes)";
    }

    @Override
    public String getIcono() {
        return "fas-times";
    }

    @Override
    public String getDefinicion() {
        return "La regla multiplicativa se usa para calcular la probabilidad de la intersección de dos eventos (que ocurran ambos). "
                + "Depende de si los eventos son independientes (con reemplazo) o dependientes (sin reemplazo).";
    }

    @Override
    public String getFormula() {
        return "Eventos Independientes: P(A ∩ B) = P(A) * P(B)\n"
                + "Eventos Dependientes: P(A ∩ B) = P(A) * P(B|A)";
    }

    @Override
    public String getDescFormula() {
        return "P(B|A) refleja el cambio en las probabilidades de la urna tras haber extraído el primer elemento.";
    }

    @Override
    public Node buildDemoSection() {
        VBox demo = new VBox(15);
        demo.setPadding(new Insets(10, 0, 10, 0));

        Label desc = new Label("Urna con bolas rojas y azules. Se extraerán 2 bolas consecutivas.");
        desc.getStyleClass().add("demo-instruction");

        HBox inputs = new HBox(15);
        inputs.setAlignment(Pos.CENTER_LEFT);
        
        tfBolasRojas = new TextField("5");
        tfBolasRojas.setPrefWidth(60);
        tfBolasRojas.getStyleClass().add("demo-input");
        
        tfBolasAzules = new TextField("3");
        tfBolasAzules.setPrefWidth(60);
        tfBolasAzules.getStyleClass().add("demo-input");
        
        inputs.getChildren().addAll(
            new Label("Rojas:"), tfBolasRojas,
            new Label("Azules:"), tfBolasAzules
        );

        ToggleGroup group = new ToggleGroup();
        rbConReemplazo = new RadioButton("Con Reemplazo (Independientes)");
        rbConReemplazo.setToggleGroup(group);
        rbSinReemplazo = new RadioButton("Sin Reemplazo (Dependientes)");
        rbSinReemplazo.setToggleGroup(group);
        rbSinReemplazo.setSelected(true);

        HBox radios = new HBox(15);
        radios.getChildren().addAll(rbConReemplazo, rbSinReemplazo);

        Button btnCalcular = new Button("Calcular P(Roja y Roja)");
        btnCalcular.getStyleClass().add("btn-calcular");
        btnCalcular.setOnAction(e -> calcular());

        lblResultado = crearLabelResultado();

        demo.getChildren().addAll(desc, inputs, radios, btnCalcular, lblResultado);
        return demo;
    }

    private void calcular() {
        try {
            int rojas = Integer.parseInt(tfBolasRojas.getText());
            int azules = Integer.parseInt(tfBolasAzules.getText());
            int total = rojas + azules;

            if (rojas < 0 || azules < 0 || total < 2) {
                lblResultado.setText("Debe haber al menos 2 bolas en total y valores positivos.");
                lblResultado.setStyle("-fx-text-fill: #ff6b6b;");
                return;
            }

            double pR1 = (double) rojas / total;
            double pR2;
            String detalleFormula;

            if (rbSinReemplazo.isSelected()) {
                pR2 = (double) (rojas - 1) / (total - 1);
                detalleFormula = String.format("P(R1) * P(R2|R1) = (%d/%d) * (%d/%d)", rojas, total, rojas - 1, total - 1);
            } else {
                pR2 = (double) rojas / total;
                detalleFormula = String.format("P(R1) * P(R2) = (%d/%d) * (%d/%d)", rojas, total, rojas, total);
            }

            double probTotal = pR1 * pR2;
            lblResultado.setText(detalleFormula + String.format("\nP(Roja ∩ Roja) = %.4f", probTotal));
            lblResultado.setStyle("-fx-text-fill: #6c63ff; -fx-font-weight: bold;");

        } catch (NumberFormatException e) {
            lblResultado.setText("Ingresa solo valores numéricos.");
            lblResultado.setStyle("-fx-text-fill: #ff6b6b;");
        }
    }
}