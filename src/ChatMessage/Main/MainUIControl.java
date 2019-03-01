package ChatMessage.Main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * @author PuZhiwei
 * */
public class MainUIControl implements Initializable {
    /**
     * 按钮
     * */
    @FXML
    private JFXButton closeButton;

    @FXML
    private JFXButton minimizeButton;

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

    /**
     * 左侧联系人
     * */
    @FXML
    private JFXListView contactsList;

    /**
     * socket网络发送消息
     * */
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;



    /**
     * 初始化
     * */
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

        // 按钮
        double buttonHight = (sceenHeight * 0.1) / 2 - 25;
        closeButton.setLayoutY(buttonHight);
        minimizeButton.setLayoutY(buttonHight);


        Label label = new Label("ITEM");
        label.setId("TestLable");
        label.setTextFill(Paint.valueOf("blue"));
        label.getStyleClass().add("MainCss");
        //contactsList.setPrefHeight(sceenHeight * 0.75);
        contactsList.getItems().add(label);
        contactsList.getItems().add(new Label("Lable2"));
        System.out.println(rootPane.getPrefHeight() + "         " + rootPane.getPrefWidth());
        System.out.println(rightCol.getPrefWidth() + "          " + leftCol.getPrefWidth());
        System.out.println(closeButton.getLayoutX());
    }

    /**
     * 向服务器发送消息
     * */
    @FXML
    private void sendMessages(ActionEvent event) {
        try {
            socket = new Socket("10.6.49.224",9999);
            OutputStream outPut = socket.getOutputStream();
            InputStream input = socket.getInputStream();
            dataOutputStream = new DataOutputStream(outPut);
            dataInputStream = new DataInputStream(input);
            dataOutputStream.writeUTF(inputText.getText());
            System.out.println(inputText.getText());
            inputText.setText("");
            dataOutputStream.close();
            dataInputStream.close();
            socket.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void close() {
        //TODO
        System.exit(0);
    }

    public void minimize(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        // 最小化到任务栏
        stage.setIconified(true);
    }

}
