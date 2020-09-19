package Server;

import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import javax.swing.*;

public class Server {
    /**
     * variables
     */
    public static ServerSocket publicsocket;//* socket
    public static JFrame serverScreen;
    public static DefaultListModel<Users> UsersList=new DefaultListModel<Users>();
    public static DefaultListModel<String> usersNameList= new DefaultListModel<>();
    public static DefaultListModel<String> log= new DefaultListModel<>();

    public static void main(String[] args) {
        JFrame initScreen= new JFrame("server");
        JLabel intro=new JLabel("Create server using port and localhost");
        JLabel intro2=new JLabel("port number:");
        JTextField input=new JTextField();
        JButton introButton=new JButton("create server");
        introButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    int port= Integer.parseInt(input.getText());
                    initScreen.dispose();
                    StartSocket(port);
                } catch (NumberFormatException e) {
                    intro.setText("Error,Please try another port");
                    e.printStackTrace();
                }
            }
        });
        intro.setBounds(10,50,250,30);
        intro2.setBounds(10,90,250,30);
        input.setBounds(10,120,250,30);
        introButton.setBounds(10,220,250,30);
        initScreen.add(intro);
        initScreen.add(intro2);
        initScreen.add(input);
        initScreen.add(introButton);
        initScreen.setSize(400,400);
        initScreen.setLayout(null);
        initScreen.setVisible(true);
        initScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    public static void StartSocket(int portnumber) {
        try {
            ServerSocket socket=  new ServerSocket(portnumber,50,InetAddress.getLocalHost());
            System.out.println(socket);
            System.out.println(socket.getLocalSocketAddress());
            Server.publicsocket=socket;
            Server.publicsocket.setSoTimeout(20000);
            Server.ComunicationScreen(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void RestartSocket(int portnumber) {
        try {
            ServerSocket socket=  new ServerSocket(portnumber,50,InetAddress.getLocalHost());
            System.out.println(socket);
            System.out.println(socket.getLocalSocketAddress());
            Server.publicsocket=socket;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void ComunicationScreen(ServerSocket socket){
        boolean WaitUser= true;
        JFrame serverScreen= new JFrame("Waiting for clients");//creating instance of JFrame
        JLabel textBox=new JLabel("State:Server online");
        JButton addButton=new JButton("Add client");
        JList<String> connectedUsers= new JList<>(usersNameList);
        JList<String> chatLog= new JList<>(log);
        addButton.setBounds(30,260, 100,50);
        textBox.setBounds(50,10, 150,100);
        connectedUsers.setBounds(20,100, 150,150);
        chatLog.setBounds(200,50, 250,300);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    textBox.setText("Waiting for client");
                    addUser();
                } catch (Exception e) {
                    Server.RestartSocket(publicsocket.getLocalPort());
                    e.printStackTrace();
                }
            }
        });
        serverScreen.add(textBox);
        serverScreen.add(connectedUsers);
        serverScreen.add(addButton);
        serverScreen.add(chatLog);
        serverScreen.setSize(400,400);
        serverScreen.setLayout(null);
        serverScreen.setVisible(true);
        Server.serverScreen=serverScreen;
        serverScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public static void addUser() {
        try {
            System.out.println("asd");
            Socket clientSocket = Server.publicsocket.accept();
            System.out.println("asd");
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            System.out.println("asd");

            Users user = new Users(clientSocket, output, input);
            System.out.println("asd");
            user.start();
            System.out.println(user.getUserName());
            System.out.println(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void RecieveMsg(Users user,String msg) throws IOException {
        for (int idUser=0;(idUser!=UsersList.getSize());idUser++){
            boolean condition1=idUser>=UsersList.getSize();
            if(condition1){
            break;}
            if (UsersList.elementAt(idUser)!=user){
                SendMsg(UsersList.elementAt(idUser),msg);
            }else{
                log.addElement(msg);
                SendMsg(user,msg);
            }
        }
    }
    public static void SendMsg(@NotNull Users user, String msg) {
        DataOutputStream out = user.getOut();
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
            UsersList.removeElement(user);
            usersNameList.removeElement(user.getUserName());
        }
    }
}
