module com.example.scarasimulation {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.scarasimulation to javafx.fxml;
    exports com.example.scarasimulation;
}