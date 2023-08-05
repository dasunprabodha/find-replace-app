package lk.ijse.dep10.findreplace;

import com.sun.javafx.stage.EmbeddedWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class AppInitializer extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene
                (new FXMLLoader(getClass().getResource("/view/EditorView.fxml")).load()));
        primaryStage.setTitle("Find And Replace App");
        primaryStage.show();
        primaryStage.centerOnScreen();
    }
}
