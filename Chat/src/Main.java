import java.util.Scanner;

public class Main {

    public static String scan;

    public static void main(String[] args) {
        System.out.println("Welcome to the chat system");
        System.out.println("please enter your name:");
        String scan =Main.name();
        System.out.println(scan);
        System.out.println("enter a port");
        int port= Integer.parseInt(Main.name());
        Server network =new Server(port);
    }

    public static String name(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
