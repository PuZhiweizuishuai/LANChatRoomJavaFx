package mycontrol.chatbox;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Pu Zhiwei
 * 消息气泡
 * */
public class OtherChatBox extends HBox implements Initializable {
    @FXML
    private HBox rootHbox;

    @FXML
    private ImageView otherHeadImage;

    @FXML
    private TextFlow otherMessage;

    TextArea textArea = new TextArea();

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        Dimension sceneSize = Toolkit.getDefaultToolkit().getScreenSize();
        if(sceneSize.height >= 1000){
            otherHeadImage.setFitHeight(60.0);
            otherHeadImage.setFitWidth(60.0);
        } else {
            otherHeadImage.setFitHeight(40.0);
            otherHeadImage.setFitWidth(40.0);
        }
    }

    public OtherChatBox() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OtherChatBoxUI.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHeightAndWidth(double height, double width) {
        otherMessage.setPrefHeight(height);
        otherMessage.setPrefWidth(width);
        textArea.setPrefWidth(width);
        textArea.setPrefHeight(height);
    }

    public void setMessage(String name) {
        textArea.setText(name);
        textArea.setFont(Font.font("Microsoft YaHei",20));
        textArea.setEditable(false);
        textArea.setId("otherChatBox");
        textArea.setWrapText(true);
        otherMessage.getChildren().add(textArea);
    }




    public void setHeadImageView(String imagePath) {
        Image image = new Image(imagePath);
        otherHeadImage.setImage(image);
    }
}
