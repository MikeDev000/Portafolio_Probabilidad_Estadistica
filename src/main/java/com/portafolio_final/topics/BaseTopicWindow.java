package com.portafolio_final.topics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.ikonli.javafx.FontIcon;

public abstract class BaseTopicWindow {
    protected static final double ANCHO_VENTANA = 740;
    protected static final double ALTO_VENTANA = 580;
    protected static final double MIN_ANCHO = 600;
    protected static final double MIN_ALTO = 480;

    protected Stage stage;

    // metodos abstractos (implementarlos en la subclase)

    public abstract String getTitulo();

    public abstract String getNombreTema();

    public abstract String getSubtitulo();

    // ICONOS = Ejemplos: "fas-dice", "fas-chart-bar", "fas-bell", "fas-calculator"
    public abstract String getIcono();

    public abstract String getDefinicion();

    public abstract String getFormula();

    public abstract String getDescFormula();

    public abstract Node buildDemoSection();

    // Metodos publicos
    public void show() {
        if (stage == null) {
            construirVentana();
        }
        stage.show();
        stage.toFront();
    }

    public void cerrar() {
        if (stage != null) {
            stage.close();
        }
    }

    // construccion de UI
    private void construirVentana() {
        stage = new Stage();
        stage.setTitle(getTitulo());
        //initModality(NONE)
        stage.setMinWidth(MIN_ANCHO);
        stage.setMinHeight(MIN_ALTO);

        // layout raiz
        VBox root = new VBox(0);
        root.getStyleClass().add("topic-window-root");

        // 1 header
        root.getChildren().add(buildHeader());

        // 2 Contenido scrolleable
        VBox contenido = new VBox(10);
        contenido.setPadding(new Insets(24, 32, 32, 32));
        contenido.getStyleClass().add("topic-content");

        // sección definicion
        contenido.getChildren().add(buildSeccionDefinicion());

        if (getFormula() != null && !getFormula().isBlank()) {
            contenido.getChildren().add(buildSeccionFormula());
        }

        // seccion demostración interactiva
        contenido.getChildren().add(buildSeccionDemo());

        // envolver en scrollpane
        ScrollPane scroll = new ScrollPane(contenido);
        scroll.setFitToWidth(true);
        scroll.getStyleClass().add("topic-scroll");
        VBox.setVgrow(scroll, Priority.ALWAYS);
        root.getChildren().add(scroll);

        // aplicar estilos
        Scene scene = new Scene(root, ANCHO_VENTANA, ALTO_VENTANA);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        java.net.URL cssUrl = getClass().getClassLoader()
                .getResource("main.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }

        stage.setScene(scene);
    }

    // Construir el header oscuro (icono, titulo, subtitulo)
    private HBox buildHeader() {
        HBox header = new HBox(16);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20, 28, 20, 28));
        header.getStyleClass().add("topic-header");

        FontIcon icono = new FontIcon(getIcono());
        icono.setIconSize(32);
        icono.getStyleClass().add("topic-header-icon");

        VBox textos = new VBox(4);
        Label lblNombre = new Label(getNombreTema());
        lblNombre.getStyleClass().add("topic-header-title");
        Label lblSub = new Label(getSubtitulo());
        lblSub.getStyleClass().add("topic-header-subtitle");
        textos.getChildren().addAll(lblNombre, lblSub);

        header.getChildren().addAll(icono, textos);
        return header;
    }

    // Construye tarjeta de sección "definición"
    private VBox buildSeccionDefinicion() {
        VBox card = new VBox(12);
        card.getStyleClass().add("topic-section-card");

        // encabezado seccion
        HBox encabezado = new HBox(10);
        encabezado.setAlignment(Pos.CENTER_LEFT);
        FontIcon icono = new FontIcon("fas-book-open");
        icono.setIconSize(16);
        icono.getStyleClass().add("section-icon");
        Label badge = new Label("DEFINICIÓN");
        badge.getStyleClass().add("section-badge");

        Label titulo = new Label("¿Qué es " + getNombreTema() + "?");
        titulo.getStyleClass().add("section-title-text");

        Separator sep = new Separator();

        Label definicion = new Label(getDefinicion());
        definicion.getStyleClass().add("definition-text");
        definicion.setWrapText(true);

        card.getChildren().addAll(encabezado, titulo, sep, definicion);
        return card;
    }

    // construye tarjeta de seccion: formula/codigo
    private VBox buildSeccionFormula() {
        VBox card = new VBox(12);
        card.getStyleClass().add("topic-section-card");

        // encabezado
        HBox encabezado = new HBox(10);
        encabezado.setAlignment(Pos.CENTER_LEFT);
        FontIcon icono = new FontIcon("fas-superscript");
        icono.setIconSize(16);
        icono.getStyleClass().add("section-icon");
        Label badge = new Label("FÓRMULA / CÓDIGO");
        badge.getStyleClass().add("section-badge");
        encabezado.getChildren().addAll(icono, badge);

        Label titulo = new Label("Expresión Matemática");
        titulo.getStyleClass().add("section-title-text");

        Separator sep = new Separator();

        // bloque oscuro formula en monoespacio
        VBox formulaBlock = new VBox(0);
        formulaBlock.getStyleClass().add("formula-block");
        Label lblFormula = new Label(getFormula());
        lblFormula.getStyleClass().add("formula-text");
        lblFormula.setWrapText(true);
        formulaBlock.getChildren().add(lblFormula);

        // descripcion formula (si existe)
        if (getDescFormula() != null && !getDescFormula().isBlank()) {
            Label lblDesc = new Label(getDescFormula());
            lblDesc.getStyleClass().add("formula-desc");
            lblDesc.setWrapText(true);
            card.getChildren().addAll(encabezado, titulo, sep, formulaBlock, lblDesc);
        } else {
            card.getChildren().addAll(encabezado, titulo, sep, formulaBlock);
        }

        return card;
    }

    // envuelve la seccion demo en una tarjeta
    private VBox buildSeccionDemo() {
        VBox card = new VBox(12);
        card.getStyleClass().add("topic-section-card");

        // Encabezado de sección
        HBox encabezado = new HBox(10);
        encabezado.setAlignment(Pos.CENTER_LEFT);
        FontIcon icono = new FontIcon("fas-play-circle");
        icono.setIconSize(16);
        icono.getStyleClass().add("section-icon-demo");
        Label badge = new Label("DEMOSTRACIÓN INTERACTIVA");
        badge.getStyleClass().add("section-badge-demo");
        encabezado.getChildren().addAll(icono, badge);

        Label titulo = new Label("Prueba el Concepto");
        titulo.getStyleClass().add("section-title-text");

        Separator sep = new Separator();

        // El Node con la demo lo construye la subclase
        Node demoNode = buildDemoSection();
        VBox.setVgrow(demoNode, Priority.ALWAYS);

        card.getChildren().addAll(encabezado, titulo, sep, demoNode);
        return card;
    }

    // metodos utilitarios para subclases

    // label de resultado "estilizado"
    // usar en buildDemoSection()
    protected Label crearLabelResultado() {
        Label lbl = new Label("— Ingresa los datos y presiona calcular -");
        lbl.getStyleClass().add("result-placeholder");
        lbl.setWrapText(true);
        return lbl;
    }

    // label de error en rojo
    // usar en bloque catch de los calculos
    protected Label crearLabelError(String mensaje) {
        Label lbl = new Label("⚠ " + mensaje);
        lbl.getStyleClass().add("error-text");
        lbl.setWrapText(true);
        return  lbl;
    }

    // crear un separador de espacio vertical
    //@param altura Altura del espacion en PIXELES
    protected Region espacio(double altura) {
        Region r = new Region();
        r.setMinHeight(altura);
        r.setPrefHeight(altura);
        return r;
    }
}














