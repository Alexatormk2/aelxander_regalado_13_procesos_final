import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Calculadora {

    public static void main(String[] args) throws IOException {
        ServerSocket s;
        Socket c;
        s = new ServerSocket(6000);
        System.out.println("Servidor iniciado");
        while (true) {
            c = s.accept(); //esperando cliente
            Hilo h = new Hilo(c);
            h.start();
        }
    }
}
