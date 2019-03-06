package ChatMessage.Main;

import ChatMessage.communication.Communication;
import ChatMessage.user.Message;
import ChatMessage.user.MessageType;
import ChatMessage.user.SaveUser;
import ChatMessage.user.ServerIP;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import mycontrol.chatbox.MyChatBox;
import mycontrol.chatbox.OtherChatBox;
import mycontrol.popup.PopUpUI;
import mycontrol.userlist.UserListUI;


/**
 * @author PuZhiwei
 * */
public class MainUIControl implements Initializable {
    /**
     * MainUIControl 对象
     * */
    private static MainUIControl instance;

    /**
     * 通信基类对象
     * */
    private Communication comm;

    /**
     * 按钮
     */
    @FXML
    private JFXButton closeButton;

    @FXML
    private JFXButton minimizeButton;

    /**
     * 外部界面
     */
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
     */
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
     */
    @FXML
    private ColumnConstraints showMessagesLeftCol;

    @FXML
    private ColumnConstraints showMessagesCentreCol;

    @FXML
    private ColumnConstraints showMessagesRightCol;

    @FXML
    private ListView chatBoxList;

    @FXML
    private Label nameLabelTop;

    @FXML
    private ImageView headImageTop;

    /**
     * 时间
     * */
    private  Date lastTime;

    /**
     * 左侧联系人
     */
    @FXML
    private JFXListView contactsList;

    /**
     * 右侧信息
     * */
    @FXML
    private ImageView lefttMyHead;
    @FXML
    private Label leftNameLabel;

    /**
     * 个人信息
     * */
    private String headImagePath;
    private String userName;

    /**
     * socket网络发送消息
     */
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;


    /**
     * 获取屏幕大小用于自适应窗口
     * */
    private Dimension sceneSize = Toolkit.getDefaultToolkit().getScreenSize();
    private double sceneWidth = sceneSize.width * 0.8;
    private double sceneHeight = sceneSize.height * 0.8;

    /**
     * 获取 LoginControl 对象
     * */
    public MainUIControl() {
        instance = this;
    }

    /**
     * 获得 MainUIControl 对象
     * */
    public static MainUIControl getInstance() {
        return instance;
    }


    /**
     * 注册通信线程对象
     * */
    public void setConnection(Communication comm) {
        this.comm = comm;
    }


    /**
     * 初始化
     */
    @Override
    public void initialize(URL location, ResourceBundle resource) {
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

        double buttonHight = (sceneHeight * 0.1) / 2 - 25;
        nameLabelTop.setText("群聊中：");
        headImageTop.setLayoutY(buttonHight - 5);
        headImageTop.setFitWidth(60);
        headImageTop.setFitHeight(60);
        headImageTop.setImage(new Image("@../../images/GroupChat.png"));

        // 按钮
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

        lefttMyHead.setImage(new Image("@../../images/508035880.jpg"));
        leftNameLabel.setText(SaveUser.getLoginUserName());
    }

    /**
     * 向服务器发送消息
     * 鼠标事件
     */
    @FXML
    private void sendMessages(ActionEvent event) {
        if(sendMessageToServer()){
            showMyMessage();
        }
    }

    /**
     * 发送消息快捷键
     * */
    @FXML
    public void sendMethod(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            if(sendMessageToServer()){
                showMyMessage();
            }
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
    /**
     * 显示我发出的消息
     * */
    public void showMyMessage() {
        String myMessage = inputText.getText();
        if (myMessage.equals("")) {
            System.out.println("未输入任何内容！");
        } else {
            getTime();
            int length = myMessage.length();
            int width;
            if (length <= 2){
                width = 28*3;
            } else if (length <= 28 && length > 2) {
                width = 28 * length;
            } else {
                width = 600;
            }
            int height;
            if (((length / 28) + 1) >= 3) {
                height = ((length / 28) + 1) * 35;
            } else {
                height = ((length / 28) + 1) * 40;
            }
            System.out.println( length + "       " +((length / 23) + 1));
            MyChatBox myChatBox = new MyChatBox();
            myChatBox.setMessage(myMessage);
            myChatBox.setHeightAndWidth(height,width);
            myChatBox.setHeadImageView("@../../images/508035880.jpg");
            chatBoxList.setNodeOrientation(NodeOrientation.valueOf("RIGHT_TO_LEFT"));
            chatBoxList.getItems().add(myChatBox);
            inputText.setText("");
        }
    }

    /**
     * 显示收到的消息
     * */
    public void showOtherMessage() {
        getTime();
        String otherMessage = inputText.getText();
        if (otherMessage.equals("")) {
            System.out.println("未输入任何内容！");
        } else {
            int length = otherMessage.length();
            int width;
            if(length <= 2) {
                width = 3*28;
            }else if (length <= 28 && length > 2) {
                width = 28 * length;
            } else {
                width = 600;
            }
            int height;
            if (((length / 28) + 1) >= 3) {
                height = ((length / 28) + 1) * 35;
            } else {
                height = ((length / 28) + 1) * 40;
            }
            OtherChatBox otherChatBox = new OtherChatBox();
            otherChatBox.setMessage(otherMessage);
            otherChatBox.setNameLabel("puzhiwei");
            otherChatBox.setHeightAndWidth(height, width);
            otherChatBox.setHeadImageView("@../../images/508035880.jpg");
            chatBoxList.setNodeOrientation(NodeOrientation.valueOf("LEFT_TO_RIGHT"));
            chatBoxList.getItems().add(otherChatBox);
            inputText.setText("");
        }
    }

    /**
     * 获取当前时间,并显示在聊天界面
     * */
    public String getTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = df.format(date);
        if(lastTime == null || date.getTime() - lastTime.getTime() > 180000) {
            Label timeLabel = new Label(time);
            timeLabel.setFont(new Font("Microsoft YaHei",15));
            chatBoxList.getItems().add(timeLabel);
            lastTime = date;
            return time;
        }
        return time;
    }


    /**
     * 点击联系人后的事件
     * 以下两个都可以用
     * */
    public void setContactsList() {
        contactsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                UserListUI userListUI = (UserListUI) contactsList.getSelectionModel().getSelectedItem();
                System.out.println(userListUI.getName());
            }
        });
    }

    /**
     * 切换联系人
     * */
    public void handleMouseClickContactsList(MouseEvent arg0) {
        //TODO
        // 代补充聊天记录方法
        UserListUI userListUI = (UserListUI) contactsList.getSelectionModel().getSelectedItem();
        if(userListUI.getName().equals("    群聊")) {
            nameLabelTop.setText("群聊中：");
        } else {
            nameLabelTop.setText("与" + userListUI.getName() + "  聊天中！");
        }

        headImageTop.setImage(new Image(userListUI.getImagePath()));
    }


    /**
     * 发送消息
     * */
    private boolean sendMessageToServer() {
        if (inputText.getText().equals("")) {
            System.out.println("未输入任何内容！");
            return false;
        }
        try {
            socket = new Socket();
            // 防止超时
            socket.connect(new InetSocketAddress(ServerIP.IP,ServerIP.port), ServerIP.timeout);
            // 发送
            OutputStream outPut =new ObjectOutputStream(socket.getOutputStream());
            // 接收
            InputStream input = socket.getInputStream();
            //dataOutputStream = new DataOutputStream(outPut);
            dataInputStream = new DataInputStream(input);
            Message message = new Message(SaveUser.getLoginUserName(), inputText.getText(), MessageType.MSG);
            ((ObjectOutputStream) outPut).writeObject(message);
            socket.getOutputStream().flush();
            dataInputStream.close();
            socket.close();
            return true;
        } catch(Exception e) {
            new PopUpUI("错误","服务器异常，消息发送失败！");
            e.printStackTrace();
            return false;
        }
    }
}