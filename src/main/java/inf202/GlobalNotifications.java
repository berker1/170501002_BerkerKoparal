package inf202;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class GlobalNotifications {

    public void informationDialog(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION); //info vs warning = icon
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void warningDialog(String title, String content){
        Alert alert = new Alert(Alert.AlertType.WARNING); //info vs warning = icon
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void errorDialog(String title, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR); //info vs warning = icon
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public Optional<ButtonType> confirmationDialog(String title, String content){
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        alert2.setTitle(title);
        alert2.setHeaderText(null);
        alert2.setContentText(content);

        return alert2.showAndWait();

    }

}
