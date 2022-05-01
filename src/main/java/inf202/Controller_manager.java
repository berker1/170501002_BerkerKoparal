package inf202;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller_manager {
    private int loginInfo = 1;

    public int getLoginInfo() {
        return loginInfo;
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Button btnManagerToMyPage, btnBackToManagerPage, btn_logout;

    public void toLawyerPage(ActionEvent e) throws IOException{

        boolean girdi = true;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Screen_lawyer.fxml"));
        root = loader.load();

        Controller_lawyer controller_lawyer = loader.getController();
        controller_lawyer.setVis(girdi);

        //root = FXMLLoader.load(getClass().getResource("Screen_lawyer.fxml"));
        //programın kullandığı pencereye erişim
        stage = (Stage) btnManagerToMyPage.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void logout(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Screen_login.fxml"));
        //programın kullandığı pencereye erişim
        stage = (Stage) btn_logout.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}
