package inf202;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Controller_case {

    private Stage stage;
    private Scene scene;
    private Parent root;

    Database db = new Database();

    @FXML
    Button btn_backToVerwaltung, btn_caseSave;
    @FXML
    private TextField tf_caseDate, tf_caseCode, tf_caseClass, tf_caseState;
    @FXML
    private TextArea ta_description;

    public void backToVerwaltung(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Screen_verwaltung.fxml"));
        //programın kullandığı pencereye erişim
        stage = (Stage) btn_backToVerwaltung.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void caseSave( ActionEvent e) throws SQLException {

        db.add_case(tf_caseDate.getText(), tf_caseCode.getText(), tf_caseClass.getText(),
                tf_caseState.getText(), ta_description.getText());
        System.out.printf(ta_description.getText());
    }
}
