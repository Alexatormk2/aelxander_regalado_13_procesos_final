import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class Servidor {


    public static void main(String[] args) throws IOException {
        ServerSocket s;
        Socket c;
        s = new ServerSocket(5000);
        System.out.println("Servidor firma iniciado");
        while (true) {
            c = s.accept(); //esperando cliente
            Hilo hilo = new Hilo(c);
            hilo.start();
        }
    }
}