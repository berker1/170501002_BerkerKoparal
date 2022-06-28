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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Controller_person {

    GlobalNotifications globalNotifications = new GlobalNotifications();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField tf_personAge, tf_personContact, tf_personMail, tf_personName, tf_personProfession,
            tf_personSex, tf_personSurname, tf_personTC, tf_personUserName, tf_personUserPass;
    @FXML
    private Button btn_savePerson, btn_editPerson, btn_backToAdmin;
    @FXML
    private Text  txt_personInfo;

    private int indexPerson = 0;
    private String oldTC;
    private int newSave = 0;

    public void setIndexPerson(int indexPerson) {
        this.indexPerson = indexPerson;
    }

    public int getIndexPerson() {
        return indexPerson;
    }

    public void setText(String tc, int index, String name){
        // tc -1 ise kayıt yapılacak
        // tc -1 değilse details açılacak, index 3 lavyer details, index 2 manager details
        oldTC = tc;
        setIndexPerson(index);
        if(tc != "-1"){
            makeNotEditablePerson();
            btn_savePerson.setVisible(false);
            btn_savePerson.setDisable(true);
            txt_personInfo.setText(name);
            if(index == 3){
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
            }else{
                Manager manager = Database.getManagerDetails(tc);
                tf_personName.setText(manager.getVorname());
                tf_personSurname.setText(manager.getNachname());
                tf_personTC.setText(String.valueOf(manager.getTcNummer()));
                tf_personSex.setText(manager.getGeschlecht());
                tf_personProfession.setText(manager.getBranche());
                tf_personAge.setText(String.valueOf(manager.getAlter()));
                tf_personMail.setText(manager.getEmail());
                tf_personContact.setText(manager.getKontaktNummer());
                tf_personUserName.setText(manager.getUserName());
                tf_personUserPass.setText(manager.getUserPassword());
            }

        }else {
            btn_editPerson.setVisible(false);
            btn_editPerson.setDisable(true);
            newSave = 1;
           if (index == 2){
               txt_personInfo.setText("Add a new Manager");
           }else{
               txt_personInfo.setText("Add a new Lawyer");
           }
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
        //tf_personUserName.setEditable(true);
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
        Anwalt lawyer = new Anwalt(tf_personName.getText(), tf_personSurname.getText(),tf_personTC.getText(),
                tf_personMail.getText(), tf_personContact.getText(),tf_personSex.getText(), Integer.parseInt(tf_personAge.getText()),
                tf_personUserName.getText(),tf_personUserPass.getText(), tf_personProfession.getText() );
        return lawyer;
    }

    private Manager getManager(){
    Manager  manager = new Manager(tf_personName.getText(), tf_personSurname.getText(), tf_personTC.getText(),
            tf_personMail.getText(), tf_personContact.getText(),tf_personSex.getText(), Integer.parseInt(tf_personAge.getText()),
            tf_personUserName.getText(),tf_personUserPass.getText(), tf_personProfession.getText()) ;
    return manager;
    }

    public void savePerson(ActionEvent e) throws IOException, SQLException {
        System.out.println("save person baslıldı");
        System.out.println("index : " + indexPerson);
        System.out.println("tc uygun mu :" + Person.checkTC(tf_personTC.getText())); //ünlem var terse
        boolean control = false;

        /////////
        if(tf_personUserPass.getText().length() > 4){
            if(tf_personName.getText().length() > 2 && tf_personSurname.getText().length() >1){
                if(tf_personTC.getText() != null && Person.checkTC(tf_personTC.getText())){
                    System.out.println("tc de hatasız");
                    control = true;
                }else {
                    globalNotifications.errorDialog("Error", "TC must be 11 Digits ");
                }
            }else {
                globalNotifications.errorDialog("Error", "Username and USerSurname can not be null ");
            }
        }else{
            globalNotifications.errorDialog("Error", "Userpass length must be at least  5");
        }
        ////////
        if(control){
            System.out.println("control true");
            if(newSave == 0){
                System.out.println("newSafe 0");
                System.out.println("index person:" + indexPerson);
                if(indexPerson == 3){
                    System.out.println("index edit = 3");
                    Database.editPersonLawyer(getLawyer(), oldTC);
                    globalNotifications.informationDialog("INFORMATION", "Lawyer Successfully Saved");
                    makeNotEditablePerson();
                    btn_savePerson.setVisible(false);
                    btn_savePerson.setDisable(true);
                    btn_editPerson.setVisible(true);
                    btn_editPerson.setDisable(false);
                }else if(indexPerson == 2) {
                    System.out.println("index edit = 2");
                    Database.editPersonManager(getManager(), oldTC);
                    globalNotifications.informationDialog("INFORMATION", "Manager Successfully Saved");
                    makeNotEditablePerson();
                    btn_savePerson.setVisible(false);
                    btn_savePerson.setDisable(true);
                    btn_editPerson.setVisible(true);
                    btn_editPerson.setDisable(false);

                }
            }else{ // newsafe 1
                if(!Database.personExistiert(tf_personTC.getText())){
                    if(indexPerson == 2){
                        //manager save
                        Database.saveNewManager(getManager());
                        globalNotifications.informationDialog("INFORMATION", "Manager Successfully Saved");

                    }else{ // indexperson 3
                        //save lawyer
                        Database.saveNewLawyer(getLawyer());
                        globalNotifications.informationDialog("INFORMATION", "Lawyer Successfully Saved");
                    }
                }else{
                    globalNotifications.errorDialog("ERROR", "TC Nummer Existiert");
                }

            }

        }

    }

    public void editPerson(ActionEvent e) throws IOException {
        makeEditablePrson();
        btn_editPerson.setVisible(false);
        btn_editPerson.setDisable(true);
        btn_savePerson.setVisible(true);
        btn_savePerson.setDisable(false);
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
