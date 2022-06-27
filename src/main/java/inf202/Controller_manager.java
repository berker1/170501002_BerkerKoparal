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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller_manager implements Initializable {
    private int loginInfo = 1;

    public int getLoginInfo() {
        return loginInfo;
    }

    GlobalNotifications globalNotifications = new GlobalNotifications();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button btnManagerToMyPage, btnBackToManagerPage, btn_logout, btn_show_details;
    @FXML
    private Button btn_all_cases,btn_assignable_cases,btn_assign_case, btn_retain_case, btn_lawyer_details,btn_case_edit, btn_save_case;
    @FXML
    private TextField tf_case_date_m, tf_case_class_m, tf_case_code_m, tf_case_state_m;
    @FXML
    private TextArea ta_case_description_m;
    @FXML
    private Text txt_user;
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

    private ObservableList<Anwalt> listL;
    private ObservableList<Fall> ListC;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_assign_case.setVisible(false);
        //btn_retain_case.setVisible(false);

        getAllLawyers();
        getAllCases();

        tableCase.setOnMouseClicked((MouseEvent event) ->{
            clickTableCases();
        });
        tableLawyers.setOnMouseClicked((MouseEvent event) ->{
            clickTableLawyer();
        });

        txt_user.setText(Database.getUserInfo().toUpperCase());
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
        tf_case_class_m.setText(fallSelected.getFallArt());
        tf_case_code_m.setText(fallSelected.getFallCode());
        tf_case_state_m.setText(fallSelected.getFallState());
        ta_case_description_m.setText(fallSelected.getFallDescription());
        tf_case_date_m.setText(fallSelected.getCaseDate());
    }

    private void clickTableLawyer(){
        int tc = tableLawyers.getItems().get(tableLawyers.getSelectionModel().getSelectedIndex()).getTcNummer();
        int index = 3;
        ListC= Database.getDataAssignedCases(tc, index);
        tableCase.setItems(ListC);
        //btn_retain_case.setVisible(true);
    }

    public void getAllLawyers(){
        lawyerName.setCellValueFactory(new PropertyValueFactory<Anwalt, String>("vorname"));
        lawyerSurname.setCellValueFactory(new PropertyValueFactory<Anwalt, String>("nachname"));
        lawyerBranche.setCellValueFactory(new PropertyValueFactory<Anwalt, String>("branche"));
        listL = Database.getLawyersForManager(Database.getUserTC());
        tableLawyers.setItems(listL);
    }

    public void getAllCases(){
        cases.setCellValueFactory(new PropertyValueFactory<Fall, String>("fallCode"));
        ListC = Database.getDataFall(Database.getUserTC(), 2);
        tableCase.setItems(ListC);
    }

    public void getAssignableCases(){
        cases.setCellValueFactory(new PropertyValueFactory<Fall, String>("fallCode"));
        ListC = Database.getAssignableCasesInManager(Database.getUserTC());
        tableCase.setItems(ListC);
    }

    public void showAllCases(ActionEvent e) throws IOException{
        getAllCases();
        btn_assign_case.setVisible((false));
        //btn_retain_case.setVisible(false);
    }

    public void showAssignableCases(ActionEvent e) throws IOException{
        getAssignableCases();
        btn_assign_case.setVisible(true);
        //btn_retain_case.setVisible(false);
    }

    public void assignCase(ActionEvent e) throws IOException, SQLException {
        int tc = tableLawyers.getItems().get(tableLawyers.getSelectionModel().getSelectedIndex()).getTcNummer();
        int caseID = tableCase.getItems().get(tableCase.getSelectionModel().getSelectedIndex()).getFallId();
        int index = 3;
        Database.assignCase(tc, caseID, index);
        getAssignableCases();
    }


    public void retainCase(ActionEvent e) throws IOException, SQLException {
        int tc = tableLawyers.getItems().get(tableLawyers.getSelectionModel().getSelectedIndex()).getTcNummer();
        int caseID = tableCase.getItems().get(tableCase.getSelectionModel().getSelectedIndex()).getFallId();
        int index = 3;
        Database.retainCase(tc, caseID, index);
    }


    public void toLawyerPage(ActionEvent e) throws IOException{

        boolean input = true;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Screen_lawyer.fxml"));
        root = loader.load();

        Controller_lawyer controller_lawyer = loader.getController();
        controller_lawyer.setVis(input);

        //root = FXMLLoader.load(getClass().getResource("Screen_lawyer.fxml"));
        //programın kullandığı pencereye erişim
        stage = (Stage) btnManagerToMyPage.getScene().getWindow();
        stage.setScene(new Scene(root));
    }


    public void showLawyerDetails(ActionEvent e) throws IOException {
        int tc = tableLawyers.getItems().get(tableLawyers.getSelectionModel().getSelectedIndex()).getTcNummer();
        String name = "Lawyer Details";
        this.toPersonPage(tc, -1, name);
    }

    private void toPersonPage(int tc, int index, String name) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Screen_person.fxml"));
        root = loader.load();
        Controller_person controller_person = loader.getController();
        controller_person.setText(tc, index, name);
        stage = (Stage) btn_logout.getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }


    public void logout(ActionEvent e) throws IOException {
        Optional<ButtonType> isConfirmed = globalNotifications.confirmationDialog("Confirmation", "Are you sure to EXIT?");
        if(isConfirmed.get() == ButtonType.OK){
            System.out.println("ok buton yıklandı");
            root = FXMLLoader.load(getClass().getResource("Screen_login.fxml"));
            //programın kullandığı pencereye erişim
            stage = (Stage) btn_logout.getScene().getWindow();
            stage.setScene(new Scene(root));
        }else{
            System.out.println("cacel buton yıklandı");
        }
    }

    public void caseEdit(ActionEvent e) throws SQLException {
        btn_save_case.setVisible(true);
        btn_case_edit.setVisible(false);
        tf_case_state_m.setEditable(true);
        ta_case_description_m.setEditable(true);
    }

    public void saveCase(ActionEvent e) throws SQLException {
        btn_save_case.setVisible(false);
        btn_case_edit.setVisible(true);
        int caseID = tableCase.getItems().get(tableCase.getSelectionModel().getSelectedIndex()).getFallId();
        if(tf_case_state_m.getText() == null || tf_case_state_m.getText().length() < 3){
            tf_case_state_m.setText("on-going");
        }
        Database.edit_case(tf_case_date_m.getText(),tf_case_code_m.getText(),tf_case_class_m.getText(),tf_case_state_m.getText(),
                ta_case_description_m.getText(),caseID);
        tf_case_state_m.setEditable(false);
        ta_case_description_m.setEditable(false);
    }



}
