package inf202;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller_manager implements Initializable {
    private int loginInfo = 1;

    public int getLoginInfo() {
        return loginInfo;
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Button btnManagerToMyPage, btnBackToManagerPage, btn_logout;
    @FXML
    private TableView<Anwalt> tableLawyers;
    @FXML
    private TableColumn<Anwalt,String> Lawyers; // galba gerek olmayacak
    @FXML
    private TableColumn<Anwalt,String> lawyerName;
    @FXML
    private TableColumn<Anwalt,String> lawyerSurname;
    @FXML
    private TableColumn<Anwalt,String> lawyerBranche;
    @FXML
    private TableView<Fall> tableCase;
    @FXML
    private TableColumn<Fall, String> cases;

    ObservableList<Anwalt> listA;
    ObservableList<Fall> listB;

    int imdex = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lawyerName.setCellValueFactory(new PropertyValueFactory<Anwalt, String>("vorname"));
        lawyerSurname.setCellValueFactory(new PropertyValueFactory<Anwalt, String>("nachname"));
        lawyerBranche.setCellValueFactory(new PropertyValueFactory<Anwalt, String>("branche"));

        try {
            listA = Database.getLawyersForManager(Database.getUserTC());
        } catch (SQLException e) {
            e.printStackTrace();
        }

       tableLawyers.setItems(listA);

        cases.setCellValueFactory(new PropertyValueFactory<Fall, String>("fallCode"));
        listB = Database.getDataFall();
        tableCase.setItems(listB);

    }

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
