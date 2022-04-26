package inf202;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("verwaltung_screen.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Screen Log-In");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args){
        Database db = new Database();
        db.connection_db();

        launch();
    }

}
