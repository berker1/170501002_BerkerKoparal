package inf202;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private Button btnManagerToMyPage, btnBackToManagerPage, btn_logout, btn_show_details;
    @FXML
    private Button btn_all_cases,btn_assignable_cases,btn_assign_case, btn_retain_case, btn_assigned_cases;
    @FXML
    private TextField tf_case_date_m, tf_case_class_m, tf_case_code_m, tf_case_state_m;
    @FXML
    private TextArea ta_case_description_m;
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
        btn_assign_case.setVisible(false);
        btn_retain_case.setVisible(false);

        lawyerName.setCellValueFactory(new PropertyValueFactory<Anwalt, String>("vorname"));
        lawyerSurname.setCellValueFactory(new PropertyValueFactory<Anwalt, String>("nachname"));
        lawyerBranche.setCellValueFactory(new PropertyValueFactory<Anwalt, String>("branche"));

        try {
            listA = Database.getLawyersForManager(Database.getUserTC());
        } catch (SQLException e) {
            e.printStackTrace();
        }

       tableLawyers.setItems(listA);

        getAllCases();

    }

    public void getAllCases(){
        cases.setCellValueFactory(new PropertyValueFactory<Fall, String>("fallCode"));
        try {
            listB = Database.getDataFall(Database.getUserTC(), 2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableCase.setItems(listB);
    }

    public void getAssignableCases(){
        cases.setCellValueFactory(new PropertyValueFactory<Fall, String>("fallCode"));
        try {
            listB = Database.getAssignableCases(Database.getUserTC());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableCase.setItems(listB);
    }

    public void showAllCases(ActionEvent e) throws IOException{
        getAllCases();
        btn_assign_case.setVisible((false));
        btn_retain_case.setVisible(false);
    }

    public void showAssignableCases(ActionEvent e) throws IOException{
        getAssignableCases();
        btn_assign_case.setVisible(true);
        btn_retain_case.setVisible(false);
    }

    public void assignCase(ActionEvent e) throws IOException, SQLException {
        int tc = tableLawyers.getItems().get(tableLawyers.getSelectionModel().getSelectedIndex()).getTcNummer();
        int caseID = tableCase.getItems().get(tableCase.getSelectionModel().getSelectedIndex()).getFallId();
        int index = 3;
        Database.assignCase(tc, caseID, index);
        getAssignableCases();
    }

    public void showAssignedCases(ActionEvent e){
        int tc = tableLawyers.getItems().get(tableLawyers.getSelectionModel().getSelectedIndex()).getTcNummer();
        int index = 3;
        listB = Database.getDataCaseAssignedForLawyer(tc, index);
        tableCase.setItems(listB);
        btn_retain_case.setVisible(true);
        btn_assign_case.setVisible(false);
    }

    public void retainCase(ActionEvent e) throws IOException, SQLException {
        int tc = tableLawyers.getItems().get(tableLawyers.getSelectionModel().getSelectedIndex()).getTcNummer();
        int caseID = tableCase.getItems().get(tableCase.getSelectionModel().getSelectedIndex()).getFallId();
        int index = 3;
        Database.retainCase(tc, caseID, index);
    }

    public void showDetails(ActionEvent e) throws IOException, SQLException {
        int caseID = tableCase.getItems().get(tableCase.getSelectionModel().getSelectedIndex()).getFallId();
        Fall fallSelected = Database.show_case_details(caseID);
        tf_case_date_m.setText(fallSelected.getCaseDate());
        tf_case_class_m.setText(fallSelected.getFallArt());
        tf_case_code_m.setText(fallSelected.getFallCode());
        tf_case_state_m.setText(fallSelected.getFallState());
        ta_case_description_m.setText(fallSelected.getFallDescription());
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
