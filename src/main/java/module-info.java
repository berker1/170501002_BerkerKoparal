module inf202 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens inf202 to javafx.fxml;
    exports inf202;
}