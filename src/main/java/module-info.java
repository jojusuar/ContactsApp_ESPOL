module com.espol.Proyecto1_Estructuras {
    requires javafx.controls;
    requires javafx.fxml;

    opens vistas to javafx.fxml;
    exports vistas;
}
