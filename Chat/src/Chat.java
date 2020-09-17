public class Chat {
    public Chat(){

    }
    public static void recieve(String data){
        boolean port_error= data.equals("port_error");
        boolean port_accept= data.equals("port_accepted");
        if (port_error){
            System.out.println("This port see that is not working");
        }
        else if(port_accept){
            System.out.println("This port is working, server is running, connecting");

        }


    }
}
