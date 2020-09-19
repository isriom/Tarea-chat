package Server;

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
        addButton.setBounds(300,350, 100,50);
        textBox.setBounds(50,10, 150,100);
        connectedUsers.setBounds(20,150, 250,100);
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
        serverScreen.setSize(400,400);
        serverScreen.setLayout(null);
        serverScreen.setVisible(true);
        Server.serverScreen=serverScreen;
    }
    public static void addUser() {
        try {
            System.out.println("asd");
            Socket clientSocket = Server.publicsocket.accept();
            System.out.println("asd");
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            Users user = new Users(clientSocket, output, input);
            System.out.println(user.getUserName());
            System.out.println(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
