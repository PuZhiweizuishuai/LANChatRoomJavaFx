package ChatMessage.SignIn;

import ChatMessage.Login.DragUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SignIn extends Application {
    //private Stage stage = new Stage();

    public SignIn() {
        try {
            Stage stage = new Stage();
            start(stage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("SignInUI.fxml"));
        primaryStage.setTitle("Chat Message注册");
        Scene scene = new Scene(root,960,540);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        DragUtil.addDragListener(primaryStage, root);
        primaryStage.show();
    }

    /*public void showWindow() throws Exception {
        start(stage);
    }*/
}
