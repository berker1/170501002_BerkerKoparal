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
import javafx.scene.layout.AnchorPane;
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

public class Controller_lawyer implements Initializable {

    GlobalNotifications globalNotifications = new GlobalNotifications();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button btnBackToManagerPage, btn_logout, btn_lawyer_details, btn_save_case, btn_case_edit;
    @FXML
    private Text txt_user;
    @FXML
    private TableView<Fall> caseTable;
    @FXML
    private TableColumn<Fall,String> cases;
    @FXML
    private TextField tf_case_date, tf_case_class, tf_case_code, tf_case_state;
    @FXML
    private TextArea ta_case_description;
    @FXML
    private AnchorPane ap_lawyerForBack;

    ObservableList<Fall> listM;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        cases.setCellValueFactory(new PropertyValueFactory<Fall, String>("fallCode"));

        listM = Database.getDataFall(Database.getUserTC(), 3);
        caseTable.setItems(listM);

        caseTable.setOnMouseClicked((MouseEvent event) ->{
            clickTableCases();
        });

        txt_user.setText(Database.getUserInfo().toUpperCase());
    }

    private void clickTableCases(){
        System.out.println("show case details clicked");
        int caseID = caseTable.getItems().get(caseTable.getSelectionModel().getSelectedIndex()).getFallId();
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
        tf_case_date.setText(fallSelected.getCaseDate());
    }


    public void setVis(boolean input){
        btnBackToManagerPage.setVisible(input);
        ap_lawyerForBack.setVisible(input);
    }

    public void toManagerPage() throws IOException {
        root = FXMLLoader.load(getClass().getResource("Screen_manager.fxml"));
        //programın kullandığı pencereye erişim
        stage = (Stage) btnBackToManagerPage.getScene().getWindow();
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

    public void showLawyerDetails(ActionEvent e) throws IOException {
        int tc = Database.getUserTC();
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

    public void caseEdit(ActionEvent e) throws SQLException {
        btn_save_case.setVisible(true);
        btn_case_edit.setVisible(false);
        tf_case_state.setEditable(true);
        ta_case_description.setEditable(true);
    }

    public void saveCase(ActionEvent e) throws SQLException {
        btn_save_case.setVisible(false);
        btn_case_edit.setVisible(true);
        int caseID = caseTable.getItems().get(caseTable.getSelectionModel().getSelectedIndex()).getFallId();
        if(tf_case_state.getText() == null || tf_case_state.getText().length() < 3){
            tf_case_state.setText("on-going");
        }
        Database.edit_case(tf_case_date.getText(),tf_case_code.getText(),tf_case_class.getText(),tf_case_state.getText(),
                ta_case_description.getText(),caseID);
        tf_case_state.setEditable(false);
        ta_case_description.setEditable(false);
    }

}
