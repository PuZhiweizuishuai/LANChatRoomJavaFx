package MainUI;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainUI.fxml"));
        Parent root = fxmlLoader.load();
        MainUIControll mainUIControll = fxmlLoader.getController();
        JFXButton move = mainUIControll.getMoveButton();
        primaryStage.setTitle("ChatMessages服务端");
        Scene scene = new Scene(root, 960, 540);
        primaryStage.setScene(scene);
        // 窗口样式调整
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        // 设置窗口大小不可调整
        primaryStage.setResizable(false);
        // 窗口移动
        DragUtil.addDragListener(primaryStage, move);

        DragUtil.addDrawFunc(primaryStage, root);

        primaryStage.show();
    }





    public static void main(String[] args) {
        launch(args);
    }
}

