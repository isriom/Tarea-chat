import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    private static Socket userSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static String name;

    public static void main(String[] args) {
        JFrame chatScreen=new JFrame("Client");
        JLabel intro1=new JLabel("Welcome to the chat system");
        intro1.setBounds(10,50,250,30);
        JLabel intro2=new JLabel("please enter your name");
        intro2.setBounds(10,100,150,30);
        JTextField intro3=new JTextField();
        intro3.setBounds(10,150,150,30);
        JLabel intro4=new JLabel("please server port");
        intro4.setBounds(10,200,150,30);
        JTextField intro5=new JTextField();
        intro5.setBounds(10,250,150,30);
        JButton introButton=new JButton("connect to server");
        introButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String User=intro3.getText();
                String serverport=intro5.getText();
                int port= Integer.parseInt(serverport);
                try{
                    Socket clientSocket = new Socket(InetAddress.getLocalHost(),port);
                    createAccount(clientSocket,intro3.getText());
                    chatScreen.setVisible(false);
                    chatScreen.dispose();
                    Main.display();
                } catch (Exception e) {
                    intro1.setBackground(Color.white);
                    intro1.setText("Error,try again");
                    intro1.setBounds(200,100,250,30);
                }

            }
        });
        introButton.setBounds(200,250,150,50);
        chatScreen.add(intro1);
        chatScreen.add(intro2);
        chatScreen.add(intro3);
        chatScreen.add(intro4);
        chatScreen.add(intro5);
        chatScreen.add(introButton);
        chatScreen.setSize(400,400);
        chatScreen.setLayout(null);
        chatScreen.setVisible(true);

    }
    public static String write(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    public static void display() throws InterruptedException {
        JFrame chatScreen=new JFrame("Client");
        JTextArea readArea= new JTextArea();
        readArea.setBounds(10,10, 380,300);
        chatScreen.setSize(400,400);
        chatScreen.setLayout(null);
        chatScreen.setVisible(true);
        chatScreen.add(readArea);
        Thread.sleep(2000);
        out.println(name);

    }
    public static void createAccount(Socket socket, String user) throws IOException {
        PrintWriter output = new PrintWriter(socket.getOutputStream());
        BufferedReader input = new BufferedReader(new InputStreamReader( socket.getInputStream()));
        userSocket=socket;
        out=output;
        in=input;
        name=user;
    }
}
