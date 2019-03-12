package ChatMessage.Setting;

import ChatMessage.Login.DragUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SettingUIMain extends Application {
    private Stage stage = new Stage();
    private FXMLLoader fxmlLoader;
    private Parent root;
    public SettingUIMain(FXMLLoader fxmlLoader, Parent root) {
        this.fxmlLoader = fxmlLoader;
        this.root = root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("设置");
        Scene scene = new Scene(root, 960, 540);
        primaryStage.setScene(scene);
        // 窗口样式调整
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        // 设置窗口大小不可调整
        primaryStage.setResizable(false);
        // 窗口移动
        DragUtil.addDragListener(primaryStage, root);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.getIcons().add(new Image("/resources/images/setting.png"));
        primaryStage.show();
    }



    public void showWindow() {
        try {
            start(stage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
