package ChatMessage.Main;

import java.awt.*;
import java.lang.Exception;

import ChatMessage.Login.DragUtil;
import ChatMessage.communication.Communication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author PuZhiwei
 * 默认群聊主界面
 * */
public class Main extends Application {
    private Stage stage = new Stage();

    private FXMLLoader fxmlLoader;
    private Parent root;
    public Main(FXMLLoader fxmlLoader, Parent root) {
        this.fxmlLoader = fxmlLoader;
        this.root = root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 获取屏幕大小
        Dimension sceneSize = Toolkit.getDefaultToolkit().getScreenSize();
        //root = fxmlLoader.load();
        primaryStage.setTitle("群聊");
        Scene scene = new Scene(root,sceneSize.width * 0.8,sceneSize.height*0.8);
        scene.getStylesheets().add(getClass().getResource("MainCss.css").toExternalForm());
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        DragUtil.addDragListener(primaryStage, root);
        primaryStage.getIcons().add(new Image("/resources/images/Icon.png"));
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }

    public void showWindow() {
        try {
            start(stage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
