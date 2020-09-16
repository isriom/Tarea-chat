import java.util.Scanner;

public class Main {

    public static String scan;

    public static void main(String[] args) {
        System.out.println("Welcome to the chat system");
        System.out.println("please enter your name:");

        Main.name();
        System.out.println(scan);
    }

    public static void name(){
        Scanner scanner = new Scanner(System.in);
        Main.scan=scanner.nextLine();
    }
}
