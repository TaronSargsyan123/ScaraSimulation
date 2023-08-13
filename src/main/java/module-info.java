module com.example.scarasimulation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;


    opens com.example.scarasimulation to javafx.fxml;
    exports com.example.scarasimulation;
    exports com.example.scarasimulation.scara;
    opens com.example.scarasimulation.scara to javafx.fxml;
    exports com.example.scarasimulation.initialWindow;
    opens com.example.scarasimulation.initialWindow to javafx.fxml;
}