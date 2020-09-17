import java.net.*;
import java.io.*;

public class Server {
    public Server(int portnumber ) {
        try {
            ServerSocket port=  new ServerSocket(portnumber);
            Chat.recieve("port_accepted");
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("This port see that is not working or out of range");
            Chat.recieve("port_error");
            e.printStackTrace();
        }
    }
}
