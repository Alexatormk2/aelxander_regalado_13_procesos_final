import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidorconhilos {

    public static void main(String[] args) throws IOException {
        ServerSocket servidor=new ServerSocket(6000);
        Socket cliente;

        while(true) {
            cliente = servidor.accept();
            HiloMayusculas hilo=new HiloMayusculas(cliente);
            hilo.start();
        }
    }
}
