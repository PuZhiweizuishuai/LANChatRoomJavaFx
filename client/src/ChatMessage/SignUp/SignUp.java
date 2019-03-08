package ChatMessage.SignUp;

import ChatMessage.Login.DragUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SignUp extends Application {
    //private Stage stage = new Stage();

    public SignUp() {
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
        Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/SignUpUI.fxml"));
        primaryStage.setTitle("Chat Message注册");
        Scene scene = new Scene(root,960,540);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        DragUtil.addDragListener(primaryStage, root);
        primaryStage.getIcons().add(new Image("/resources/images/Icon.png"));
        primaryStage.show();
    }

    /*public void showWindow() throws Exception {
        start(stage);
    }*/
}
