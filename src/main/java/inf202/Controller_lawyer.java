package inf202;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller_lawyer {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Button btnBackToManagerPage, btn_logout;

    public void setVis(boolean input){
        btnBackToManagerPage.setVisible(input);
    }

    public void toManagerPage() throws IOException {
        root = FXMLLoader.load(getClass().getResource("Screen_manager.fxml"));
        //programın kullandığı pencereye erişim
        stage = (Stage) btnBackToManagerPage.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void logout(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Screen_login.fxml"));
        //programın kullandığı pencereye erişim
        stage = (Stage) btn_logout.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
