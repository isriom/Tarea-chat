import java.net.*;
import java.io.*;
import java.util.List;
import javax.swing.*;

public class Server {
    public Server(int portnumber ) {
        try {
            ServerSocket port=  new ServerSocket(portnumber);
            Main.recieve("port_accepted");
            Server.Comunication(port);
        } catch (BindException e){
            System.out.println("This port connect to a working server");
            Main.recieve("connect?");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("This port see that is not working ");
            Main.recieve("port_error");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("This port see that is out of range");
            Main.recieve("port_error");
            e.printStackTrace();
        }
    }
    public static JFrame screen=new JFrame("server");//creating instance of JFrame

    public static void Comunication(ServerSocket socketport) throws IOException {
        DefaultListModel<Socket> Users = new DefaultListModel<>();
        boolean WaitUser= true;
        Socket clientSocket = new Socket();
        clientSocket.connect(socketport.getLocalSocketAddress());
        //Users.addElement(clientSocket);

        JList<Socket> userslist= new JList<>(Users);
        userslist.setBounds(100,100, 250,100);
        Server.screen.setSize(400,400);
        Server.screen.setLayout(null);
        Server.screen.setVisible(true);
        Server.screen.add(userslist);

        try {
            while (WaitUser) {
                Users.addElement(socketport.accept());
                System.out.println(Users);
                WaitUser= Users.size()<3;

            }
            System.out.println("This port see that is out of range");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
