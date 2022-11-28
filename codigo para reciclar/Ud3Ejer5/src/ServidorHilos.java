import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorHilos {


    public static void main(String[] args) throws IOException {
        int numeropuerto = 6000;// puerto

        ServerSocket servidor = new ServerSocket(numeropuerto);
        System.out.println("Esperando al cliente. . . . .");
while (true) {
    Socket cliente = servidor.accept();
    Hilo h =new Hilo(cliente);
    h.start();


}
    }

}
