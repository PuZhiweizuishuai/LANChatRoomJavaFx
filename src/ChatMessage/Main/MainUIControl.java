package ChatMessage.Main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import mycontrol.chatbox.OtherChatBox;
import mycontrol.userlist.UserListUI;


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
     * 消息显示界面
     * */
    @FXML
    private ColumnConstraints showMessagesLeftCol;

    @FXML
    private ColumnConstraints showMessagesCentreCol;

    @FXML
    private ColumnConstraints showMessagesRightCol;

    @FXML
    private ListView chatBoxList;

    /**
     * 左侧联系人
     * */
    @FXML
    private JFXListView contactsList;

    @FXML
    private Pane ceshiyixai;


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
        Dimension sceneSize = Toolkit.getDefaultToolkit().getScreenSize();
        double sceneWidth = sceneSize.width * 0.8;
        double sceneHeight = sceneSize.height * 0.8;

        // 外部界面大小
        rootPane.setPrefWidth(sceneWidth);
        rootPane.setPrefHeight(sceneHeight);
        rightCol.setPrefWidth(sceneWidth * 0.65);
        leftCol.setPrefWidth(sceneWidth - (sceneWidth * 0.65));
        topRow.setPrefHeight(sceneHeight * 0.1);
        centreRow.setPrefHeight(sceneHeight * 0.65);
        downRow.setPrefHeight(sceneHeight * 0.25);

        // 文本输入界面
        double inputSceneWidth = sceneWidth * 0.65;
        double inputSceneHeight = sceneHeight * 0.25;
        inputChatLeftCol.setPrefWidth(inputSceneWidth * 0.05);
        inputChatCentreCol.setPrefWidth(inputSceneWidth * 0.9);
        inputChatRightCol.setPrefWidth(inputSceneWidth * 0.05);

        inputChatTopRow.setPrefHeight(inputSceneHeight * 0.25);
        inputChatCentreRow.setPrefHeight(inputSceneHeight * 0.6);
        inputChatDownRow.setPrefHeight(inputSceneHeight * 0.15);

        inputChatAnchor.setPrefSize(inputSceneWidth * 0.9, inputSceneHeight * 0.6);

        sendButton.setPrefSize(inputSceneWidth * 0.15, inputSceneHeight * 0.3);
        // 自动换行
        inputText.setWrapText(true);
        inputText.setPrefWidth(inputSceneWidth * 0.72);

        // 聊天界面
        showMessagesLeftCol.setPrefWidth(inputSceneWidth * 0.025);
        showMessagesCentreCol.setPrefWidth(inputSceneWidth * 0.95);
        showMessagesRightCol.setPrefWidth(inputSceneWidth * 0.025);

        // 按钮
        double buttonHight = (sceneHeight * 0.1) / 2 - 25;
        closeButton.setLayoutY(buttonHight);
        minimizeButton.setLayoutY(buttonHight);

        UserListUI groupChat = new UserListUI();
        groupChat.setNameLabel("    群聊");
        groupChat.setHeadImageView("@../../images/GroupChat.png");
        contactsList.getItems().add(groupChat);
        for (int i = 0; i < 10; i++) {
            UserListUI userListUI = new UserListUI();
            userListUI.setNameLabel("    Image   " + i);
            userListUI.setHeadImageView("@../../images/508035880.jpg");
            contactsList.getItems().add(userListUI);
        }
    }
    /**
     * 向服务器发送消息
     * */
    @FXML
    private void sendMessages(ActionEvent event) {
        if(inputText.getText().equals("")) {
            System.out.println("未输入任何内容！");
        }else {
            System.out.println(inputText.getText());
            OtherChatBox otherChatBox = new OtherChatBox();
            otherChatBox.setMessage(inputText.getText());

            otherChatBox.setHeadImageView("@../../images/508035880.jpg");
            chatBoxList.getItems().add(otherChatBox);
            inputText.setText("");
        }
        /*try {
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
        }*/
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
