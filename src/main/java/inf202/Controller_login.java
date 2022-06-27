package inf202;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller_login implements Initializable {

    GlobalNotifications globalNotifications = new GlobalNotifications();
    private Stage stage;
    private Scene scene;
    private Parent root;

    private String userName;
    private String password;
    private int loginWho;

    private Database database = new Database();

    @FXML
    private Button btn_login;
    @FXML
    private TextField tf_userName, tf_password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //root.setStyle("-fx-background-image: url('E:\\desktop_main\\Erwischt\\berkay.JPG')");
    }

    public void login() throws IOException, SQLException {

        userName = tf_userName.getText();
        password = tf_password.getText();

        loginWho = database.login_db(userName, password);

        switch (loginWho){
            case 0:
                globalNotifications.warningDialog("WARNING!", "Username or Password is NOT Correct");
                System.out.println("username or password is incorrect");
                tf_userName.setText("");
                tf_password.setText("");
                break;
            case 1:
                System.out.println("Verwaltungspersonel loged-in ");
                toScreen("Screen_verwaltung.fxml", 1);
                break;
            case 2:
                System.out.println("Manager loged-in ");
                toScreen("Screen_manager.fxml", 2);
                break;
            case 3:
                System.out.println("Lawyer loged-in ");
                toScreen("Screen_lawyer.fxml", 3);
                break;
        }
    }
    private void toScreen(String screenRoot, int index) throws IOException {

        root = FXMLLoader.load(getClass().getResource(screenRoot));
        //programın kullandığı pencereye erişim
        stage = (Stage) btn_login.getScene().getWindow();

        switch (index){
            case 1:
                stage.setX(320);
                stage.setY(80);
                break;
            case 2:
                stage.setX(400);
                stage.setY(150);
                break;
            case 3:
                stage.setX(400);
                stage.setY(150);
                break;
        }
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }
}
