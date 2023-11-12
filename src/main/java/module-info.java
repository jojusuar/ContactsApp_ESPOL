module com.espol.proyecto1_estructuras {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.espol.proyecto1_estructuras to javafx.fxml;
    exports com.espol.proyecto1_estructuras;
}
