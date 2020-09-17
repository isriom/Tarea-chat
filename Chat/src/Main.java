import javax.swing.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to the chat system");
        System.out.println("please enter your name:");
        String scan =Main.write();
        System.out.println(scan);
        System.out.println("enter server port");
        int port= Integer.parseInt(Main.write());
        Main.display();
        try{
            Server network =new Server(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String write(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    public static void recieve(String data){
        boolean port_error= data.equals("port_error");
        boolean port_accept= data.equals("port_accepted");
        boolean connect= data.equals("connect?");
        if (port_error){
            System.out.println("This port see that is not working");
        }
        else if(port_accept){
            System.out.println("This port is working, server is running, connecting");

        }
        else if (connect){
            System.out.println("Connect to server?");

        }

    }
    public static JFrame chat=new JFrame("Client");//creating instance of JFrame

    public static void display(){
        JTextArea readArea= new JTextArea();
        readArea.setBounds(10,10, 380,300);
        Main.chat.setSize(400,400);
        Main.chat.setLayout(null);
        Main.chat.setVisible(true);
        Main.chat.add(readArea);
    }
}
