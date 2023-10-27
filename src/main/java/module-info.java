module com.espol.mavenproject1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.espol.mavenproject1 to javafx.fxml;
    exports com.espol.mavenproject1;
}
