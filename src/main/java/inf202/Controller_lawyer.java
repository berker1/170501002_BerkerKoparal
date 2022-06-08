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
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller_lawyer implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Button btnBackToManagerPage, btn_logout, btn_show_details;
    @FXML
    private TableView<Fall> caseTable;
    @FXML
    private TableColumn<Fall,String> cases;
    @FXML
    TextField tf_case_date, tf_case_class, tf_case_code, tf_case_state;
    @FXML
    TextArea ta_case_description;

    ObservableList<Fall> listM;

    int imdex = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        cases.setCellValueFactory(new PropertyValueFactory<Fall, String>("fallCode"));

        listM = Database.getDataFall();
        caseTable.setItems(listM);
    }

    public void showDetails() throws SQLException {
        String fallCode = caseTable.getItems().get(caseTable.getSelectionModel().getSelectedIndex()).getFallCode();
        Fall fallSelected = Database.show_case_details(fallCode);
        tf_case_date.setText(fallSelected.getCaseDate());
        tf_case_class.setText(fallSelected.getFallArt());
        tf_case_code.setText(fallSelected.getFallCode());
        tf_case_state.setText(fallSelected.getFallState());
        ta_case_description.setText(fallSelected.getFallDescription());
    }

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
