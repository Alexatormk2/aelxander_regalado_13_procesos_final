import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class Server_Banco {

    final static int   puerto = 5500;




    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, ClassNotFoundException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        ServerSocket s;
        Socket c;
        s = new ServerSocket(5000);
        System.out.println("Servidor iniciado");
        while (true) {
            c = s.accept(); //esperando cliente
            Hilo_Banco hiloBanco = new Hilo_Banco(c);
            hiloBanco.start();
        }

    }

}
