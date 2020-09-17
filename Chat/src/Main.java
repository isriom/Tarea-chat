import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String user="intro3.getText()";
        String serverport="intro5.getText()";
        JFrame chat=new JFrame("Client");
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
        JButton introButton=new JButton("create or connecto to server");
        introButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String User=intro3.getText();
                String serverport=intro5.getText();
                int port= Integer.parseInt(serverport);
                try{
                    chat.setVisible(false);
                    chat.dispose();
                    Main.display();
                    Server network=new Server(port);
                    Socket clientSocket = new Socket(InetAddress.getLocalHost(),port);
                    Server.listen(Server.publicsocket);

                } catch (Exception e) {
//                    chat.setVisible(false);
//                    chat.dispose();
                    try {
                        Socket clientSocket = new Socket(InetAddress.getLocalHost(),port);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
        introButton.setBounds(250,250,100,50);
        chat.add(intro1);
        chat.add(intro2);
        chat.add(intro3);
        chat.add(intro4);
        chat.add(intro5);
        chat.add(introButton);
        chat.setSize(400,400);
        chat.setLayout(null);
        chat.setVisible(true);

    }
    public static String write(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    public static JFrame chat=new JFrame("Client");
    public static void display(){
        JTextArea readArea= new JTextArea();
        readArea.setBounds(10,10, 380,300);
        Main.chat.setSize(400,400);
        Main.chat.setLayout(null);
        Main.chat.setVisible(true);
        Main.chat.add(readArea);
    }
}
