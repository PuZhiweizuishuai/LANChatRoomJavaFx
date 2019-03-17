package SettingServer;

import ChatMessage.Login.DragUtil;
import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SettingServerIP  extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SettingServerIP.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("ChatMessages设置");
        Scene scene = new Scene(root, 960, 640);
        primaryStage.setScene(scene);
        // 窗口样式调整
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        // 设置窗口大小不可调整
        primaryStage.setResizable(false);
        // 窗口移动
        DragUtil.addDragListener(primaryStage, root);

        primaryStage.show();
    }





    public static void main(String[] args) {
        launch(args);
    }
}