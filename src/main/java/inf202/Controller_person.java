package inf202;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Controller_person {

    GlobalNotifications globalNotifications = new GlobalNotifications();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField tf_personAge, tf_personContact, tf_personInfo, tf_personMail, tf_personName, tf_personProfession,
            tf_personSex, tf_personSurname, tf_personTC, tf_personUserName, tf_personUserPass;
    @FXML
    private Button btn_savePerson, btn_editPerson, btn_backToAdmin;

    private static int indexPerson = 0;

    public void setText( int tc, int index, String name){
        if(tc != -1){
            makeNotEditablePerson();
            btn_savePerson.setVisible(false);
            Anwalt lawyer = Database.getLawyerDetails(tc);
            tf_personName.setText(lawyer.getVorname());
            tf_personSurname.setText(lawyer.getNachname());
            tf_personTC.setText(String.valueOf(lawyer.getTcNummer()));
            tf_personSex.setText(lawyer.getGeschlecht());
            tf_personProfession.setText(lawyer.getBranche());
            tf_personAge.setText(String.valueOf(lawyer.getAlter()));
            tf_personMail.setText(lawyer.getEmail());
            tf_personContact.setText(lawyer.getKontaktNummer());
            tf_personUserName.setText(lawyer.getUserName());
            tf_personUserPass.setText(lawyer.getUserPassword());
            tf_personInfo.setText(name);
        }else {
            indexPerson = index;
           if (index == 2){
               tf_personInfo.setText("Add a new Manager");
           }else{
               tf_personInfo.setText("Add a new Lawyer");
           }
            btn_editPerson.setVisible(false);
        }
    }

    public void makeEditablePrson(){
        tf_personName.setEditable(true);
        tf_personSurname.setEditable(true);
        tf_personTC.setEditable(true);
        tf_personSex.setEditable(true);
        tf_personProfession.setEditable(true);
        tf_personAge.setEditable(true);
        tf_personMail.setEditable(true);
        tf_personContact.setEditable(true);
        tf_personUserName.setEditable(true);
        tf_personUserPass.setEditable(true);
    }

    public void makeNotEditablePerson(){
        tf_personName.setEditable(false);
        tf_personSurname.setEditable(false);
        tf_personTC.setEditable(false);
        tf_personMail.setEditable(false);
        tf_personContact.setEditable(false);
        tf_personSex.setEditable(false);
        tf_personAge.setEditable(false);
        tf_personUserName.setEditable(false);
        tf_personUserPass.setEditable(false);
        tf_personProfession.setEditable(false);
    }

    private Anwalt getLawyer(){
        Anwalt lawyer = new Anwalt(tf_personName.getText(), tf_personSurname.getText(), Integer.parseInt(tf_personTC.getText()),
                tf_personMail.getText(), tf_personContact.getText(),tf_personSex.getText(), Integer.parseInt(tf_personAge.getText()),
                tf_personUserName.getText(),tf_personUserPass.getText(), tf_personProfession.getText() );
        return lawyer;
    }

    private Manager getManager(){
        Manager manager = new Manager(tf_personName.getText(), tf_personSurname.getText(), Integer.parseInt(tf_personTC.getText()),
                tf_personMail.getText(), tf_personContact.getText(),tf_personSex.getText(), Integer.parseInt(tf_personAge.getText()),
                tf_personUserName.getText(),tf_personUserPass.getText(), tf_personProfession.getText() );
        return manager;
    }

    public void savePerson(ActionEvent e) throws IOException, SQLException {
        if(indexPerson == 2){
            if(tf_personTC.getText() != null && !Person.checkTC(tf_personTC.getText())) {
                Database.editPersonLawyer(getLawyer());
            }else {
                globalNotifications.errorDialog("Error", "TC must be 11 Digits ");
            }
        }else if(indexPerson == 3) {
            if(tf_personTC.getText() != null && !Person.checkTC(tf_personTC.getText())){
                Database.editPersonManager(getManager());
            }else {
                globalNotifications.errorDialog("Error", "TC must be 11 Digits ");
            }
        }
    }

    public void editPerson(ActionEvent e) throws IOException {
        makeEditablePrson();
        btn_editPerson.setVisible(false);
        btn_savePerson.setVisible(true);
    }

    public void backToAdmin(ActionEvent e) throws IOException {
        int login = Database.getUserLogin();
        switch (login){
            case 1:
                root = FXMLLoader.load(getClass().getResource("Screen_verwaltung.fxml"));
                break;
            case 2:
                root = FXMLLoader.load(getClass().getResource("Screen_manager.fxml"));
                break;
            case 3:
                root = FXMLLoader.load(getClass().getResource("Screen_lawyer.fxml"));
                break;
        }
        //programın kullandığı pencereye erişim
        stage = (Stage) btn_backToAdmin.getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
    }

}
