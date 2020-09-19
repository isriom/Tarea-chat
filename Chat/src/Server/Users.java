package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Users extends Thread {
    private Socket userSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String userName;

    public Users(Socket socket, PrintWriter output, BufferedReader input) {
        userSocket=socket;
        out=output;
        in=input;
        try {
            userName=in.readLine();
            Server.UsersList.addElement(this);
            Server.usersNameList.addElement(userName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while(true){
            try {
                String msg=in.readLine();
                SendToServer(userName+"\n"+msg);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public BufferedReader getIn() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public Socket getUserSocket() {
        return userSocket;
    }

    public String getUserName() {
        return userName;
    }

    public void SendToServer(String msg) {

    }
}
