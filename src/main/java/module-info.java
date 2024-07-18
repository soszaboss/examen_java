module com.example.project_examen {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires spring.security.crypto;

    opens com.example.project_examen to javafx.fxml;
    exports com.example.project_examen;
}