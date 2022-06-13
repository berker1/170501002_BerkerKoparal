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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller_verwaltung implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private ObservableList<Anwalt> listL;
    private ObservableList<Fall> listC;
    private ObservableList<Manager> listM;

    @FXML
    private Button btn_logout, btn_addCase, btn_manager_details,btn_lawyer_details,btn_add_manager,btn_all_managers,
            btn_add_lawyer,btn_all_lawyers, btn_add_case, btn_all_cases,btn_assignable_lawyers, btn_assign_lawyer,
            btn_assignable_cases, btn_assaign_case_manager, btn_assaign_case_lawyer,btn_assaigned_cases_manager,
            btn_assigned_cases_lawyer, btn_retain_case_manager, btn_retain_case_lawyer, btn_save_case_changes;
    @FXML
    private TextField tf_case_date, tf_case_class, tf_case_code, tf_case_state, tf_user;
    @FXML
    private TextArea ta_case_description;
    @FXML
    private TableView<Manager> tableManagers;
    @FXML
    private TableColumn<Manager,String> managerName;
    @FXML
    private TableColumn<Manager,String> managerSurname;
    @FXML
    private TableColumn<Manager,String> managerBranche;
    @FXML
    private TableView<Anwalt> tableLawyers;
    @FXML
    private TableColumn<Anwalt,String> lawyerName;
    @FXML
    private TableColumn<Anwalt,String> lawyerSurname;
    @FXML
    private TableColumn<Anwalt,String> lawyerBranche;
    @FXML
    private TableView<Fall> tableCase;
    @FXML
    private TableColumn<Fall, String> caseCode;
    @FXML
    private TableColumn<Fall, String> caseID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getAllCases();
        getAllLawyers();
        getAllManagers();
        tf_user.setText(Database.getUserInfo().toUpperCase());
    }

    public void getAllManagers(){
        managerName.setCellValueFactory(new PropertyValueFactory<Manager, String>("vorname"));
        managerSurname.setCellValueFactory(new PropertyValueFactory<Manager, String>("nachname"));
        managerBranche.setCellValueFactory(new PropertyValueFactory<Manager, String>("branche"));
        listM = Database.getManagers();
        tableManagers.setItems(listM);
    }

    public void getAllLawyers(){
        lawyerName.setCellValueFactory(new PropertyValueFactory<Anwalt, String>("vorname"));
        lawyerSurname.setCellValueFactory(new PropertyValueFactory<Anwalt, String>("nachname"));
        lawyerBranche.setCellValueFactory(new PropertyValueFactory<Anwalt, String>("branche"));
        listL = Database.getLawyers();
        tableLawyers.setItems(listL);
    }

    public void getAllCases(){
        caseID.setCellValueFactory(new PropertyValueFactory<Fall, String>("fallId"));
        caseCode.setCellValueFactory(new PropertyValueFactory<Fall, String>("fallCode"));
        listC = Database.getCases();
        tableCase.setItems(listC);
    }

    public void getAssignableCases(int index){
        caseCode.setCellValueFactory(new PropertyValueFactory<Fall, String>("fallCode"));
        listC = Database.getAssignableCasesInAdmin(index);
        tableCase.setItems(listC);
    }

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

    public void showAssignableCasesForManager(ActionEvent e) {
        caseID.setCellValueFactory(new PropertyValueFactory<Fall, String>("fallId"));
        caseCode.setCellValueFactory(new PropertyValueFactory<Fall, String>("fallCode"));
        listC = Database.getAssignableCasesInAdmin(2);
        tableCase.setItems(listC);
    }

    public void showAssignableCasesForLawyer(ActionEvent e) {
        caseID.setCellValueFactory(new PropertyValueFactory<Fall, String>("fallId"));
        caseCode.setCellValueFactory(new PropertyValueFactory<Fall, String>("fallCode"));
        listC = Database.getAssignableCasesInAdmin(3);
        tableCase.setItems(listC);
    }

    public void showAssignedCasesManager(ActionEvent e) {
        int tc = tableManagers.getItems().get(tableManagers.getSelectionModel().getSelectedIndex()).getTcNummer();
        int index = 2;
        listC= Database.getDataAssignedCases(tc, index);
        tableCase.setItems(listC);
    }

    public void showAssignedCasesLawyer(ActionEvent e) {
        int tc = tableLawyers.getItems().get(tableLawyers.getSelectionModel().getSelectedIndex()).getTcNummer();
        int index = 3;
        listC= Database.getDataAssignedCases(tc, index);
        tableCase.setItems(listC);
    }

    public void showManagerDetails(ActionEvent e) {

    }

    public void showLawyerDetails(ActionEvent e) {

    }

    public void addManager(ActionEvent e) {

    }

    public void addLawyer(ActionEvent e) {

    }

    public void showAllManagers(ActionEvent e) {
        getAllManagers();
    }

    public void showAllLawyers(ActionEvent e) {
        getAllLawyers();
    }

    public void ShowAllCases(ActionEvent e) {
        getAllCases();
    }

    public void showAssignableLawyers(ActionEvent e) {
        lawyerName.setCellValueFactory(new PropertyValueFactory<Anwalt, String>("vorname"));
        lawyerSurname.setCellValueFactory(new PropertyValueFactory<Anwalt, String>("nachname"));
        lawyerBranche.setCellValueFactory(new PropertyValueFactory<Anwalt, String>("branche"));
        listL = Database.getAssignableLawyers();
        tableLawyers.setItems(listL);
    }

    public void assignLawyerToManager(ActionEvent e) throws SQLException {
        int tcLawyer = tableLawyers.getItems().get(tableLawyers.getSelectionModel().getSelectedIndex()).getTcNummer();
        int tcManager = tableManagers.getItems().get(tableManagers.getSelectionModel().getSelectedIndex()).getTcNummer();
        Database.assignLawyer(tcLawyer, tcManager);
    }

    public void assignCaseManager(ActionEvent e) throws SQLException {

        int tc = tableManagers.getItems().get(tableManagers.getSelectionModel().getSelectedIndex()).getTcNummer();
        int caseID = tableCase.getItems().get(tableCase.getSelectionModel().getSelectedIndex()).getFallId();
        int index = 3;
        Database.assignCase(tc, caseID, index);
        getAssignableCases(2);
    }

    public void assignCaseLawyer(ActionEvent e) throws SQLException {
        int tc = tableLawyers.getItems().get(tableLawyers.getSelectionModel().getSelectedIndex()).getTcNummer();
        int caseID = tableCase.getItems().get(tableCase.getSelectionModel().getSelectedIndex()).getFallId();
        int index = 2;
        Database.assignCase(tc, caseID, index);
        getAssignableCases(3);

    }

    public void retainCaseManager(ActionEvent e) throws SQLException {
        int tc = tableManagers.getItems().get(tableManagers.getSelectionModel().getSelectedIndex()).getTcNummer();
        int caseID = tableCase.getItems().get(tableCase.getSelectionModel().getSelectedIndex()).getFallId();
        int index = 3;
        Database.retainCase(tc, caseID, index);
    }

    public void retainCaseLawyer(ActionEvent e) throws SQLException {
        int tc = tableLawyers.getItems().get(tableLawyers.getSelectionModel().getSelectedIndex()).getTcNummer();
        int caseID = tableCase.getItems().get(tableCase.getSelectionModel().getSelectedIndex()).getFallId();
        int index = 3;
        Database.retainCase(tc, caseID, index);
    }

    public void showCaseDetails(ActionEvent e) throws SQLException {
        int caseID = tableCase.getItems().get(tableCase.getSelectionModel().getSelectedIndex()).getFallId();
        Fall fallSelected = Database.show_case_details(caseID);
        tf_case_date.setText(fallSelected.getCaseDate());
        tf_case_class.setText(fallSelected.getFallArt());
        tf_case_code.setText(fallSelected.getFallCode());
        tf_case_state.setText(fallSelected.getFallState());
        ta_case_description.setText(fallSelected.getFallDescription());
    }
}
