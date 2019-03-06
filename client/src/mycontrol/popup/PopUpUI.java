package mycontrol.popup;

import ChatMessage.Login.DragUtil;
import ChatMessage.Main.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * @author Pu Zhiwei
 * */
public class PopUpUI extends Application {
    private String title = "";
    private String text = "";
    public PopUpUI() {

    }

    public PopUpUI(String title, String text) {
        this.title = title;
        this.text = text;
        Stage stage = new Stage();
        try {
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Dimension sceneSize = Toolkit.getDefaultToolkit().getScreenSize();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PopUpUI.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle(title);
        double width = sceneSize.width * 0.2;
        if(width <= 320)
            width = sceneSize.width * 0.25;
        // System.out.println(width);
        Scene scene = new Scene(root, width, sceneSize.height * 0.2);
        primaryStage.setScene(scene);
        // 设置界面一直在最上面显示
        primaryStage.setAlwaysOnTop(true);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        PopUpUIControl popUpUIControl = fxmlLoader.getController();
        popUpUIControl.setText(title, text);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
