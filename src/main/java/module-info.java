module com.portafolio_final {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires jdk.compiler;

    opens com.portafolio_final to javafx.fxml;
    exports com.portafolio_final;
    exports com.portafolio_final.controller;
    opens com.portafolio_final.controller to javafx.fxml;
}