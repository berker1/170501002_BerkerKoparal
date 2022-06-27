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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import java.awt.event.*;
import javafx.scene.input.MouseEvent;

public class Controller_verwaltung implements Initializable {

    GlobalNotifications globalNotifications = new GlobalNotifications();

    private Stage stage;
    private Scene scene;
    private Parent root;

    private ObservableList<Anwalt> listL;
    private ObservableList<Fall> listC;
    private ObservableList<Manager> listM;

    @FXML
    private Button btn_logout, btn_addCase, btn_manager_details,btn_lawyer_details,btn_add_manager,btn_all_managers;
    @FXML
    private Button btn_add_lawyer,btn_all_lawyers, btn_add_case, btn_all_cases,btn_assignable_lawyers, btn_assign_lawyer;
    @FXML
    private Button btn_assignable_cases, btn_assaign_case_manager, btn_assaign_case_lawyer, btn_retain_case_manager;
    @FXML
    private Button btn_retain_case_lawyer, btn_save_case_changes, btn_case_edit, btn_delete_manager, btn_delete_lawyer;
    @FXML
    private TextField tf_case_date2, tf_case_class, tf_case_code, tf_case_state;
    @FXML
    private DatePicker dp_case_date;
    @FXML
    private TextArea ta_case_description;
    @FXML
    private Text txt_user;
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
        txt_user.setText(Database.getUserInfo().toUpperCase());

        tableCase.setOnMouseClicked((MouseEvent event) ->{
            clickTableCases();
        });

        tableManagers.setOnMouseClicked((MouseEvent event) ->{
            clickTableManager();
        });

        tableLawyers.setOnMouseClicked((MouseEvent event) ->{
            clickTableLawyer();
        });
        //btn_case_edit.setDisable(true);
        dp_case_date.setDisable(true);
    }

    private void clickTableCases(){
        System.out.println("show case details clicked");
        int caseID = tableCase.getItems().get(tableCase.getSelectionModel().getSelectedIndex()).getFallId();
        Fall fallSelected = null;
        try {
            fallSelected = Database.show_case_details(caseID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tf_case_class.setText(fallSelected.getFallArt());
        tf_case_code.setText(fallSelected.getFallCode());
        tf_case_state.setText(fallSelected.getFallState());
        ta_case_description.setText(fallSelected.getFallDescription());
        tf_case_date2.setText(fallSelected.getCaseDate());
        btn_case_edit.setDisable(false);
        dp_case_date.setValue(LocalDate.parse(fallSelected.getCaseDate()));
    }

    private void clickTableManager(){
        int tc = tableManagers.getItems().get(tableManagers.getSelectionModel().getSelectedIndex()).getTcNummer();
        int index = 2;
        listC= Database.getDataAssignedCases(tc, index);
        tableCase.setItems(listC);

        lawyerName.setCellValueFactory(new PropertyValueFactory<Anwalt, String>("vorname"));
        lawyerSurname.setCellValueFactory(new PropertyValueFactory<Anwalt, String>("nachname"));
        lawyerBranche.setCellValueFactory(new PropertyValueFactory<Anwalt, String>("branche"));
        listL = Database.getLawyersForManager(tc);
        tableLawyers.setItems(listL);
    }

    private void clickTableLawyer(){
        int tc = tableLawyers.getItems().get(tableLawyers.getSelectionModel().getSelectedIndex()).getTcNummer();
        int index = 3;
        listC= Database.getDataAssignedCases(tc, index);
        tableCase.setItems(listC);
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

        Optional<ButtonType> isConfirmed = globalNotifications.confirmationDialog("Confirmation", "Are you sure to EXIT?");
        if(isConfirmed.get() == ButtonType.OK){
            System.out.println("ok clicked");
            root = FXMLLoader.load(getClass().getResource("Screen_login.fxml"));
            //programın kullandığı pencereye erişim
            stage = (Stage) btn_logout.getScene().getWindow();
            stage.setScene(new Scene(root));
        }else{
            System.out.println("cacel clicked");
        }
    }

    private static int saveParameter = 1;

    public void addCase(ActionEvent e) throws IOException, SQLException {
        saveParameter += 1;
        if(saveParameter % 2 == 0){
            btn_case_edit.setDisable(true);
            makeEditableCases();
            dp_case_date.setDisable(false);
            dp_case_date.setVisible(true);
            tf_case_date2.setVisible(false);
            tf_case_class.setText(null);
            tf_case_code.setText(null);
            dp_case_date.setValue(null);
            tf_case_state.setText(null);
            ta_case_description.setText(null);
            btn_addCase.setText("Save Case");
        }else {
            if(tf_case_code.getText() != null && tf_case_class.getText() != null){
                if (tf_case_state.getText() == null || tf_case_state.getText().length() < 3){
                    //Database.add_case(tf_case_date.getText(), tf_case_code.getText(), tf_case_class.getText(),
                    Database.add_case(dp_case_date.getValue().toString(), tf_case_code.getText(), tf_case_class.getText(),
                            "on-going", ta_case_description.getText());
                    btn_addCase.setText("Add New Case");
                    makeNotEditableCases();
                    dp_case_date.setDisable(true);
                    dp_case_date.setVisible(false);
                    tf_case_date2.setVisible(true);
                }else{
                    //Database.add_case(tf_case_date.getText(), tf_case_code.getText(), tf_case_class.getText(),
                    Database.add_case(dp_case_date.getValue().toString(), tf_case_code.getText(), tf_case_class.getText(),
                            tf_case_state.getText(), ta_case_description.getText());
                    System.out.printf(ta_case_description.getText());
                    btn_addCase.setText("Add New Case");
                    makeNotEditableCases();
                    dp_case_date.setDisable(true);
                    dp_case_date.setVisible(false);
                    tf_case_date2.setVisible(true);
                }

            }else {
                System.out.println("casecode or caseclass can not be null");
                globalNotifications.warningDialog("WARNING!", "Casecode or Caseclass can NOT be NULL");
                saveParameter -= 1;
            }
        }
    }

    public void makeEditableCases(){
        tf_case_class.setEditable(true);
        tf_case_code.setEditable(true);
        dp_case_date.setEditable(true);
        tf_case_state.setEditable(true);
        ta_case_description.setEditable(true);
    }

    public void makeNotEditableCases(){
        tf_case_class.setEditable(false);
        tf_case_code.setEditable(false);
        dp_case_date.setEditable(false);
        tf_case_state.setEditable(false);
        ta_case_description.setEditable(false);
    }


    private static int editParameter = 1;

    public void caseEdit(ActionEvent e) throws SQLException {
        editParameter += 1;
        System.out.println("edit parameter : " + editParameter);
        if(editParameter % 2 == 0){
            makeEditableCases();
            dp_case_date.setValue(LocalDate.parse(tf_case_date2.getText()));
            dp_case_date.setDisable(false);
            dp_case_date.setVisible(true);
            tf_case_date2.setVisible(false);
            btn_case_edit.setText("Save Changes");
            btn_addCase.setDisable(true);
        }else{
            System.out.println(tf_case_code.getText() + tf_case_class.getText() + tf_case_state.getText());
            if(tf_case_code.getText() != null && tf_case_class.getText() != null){
                System.out.println(tf_case_code.getText() + tf_case_class.getText());
                if (tf_case_state.getText() != null ) {
                    System.out.println( tf_case_state.getText());
                    int caseID = tableCase.getItems().get(tableCase.getSelectionModel().getSelectedIndex()).getFallId();
                    //Database.edit_case(tf_case_date.getText(), tf_case_code.getText(), tf_case_class.getText(),tf_case_state.getText(),
                    Database.edit_case(dp_case_date.getValue().toString(), tf_case_code.getText(), tf_case_class.getText(),tf_case_state.getText(),
                            ta_case_description.getText(), caseID);
                    btn_addCase.setDisable(false);
                    btn_case_edit.setText("Edit Case");
                    makeNotEditableCases();
                    dp_case_date.setDisable(true);
                    dp_case_date.setVisible(false);
                    tf_case_date2.setVisible(true);
                    System.out.println("case edited");
                }
                else{
                    System.out.println("check state !");
                    globalNotifications.warningDialog("WARNING!", "Case State can NOT be NULL");
                    editParameter -= 1;
                    btn_addCase.setDisable(false);
                }
            }else{
                System.out.println("please check the new inputs");
                globalNotifications.warningDialog("WARNING!", "Case Code or Case Class can NOT be NULL");
                editParameter -= 1;
                btn_addCase.setDisable(false);
            }
        }
    }

    public void deleteCase(ActionEvent e) throws SQLException {
        int caseID = tableCase.getItems().get(tableCase.getSelectionModel().getSelectedIndex()).getFallId();
        Database.deleteCase(caseID);
        getAllCases();
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

    public void deleteManager(ActionEvent e) throws SQLException {
        int tc = tableManagers.getItems().get(tableManagers.getSelectionModel().getSelectedIndex()).getTcNummer();
        Database.deletePerson(tc);
        getAllManagers();
    }

    public void deleteLawyer(ActionEvent e) throws SQLException {
        int tc = tableLawyers.getItems().get(tableLawyers.getSelectionModel().getSelectedIndex()).getTcNummer();
        Database.deletePerson(tc);
        getAllLawyers();
    }

    private void toPersonPage(int tc, int index, String name) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Screen_person.fxml"));
        root = loader.load();
        Controller_person controller_person = loader.getController();
        controller_person.setText(tc, index, name);
        stage = (Stage) btn_add_manager.getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }

    public void showManagerDetails(ActionEvent e) throws IOException {
        int tc = tableManagers.getItems().get(tableManagers.getSelectionModel().getSelectedIndex()).getTcNummer();
        String name = "Manager Details";
        toPersonPage(tc, -1, name);
    }

    public void showLawyerDetails(ActionEvent e) throws IOException {
        int tc = tableLawyers.getItems().get(tableLawyers.getSelectionModel().getSelectedIndex()).getTcNummer();
        String name = "Lawyer Details";
        toPersonPage(tc,-1, name);
    }

    public void addManager(ActionEvent e) throws IOException {
        toPersonPage(-1,2, "");
    }

    public void addLawyer(ActionEvent e) throws IOException {
        toPersonPage(-1,3, "");
    }

    public void showAllManagers(ActionEvent e) { //gerek yok gibi
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
    }// #ADD8E6

}
