package Server;

import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import javax.swing.*;

/**
 * Client.Main Class of Server app.
 * @see Users for aux class
 */
public class Server {
    /**
     * semi-global variables
     */
    public static ServerSocket publicsocket;//* socket
    public static JFrame serverScreen;
    public static DefaultListModel<Users> UsersList=new DefaultListModel<Users>();
    public static DefaultListModel<String> usersNameList= new DefaultListModel<>();
    public static DefaultListModel<String> log= new DefaultListModel<>();

    /**
     * Show the initial screen for server, when you start it.
     * @param args optionals
     */
    public static void main(String[] args) {
        JFrame initScreen= new JFrame("server");
        JLabel intro=new JLabel("Create server using port and localhost");
        JLabel intro2=new JLabel("port number: (left empty for auto)");
        JTextField input=new JTextField();
        JButton introButton=new JButton("create server");
        introButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!input.getText().equals("")){
                    try{
                        int port= Integer.parseInt(input.getText());
                        initScreen.dispose();
                        StartSocket(port);
                    } catch (NumberFormatException e) {
                        intro.setText("Error,Please try another port");
                        e.printStackTrace();
                    }
                }else{
                    RestartSocket(996);
                    initScreen.dispose();

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

    /**
     * Method used when you especific a port number to start a ServerSocket.
     * @param portnumber range 1-65535, recommended >995
     */
    public static void StartSocket(int portnumber) {
        try {
            ServerSocket socket=  new ServerSocket(portnumber,50,InetAddress.getLocalHost());
            Server.publicsocket=socket;
            Server.publicsocket.setSoTimeout(20000);
            Server.ComunicationScreen(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recursive method of StartSocket to auto select a port.
     * @see #StartSocket
     * @param portnumber start in 996
     */
    public static void RestartSocket(int portnumber) {
        try {
            ServerSocket socket=  new ServerSocket(portnumber,50,InetAddress.getLocalHost());
            Server.publicsocket=socket;
            Server.publicsocket.setSoTimeout(20000);
            Server.ComunicationScreen(socket);

        } catch (IOException e) {
            RestartSocket(portnumber+1);
            e.printStackTrace();
        }
    }

    /**
     * Display a UI of server when can be added/accepted users.
     * display the actual state, a list of Users, and the chatlog.
     * The button add client accept waiting clients.
     * @param socket Server socket.
     */
    public static void ComunicationScreen(ServerSocket socket){
        boolean WaitUser= true;
        JFrame serverScreen= new JFrame("Waiting for clients"+" port:"+publicsocket.getLocalPort());//creating instance of JFrame
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
                    textBox.setText("State:Server online");

                } catch (Exception e) {;
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

    /**
     * Method that register user data and start they socket listener.
     * get and create user :socket, input class, output class and Socket listener.
     * Use a User CLass to save the data as instances, and data input and data output streams to communication.
     * @see Users
     * @see #ComunicationScreen
     */

    public static void addUser() {
        try {
            Socket clientSocket = Server.publicsocket.accept();
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            Users user = new Users(clientSocket, output, input);
            user.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Comunication between Users listenig and Servers.
     * When recieve a msg from a Users listening send this to log and to another users.
     * @param user user who send the msg.
     * @param msg msg as string.
     * @throws IOException for msg thats is not a string or a closed  user´s Socket with open listening.
     */
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

    /**
     * Send the msg received to a user.
     * if the user Socket dont accept msg try to delete it
     * @param user User to send msg.
     * @param msg msg as string.
     * @throws IOException if fail closing the socket.
     */
    public static void SendMsg(@NotNull Users user, String msg) throws IOException {
        DataOutputStream out = user.getOut();
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
            UsersList.removeElement(user);
            usersNameList.removeElement(user.getUserName());
            user.getUserSocket().close();
        }
    }
}
