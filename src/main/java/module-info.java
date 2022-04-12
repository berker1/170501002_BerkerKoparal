module inf202_klassen {
    requires javafx.controls;
    requires javafx.fxml;


    opens inf202_klassen to javafx.fxml;
    exports inf202_klassen;
}