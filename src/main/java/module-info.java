module com.espol.Proyecto1_Estructuras {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens vistas to javafx.fxml;
    exports vistas;
}
