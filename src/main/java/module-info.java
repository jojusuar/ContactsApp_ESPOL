module com.espol.Proyecto1_Estructuras {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.espol.Proyecto1_Estructuras to javafx.fxml;
    exports com.espol.Proyecto1_Estructuras;
}
