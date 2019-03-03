package ChatMessage.Main;

import java.awt.*;
import java.lang.Exception;

import ChatMessage.Login.DragUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author PuZhiwei
 * 默认群聊主界面
 * */
public class Main extends Application {
    public Main() {
        try {
            Stage stage = new Stage();
            start(stage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 获取屏幕大小
        Dimension sceenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Parent root = FXMLLoader.load(getClass().getResource("MainUI.fxml"));
        primaryStage.setTitle("群聊");
        Scene scene = new Scene(root,sceenSize.width * 0.8,sceenSize.height*0.8);
        scene.getStylesheets().add(getClass().getResource("MainCss.css").toExternalForm());
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        DragUtil.addDragListener(primaryStage, root);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
