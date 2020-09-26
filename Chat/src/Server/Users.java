package Server;

import java.io.*;
import java.net.Socket;

/**
 * Aux Class for server app.
 * Save users data and start the Socket listening.
 */
public class Users extends Thread {
    /**
     * User data
     */
    private Socket userSocket;
    private DataOutputStream out;
    private DataInputStream in;
    private String userName;

    /**
     * Builder.
     * register the user data.
     * @param socket user socket.
     * @param output output data stream of user.
     * @param input output  data input stream of user.
     */
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


    /**
     * Start the lister of users socket as a new Thread.
     * keep working while Server is running unntil socket is delete.
     */
    @Override
    public void run() {
        while(true){
            try {
                String msg=in.readUTF();
                SendToServer(msg);
            } catch (Exception e) {
                Server.UsersList.removeElement(this);
                Server.usersNameList.removeElement(this.getUserName());
                }
            }
        }

    /**
     * Getter.
     * @return respective object
     */
    public DataInputStream getIn() {
        return in;
    }
    /**
     * Getter.
     * @return respective object
     */
    public DataOutputStream getOut() {
        return out;
    }
    /**
     * Getter.
     * @return respective object
     */
    public Socket getUserSocket() {
        return userSocket;
    }
    /**
     * Getter.
     * @return respective object
     */
    public String getUserName() {
        return userName;
    }

    /**
     * method that comunicate to Server class, send the msg recieved form the lister.
     * send to server the user and the msg.
     * @see Server#RecieveMsg
     * @param msg msg as string.
     */
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
