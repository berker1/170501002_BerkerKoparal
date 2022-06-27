package inf202;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Screen_login.fxml"));
            Image icon = new Image("E:\\IntelliJ\\projects\\inf202_projekt\\src\\main\\java\\images\\tau_logo.png");
            //Scene scene = new Scene(root, 320, 240);
            stage.setTitle("LAW OFFICE");
            stage.setScene(new Scene(root));
            stage.show();
            stage.getIcons().add(icon);
            stage.setResizable(false);
        }catch (IOException e){
            e.getMessage();
        }

    }

    public static void main(String[] args) throws SQLException {
        Database db = new Database();
        db.connection_db();
        launch();
    }

}
