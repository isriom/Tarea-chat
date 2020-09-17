import java.net.*;
import java.io.*;
import javax.swing.*;

public class Server {
    public Server(int portnumber ) {
        try {
            ServerSocket socket=  new ServerSocket(portnumber,50,InetAddress.getLocalHost());
            System.out.println(socket);
            System.out.println(socket.getLocalSocketAddress());
            Server.Comunication(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static JFrame screen=new JFrame("server");//creating instance of JFrame
    public static void listen(ServerSocket socket) throws IOException {
        Users.addElement(socket.accept());
        }
    public static ServerSocket publicsocket;
    public static DefaultListModel<Socket> Users = new DefaultListModel<>();
    public static void Comunication(ServerSocket socket) {
        boolean WaitUser= true;
        JList<Socket> userslist= new JList<>(Server.Users);
        userslist.setBounds(100,100, 350,100);
        Server.screen.setSize(400,400);
        Server.screen.setLayout(null);
        Server.screen.setVisible(true);
        Server.screen.add(userslist);

        try {
            while (WaitUser) {
                Users.addElement(socket.accept());
                WaitUser= Users.size()==0;
                System.out.println(Users);
                System.out.println("Users");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Server.publicsocket=socket;
    }
}
