package inf202;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller_case {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Button btn_backToVerwaltung;

    public void backToVerwaltung(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Screen_verwaltung.fxml"));
        //programın kullandığı pencereye erişim
        stage = (Stage) btn_backToVerwaltung.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
