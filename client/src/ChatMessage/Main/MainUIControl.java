package ChatMessage.Main;

import ChatMessage.communication.Communication;
import ChatMessage.user.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
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
import java.util.*;

import javafx.event.ActionEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jdk.nashorn.internal.ir.ReturnNode;
import mycontrol.chatbox.MyChatBox;
import mycontrol.chatbox.OtherChatBox;
import mycontrol.popup.PopUpUI;
import mycontrol.userlist.UserListUI;


/**
 * @author PuZhiwei
 * */
public class MainUIControl implements Initializable {
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
    private Label nowUserNumber;

    @FXML
    private JFXListView contactsList;

    private HashMap<String, UserListUI> contactsHashMap = new HashMap<>();
    private HashMap<String, Integer> contactMessageNumber = new HashMap<>();


    /**
     * 保存个人的消息记录
     * */
    private HashMap<String, LinkedList<Message>> messageLog = new HashMap<>();

    private LinkedList<Message> groupMessage = new LinkedList<>();

    private String ChatObject = "群聊";

    /**
     * 左侧信息
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
        headImageTop.setImage(new Image("/resources/images/GroupChat.png"));

        // 按钮
        closeButton.setLayoutY(buttonHight);
        minimizeButton.setLayoutY(buttonHight);

        lefttMyHead.setImage(new Image("/resources/images/508035880.jpg"));
        leftNameLabel.setText(SaveUser.getLoginUserName());
        System.out.println(SaveUser.getLoginUserName());
    }

    /**
     * 向服务器发送消息
     * 鼠标事件
     */
    @FXML
    private void sendMessages(ActionEvent event) {
        sendMessageToServer();
    }

    /**
     * 发送消息快捷键
     * */
    @FXML
    public void sendMethod(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            sendMessageToServer();
        }
    }

    @FXML
    private void close() {
        //TODO
        Platform.exit();
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
    public void showMyMessage(String myMessage) {
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
        MyChatBox myChatBox = new MyChatBox();
        myChatBox.setMessage(myMessage);
        myChatBox.setHeightAndWidth(height,width);
        myChatBox.setHeadImageView("/resources/images/508035880.jpg");
        chatBoxList.getItems().add(myChatBox);
        inputText.setText("");
    }

    /**
     * 显示收到的消息
     * */
    public void showOtherMessage(Message message) {
        if(message.getName().equals(SaveUser.getLoginUserName())) {
            return ;
        }
        getTime();
        String otherMessage = message.getMessage();
        int length = otherMessage.length();
        int width;
        if(length <= 2) {
            width = 3*28;
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
        Platform.runLater(()->{
            OtherChatBox otherChatBox = new OtherChatBox();
            otherChatBox.setMessage(otherMessage);
            otherChatBox.setNameLabel(message.getName());
            otherChatBox.setHeightAndWidth(height, width);
            otherChatBox.setHeadImageView("/resources/images/508035880.jpg");
            chatBoxList.setNodeOrientation(NodeOrientation.valueOf("LEFT_TO_RIGHT"));
            chatBoxList.getItems().add(otherChatBox);
        });
    }


    /**
     * 设置联系人右侧消息通知气泡显示消息数
     */
    public void setContactMessageNumber(String name) {
        if(name.equals(ChatObject)) {
            System.out.println(name);
            return;
        }
        contactMessageNumber.put(name, contactMessageNumber.get(name) + 1);
    }


    /**
     * 收到消息后设置消息提示并在点击用户名后显示相应的消息
     * */
    public void addOtherMessage(Message message) {
        UserListUI userListUI = contactsHashMap.get(message.getName());
        if(ChatObject.equals(message.getName())) {
            showOtherMessage(message);
        } else {
            Platform.runLater(()-> {
                userListUI.setMessageNumber(contactMessageNumber.get(message.getName()).toString());
            });
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
     * 切换联系人
     * */
    public void handleMouseClickContactsList(MouseEvent event) {
        UserListUI userListUI = (UserListUI) contactsList.getSelectionModel().getSelectedItem();
        if(ChatObject.equals(userListUI.getName())) {
            return;
        }
        System.out.println(ChatObject);
        if(userListUI.getName().equals("群聊")) {
            nameLabelTop.setText("群聊中：");
            ChatObject = "群聊";
            userListUI.closeMessageNumber();
            //switchChatUser();
        } else {
            nameLabelTop.setText("与 " + userListUI.getName() + " 聊天中！");
            ChatObject = userListUI.getName();
            userListUI.closeMessageNumber();
            switchChatUser();
        }
        contactMessageNumber.put(ChatObject, 0);
        headImageTop.setImage(new Image(userListUI.getImagePath()));
    }


    /**
     * 发送消息
     * */
    private void sendMessageToServer() {
        String myMessage = inputText.getText();

        if(!myMessage.isEmpty()) {
            Message message = new Message(SaveUser.getLoginUserName(),myMessage,MessageType.GROUPSMS);
            message.setTo(ChatObject);
            message.setHeadPicture("/resources/images/508035880.jpg");
            if(!ChatObject.equals("群聊")) {
                message.setTYPE(MessageType.MSG);
            }
            try {
                Communication.send(message);
                SaveMessage(message);
            } catch (IOException e) {
               new PopUpUI("提示：", "发送失败，请稍后再试");
               return;
            }
            Platform.runLater(()->{
                showMyMessage(myMessage);
            });
        }
    }

    public void setUserList(Message message) {
        Platform.runLater(()->{
            ArrayList<UserInformation> uifs = message.getUserList();
            int count = uifs.size();
            nowUserNumber.setText("当前用户："+ count +" 人");
            contactsList.getItems().clear();
            contactsHashMap.clear();
            contactMessageNumber.clear();
            UserListUI groupChat = new UserListUI();
            groupChat.setNameLabel("群聊");
            groupChat.setHeadImageView("/resources/images/GroupChat.png");

            contactsHashMap.put("群聊", groupChat);
            contactMessageNumber.put("群聊", 0);

            contactsList.getItems().add(groupChat);

            for(UserInformation uif : uifs) {
                if(uif.getUserName().equals(SaveUser.getLoginUserName())) {
                    continue;
                }
                UserListUI userListUI = new UserListUI();
                userListUI.setNameLabel(uif.getUserName());
                userListUI.setHeadImageView(uif.getUserPicture());
                contactsHashMap.put(uif.getUserName(), userListUI);
                contactMessageNumber.put(uif.getUserName(), 0);
                contactsList.getItems().add(userListUI);
            }
        });
    }

    /**
     * 保存除群聊以外的信息
     * */
    public void SaveMessage(Message message) {
        if(message.getTYPE() != MessageType.GROUPSMS) {
            String key = message.getName();
            if(key.equals(SaveUser.getLoginUserName())) {
                key = message.getTo();
            }
            if(messageLog.containsKey(key)) {
                messageLog.get(key).add(message);
            } else {
                LinkedList<Message> list = new LinkedList<>();
                list.add(message);
                messageLog.put(key, list);
            }
        }
    }

    /**
     * 处理界面切换时的消息显示
     * */
    public void switchChatUser() {
        chatBoxList.getItems().clear();
        LinkedList<Message> list = messageLog.get(ChatObject);
        if(list != null) {
            for(Message message : list) {
                System.out.println(message.getName() + "  " + message.getMessage());
                if(message.getName().equals(SaveUser.getLoginUserName())) {
                    showMyMessage(message.getMessage());
                } else if(message.getTYPE() != MessageType.GROUPSMS) {
                    showOtherMessage(message);
                }
            }
        }
    }


    /**
     * 保存群聊信息
     * */
    public void saveGroupMessage(Message message) {
        if(message.getTYPE() == MessageType.GROUPSMS) {
            groupMessage.add(message);
        }
    }


    public void newUserNotification(Message message) {
        //TODO 添加具体功能
    }
}