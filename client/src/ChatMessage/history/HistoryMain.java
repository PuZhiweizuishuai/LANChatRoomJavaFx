package ChatMessage.history;

import ChatMessage.Login.DragUtil;
import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HistoryMain extends Application {
    private FXMLLoader fxmlLoader;
    private Parent root;
    private HistoryControl historyControl;
    public HistoryMain(FXMLLoader fxmlLoader, Parent root, HistoryControl historyControl) {
        this.fxmlLoader = fxmlLoader;
        this.root = root;
        this.historyControl = historyControl;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("历史记录");
        Scene scene = new Scene(root, 960, 540);
        primaryStage.setScene(scene);
        JFXButton jfxButton = historyControl.getMovebutton();
        // 窗口样式调整
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        // 窗口移动
        DragUtil.addDragListener(primaryStage, jfxButton);

        DragUtil.addDrawFunc(primaryStage, root);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.getIcons().add(new Image("/resources/images/history.png"));
        primaryStage.show();
    }



    public void showWindow(Stage stage) {
        try {
            if(stage.getScene() != null) {
                stage.show();
            } else {
                start(stage);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}