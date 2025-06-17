module com.usac.ayd2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.usac.ayd2 to javafx.fxml;
    exports com.usac.ayd2;
}
