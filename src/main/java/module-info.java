module inf202 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens inf202 to javafx.fxml;
    exports inf202;
}