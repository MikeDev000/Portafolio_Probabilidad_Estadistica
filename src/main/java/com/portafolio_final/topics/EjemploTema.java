package com.portafolio_final.topics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EjemploTema extends BaseTopicWindow {

    // Campos de entrada del usuario
    private TextField txtFavorables;  // Casos favorables para el evento A
    private TextField txtTotales;     // Casos totales del espacio muestral

    // Labels donde se muestra el resultado
    private Label lblResultadoDecimal;
    private Label lblResultadoPorcentaje;
    private Label lblResultadoFraccion;
    private Label lblMensaje;

    @Override
    public String getTitulo() {
        return "Tema 1 — Probabilidad Clásica";
    }

    @Override
    public String getNombreTema() {
        return "Probabilidad Clásica";
    }

    @Override
    public String getSubtitulo() {
        return "Regla de Laplace • Espacio muestral • Eventos";
    }

    @Override
    public String getIcono() {
        // Ícono de dados (FontAwesome 5)
        return "fas-dice";
    }

    @Override
    public String getDefinicion() {
        return "La Probabilidad Clásica (o Regla de Laplace) es una forma de medir la "
                + "posibilidad de que ocurra un evento cuando todos los resultados posibles "
                + "son igualmente probables.\n\n"
                + "El espacio muestral (Ω) es el conjunto de todos los resultados posibles "
                + "de un experimento aleatorio. Un evento (A) es cualquier subconjunto del "
                + "espacio muestral. La probabilidad de A es el cociente entre el número de "
                + "casos favorables y el número de casos totales posibles.";
    }

    @Override
    public String getFormula() {
        return "P(A)  =  |A|  /  |Ω|\n\n"
                + "P(A)  =  casos_favorables  /  casos_totales";
    }

    @Override
    public String getDescFormula() {
        return "Donde:  |A| = número de resultados favorables al evento A  |  "
                + "|Ω| = número total de resultados posibles del espacio muestral.  "
                + "El valor de P(A) siempre está en el intervalo [0, 1].";
    }


    //  buildDemoSection() — Sección interactiva del tema
    //  Aquí construye UI de cálculo/demostración

    @Override
    public Node buildDemoSection() {
        VBox demo = new VBox(16);
        demo.setPadding(new Insets(4, 0, 4, 0));

        // Descripción de la demo
        Label instruccion = new Label(
                "Ingresa el número de casos favorables y el total de casos del espacio "
                        + "muestral para calcular la probabilidad del evento A."
        );
        instruccion.getStyleClass().add("demo-instruction");
        instruccion.setWrapText(true);

        // Fila de inputs
        HBox filaInputs = new HBox(20);
        filaInputs.setAlignment(Pos.CENTER_LEFT);

        // Input: casos favorables
        VBox colFavorables = new VBox(6);
        Label lblF = new Label("Casos favorables  |A|:");
        lblF.getStyleClass().add("input-label");
        txtFavorables = new TextField();
        txtFavorables.setPromptText("Ej:  3");
        txtFavorables.getStyleClass().add("demo-input");
        txtFavorables.setPrefWidth(130);
        // Calcular al presionar Enter
        txtFavorables.setOnAction(e -> calcularProbabilidad());
        colFavorables.getChildren().addAll(lblF, txtFavorables);

        // Input: casos totales
        VBox colTotales = new VBox(6);
        Label lblT = new Label("Casos totales  |Ω|:");
        lblT.getStyleClass().add("input-label");
        txtTotales = new TextField();
        txtTotales.setPromptText("Ej:  6");
        txtTotales.getStyleClass().add("demo-input");
        txtTotales.setPrefWidth(130);
        txtTotales.setOnAction(e -> calcularProbabilidad());
        colTotales.getChildren().addAll(lblT, txtTotales);

        filaInputs.getChildren().addAll(colFavorables, colTotales);

        // Botón calcular
        Button btnCalcular = new Button("  Calcular P(A)  ");
        btnCalcular.getStyleClass().add("btn-calcular");
        btnCalcular.setOnAction(e -> calcularProbabilidad());

        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.getStyleClass().add("btn-limpiar");
        btnLimpiar.setOnAction(e -> limpiarCampos());

        HBox filaBotones = new HBox(10);
        filaBotones.setAlignment(Pos.CENTER_LEFT);
        filaBotones.getChildren().addAll(btnCalcular, btnLimpiar);

        // Box de resultado
        VBox resultBox = new VBox(8);
        resultBox.getStyleClass().add("result-box");
        resultBox.setPadding(new Insets(14, 18, 14, 18));
        resultBox.setVisible(false); // Oculto hasta calcular

        Label lblTituloRes = new Label("Resultado:");
        lblTituloRes.getStyleClass().add("result-label");

        lblResultadoDecimal = new Label();
        lblResultadoDecimal.getStyleClass().add("result-value");

        HBox filaExtra = new HBox(20);
        lblResultadoPorcentaje = new Label();
        lblResultadoPorcentaje.getStyleClass().add("result-extra");
        lblResultadoFraccion = new Label();
        lblResultadoFraccion.getStyleClass().add("result-extra");
        filaExtra.getChildren().addAll(lblResultadoPorcentaje, lblResultadoFraccion);

        resultBox.getChildren().addAll(lblTituloRes, lblResultadoDecimal, filaExtra);

        // Label de mensajes de error o advertencia
        lblMensaje = new Label();
        lblMensaje.getStyleClass().add("error-text");
        lblMensaje.setWrapText(true);
        lblMensaje.setVisible(false);

        // Guardar referencia al resultBox para mostrarlo tras calcular
        btnCalcular.setUserData(resultBox);

        // Ensamblar todo
        demo.getChildren().addAll(
                instruccion,
                filaInputs,
                filaBotones,
                lblMensaje,
                resultBox
        );

        return demo;
    }

    //  LÓGICA DE CÁLCULO

    /**
     * Calcula P(A) = casos_favorables / casos_totales.
     * Maneja errores con try-catch para que la app no se cierre
     * si el usuario ingresa datos inválidos.
     */
    private void calcularProbabilidad() {
        // Ocultar resultados anteriores
        ocultarResultados();

        try {
            // Leer y validar entradas
            String txtF = txtFavorables.getText().trim();
            String txtT = txtTotales.getText().trim();

            if (txtF.isEmpty() || txtT.isEmpty()) {
                mostrarError("Por favor completa ambos campos antes de calcular.");
                return;
            }

            int favorables = Integer.parseInt(txtF);
            int totales    = Integer.parseInt(txtT);

            // Validaciones de dominio
            if (favorables < 0 || totales < 0) {
                mostrarError("Los valores no pueden ser negativos.");
                return;
            }
            if (totales == 0) {
                mostrarError("El espacio muestral |Ω| no puede ser cero (división entre cero).");
                return;
            }
            if (favorables > totales) {
                mostrarError("Los casos favorables no pueden superar al total de casos posibles.");
                return;
            }

            // Cálculo
            double probabilidad = (double) favorables / totales;
            double porcentaje   = probabilidad * 100;
            int    mcd          = calcularMCD(favorables, totales);
            String fraccion     = (favorables / mcd) + " / " + (totales / mcd);

            // Mostrar resultado
            // Buscar y mostrar el result box (referencia guardada en UserData del botón)
            // Aquí simplemente lo actualizamos y lo hacemos visible:
            lblResultadoDecimal.setText(String.format("P(A)  =  %.4f", probabilidad));
            lblResultadoPorcentaje.setText(String.format("Porcentaje:  %.2f%%", porcentaje));
            lblResultadoFraccion.setText("Fracción simplificada:  " + fraccion);

            // Encontrar el resultBox en la jerarquía y mostrarlo
            VBox parent = (VBox) lblResultadoDecimal.getParent();
            parent.setVisible(true);

        } catch (NumberFormatException ex) {
            // El usuario ingresó texto en lugar de números
            mostrarError("Ingresa solo números enteros válidos (sin letras ni decimales).");
        }
    }

    /** Calcula el Máximo Común Divisor para simplificar la fracción */
    private int calcularMCD(int a, int b) {
        return b == 0 ? a : calcularMCD(b, a % b);
    }

    /** Limpia todos los campos y oculta resultados */
    private void limpiarCampos() {
        txtFavorables.clear();
        txtTotales.clear();
        ocultarResultados();
        txtFavorables.requestFocus();
    }

    /** Oculta el resultado y el mensaje de error */
    private void ocultarResultados() {
        lblMensaje.setVisible(false);
        // El result box está oculto por defecto; se oculta el padre del lblResultadoDecimal
        if (lblResultadoDecimal.getParent() != null) {
            lblResultadoDecimal.getParent().setVisible(false);
        }
    }

    /** Muestra un mensaje de error en rojo */
    private void mostrarError(String mensaje) {
        lblMensaje.setText(mensaje);
        lblMensaje.setVisible(true);
    }
}