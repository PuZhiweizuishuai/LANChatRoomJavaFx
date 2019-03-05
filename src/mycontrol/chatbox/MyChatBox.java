package mycontrol.chatbox;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.scene.image.ImageView;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Pu Zhiwei
 * 我的消息气泡
 * */
public class MyChatBox extends HBox implements Initializable {
    @FXML
    private HBox rootHbox;

    @FXML
    private ImageView myHeadImage;

    @FXML
    private TextFlow myMessage;

    private TextArea textArea = new TextArea();

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        Dimension sceneSize = Toolkit.getDefaultToolkit().getScreenSize();
        if(sceneSize.height >= 1000){
            myHeadImage.setFitHeight(60);
            myHeadImage.setFitWidth(60);
        } else {
            myHeadImage.setFitHeight(40);
            myHeadImage.setFitWidth(40);
        }
    }

    public MyChatBox() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyChatBoxUI.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHeightAndWidth(double height, double width) {
        myMessage.setPrefHeight(height);
        myMessage.setPrefWidth(width);
        textArea.setPrefWidth(width);
        textArea.setPrefHeight(height);
    }



    public void setMessage(String name) {
        textArea.setText(name);
        textArea.setFont(Font.font("Microsoft YaHei",20));
        textArea.setEditable(false);
        textArea.setId("MyChatBox");
        textArea.setWrapText(true);
        textArea.setNodeOrientation(NodeOrientation.valueOf("LEFT_TO_RIGHT"));
        textArea.setScrollTop(0);
        myMessage.getChildren().add(textArea);
    }




    public void setHeadImageView(String imagePath) {
        javafx.scene.image.Image image = new Image(imagePath);
        myHeadImage.setImage(image);
    }
}
