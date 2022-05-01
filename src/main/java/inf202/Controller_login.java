package inf202;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Controller_login {

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

    public void login() throws IOException, SQLException {

        userName = tf_userName.getText();
        password = tf_password.getText();

        loginWho = database.login_db(userName, password);

        switch (loginWho){
            case 0:
                System.out.println("username or password is incorrect");
                tf_userName.setText("");
                tf_password.setText("");
                break;
            case 1:
                System.out.println("Verwaltungspersonel loged-in ");
                toScreen("Screen_verwaltung.fxml");
                break;
            case 2:
                System.out.println("Manager loged-in ");
                toScreen("Screen_manager.fxml");
                break;
            case 3:
                System.out.println("Lawyer loged-in ");
                toScreen("Screen_lawyer.fxml");
                break;
        }
    }
    private void toScreen(String screenRoot) throws IOException {
        root = FXMLLoader.load(getClass().getResource(screenRoot));
        //programın kullandığı pencereye erişim
        stage = (Stage) btn_login.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
