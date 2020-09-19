package Server;

import java.io.*;
import java.net.Socket;

public class Users extends Thread {
    private Socket userSocket;
    private DataOutputStream out;
    private DataInputStream in;
    private String userName;

    public Users(Socket socket, DataOutputStream output, DataInputStream input) {
        userSocket=socket;
        out=output;
        in=input;
        try {
            userName=in.readUTF();
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
                System.out.println("reciviendo");
                String msg=in.readUTF();
                SendToServer(msg);
                System.out.println(msg);
            } catch (Exception e) {
                System.out.println("asdaw");
                Server.UsersList.removeElement(this);
                Server.usersNameList.removeElement(this.getUserName());
                }
            }
        }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public Socket getUserSocket() {
        return userSocket;
    }

    public String getUserName() {
        return userName;
    }

    public void SendToServer(String msg)  {
        try {
            Server.RecieveMsg(this,msg);
        } catch (IOException e) {
            e.printStackTrace();
            Server.UsersList.removeElement(this);
            Server.usersNameList.removeElement(this.getUserName());
        }

    }
}
