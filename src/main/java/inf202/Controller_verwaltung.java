package inf202;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller_verwaltung {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Button btn_logout, btn_addCase;

    public void logout(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Screen_login.fxml"));
        //programın kullandığı pencereye erişim
        stage = (Stage) btn_logout.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void addCase(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Screen_case.fxml"));
        //programın kullandığı pencereye erişim
        stage = (Stage) btn_addCase.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
