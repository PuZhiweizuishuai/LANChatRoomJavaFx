package ChatMessage.Main;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;

import java.net.URL;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

public class MainUIControl implements Initializable {
    /**
     * 外部界面
     * */
    @FXML
    private GridPane rootPane;

    @FXML
    private ColumnConstraints rightCol;

    @FXML
    private ColumnConstraints leftCol;

    @FXML
    private RowConstraints topRow;

    @FXML
    private RowConstraints centreRow;

    @FXML
    private RowConstraints downRow;

    /**
     * 文本输入界面
     * */
    @FXML
    private ColumnConstraints inputChatLeftCol;

    @FXML
    private ColumnConstraints inputChatCentreCol;

    @FXML
    private ColumnConstraints inputChatRightCol;

    @FXML
    private RowConstraints inputChatTopRow;

    @FXML
    private RowConstraints inputChatCentreRow;

    @FXML
    private RowConstraints inputChatDownRow;

    @FXML
    private AnchorPane inputChatAnchor;

    @FXML
    private JFXButton sendButton;

    @FXML
    private TextArea inputText;

    @FXML
    private Pane test;

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        // 获取屏幕大小用于自适应窗口
        Dimension sceenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double sceenWidth = sceenSize.width * 0.8;
        double sceenHeight = sceenSize.height * 0.8;

        // 外部界面大小
        rootPane.setPrefWidth(sceenWidth);
        rootPane.setPrefHeight(sceenHeight);
        rightCol.setPrefWidth(sceenWidth * 0.65);
        leftCol.setPrefWidth(sceenWidth - (sceenWidth * 0.65));
        topRow.setPrefHeight(sceenHeight * 0.1);
        centreRow.setPrefHeight(sceenHeight * 0.65);
        downRow.setPrefHeight(sceenHeight * 0.25);

        // 文本输入界面
        double inputSceenWidth = sceenWidth * 0.65;
        double inputSceenHight = sceenHeight * 0.25;
        inputChatLeftCol.setPrefWidth(inputSceenWidth * 0.05);
        inputChatCentreCol.setPrefWidth(inputSceenWidth * 0.9);
        inputChatRightCol.setPrefWidth(inputSceenWidth * 0.05);

        inputChatTopRow.setPrefHeight(inputSceenHight * 0.25);
        inputChatCentreRow.setPrefHeight(inputSceenHight * 0.6);
        inputChatDownRow.setPrefHeight(inputSceenHight * 0.15);

        inputChatAnchor.setPrefSize(inputSceenWidth * 0.9, inputSceenHight * 0.6);

        sendButton.setPrefSize(inputSceenWidth * 0.15, inputSceenHight * 0.3);
        // 自动换行
        inputText.setWrapText(true);
        inputText.setPrefWidth(inputSceenWidth * 0.72);




        test.setPrefHeight(sceenHeight * 0.1);
        System.out.println(rootPane.getPrefHeight() + "         " + rootPane.getPrefWidth());
        System.out.println(rightCol.getPrefWidth() + "          " + leftCol.getPrefWidth());
        System.out.println(topRow.getPrefHeight());
        System.out.println(test.getPrefHeight());
    }

    @FXML
    public void test(ActionEvent event) {
        System.out.println(rootPane.getHeight());
    }

}
