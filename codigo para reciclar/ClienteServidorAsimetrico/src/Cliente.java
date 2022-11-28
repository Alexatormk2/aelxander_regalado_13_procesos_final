import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Cliente {

    final static int puerto = 5000;

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        //Conectamos al cliente
        Socket socket = new Socket("localhost", puerto);
        // Creamos los flujos
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        System.out.println("Leemos la clave");
        //obtenemos la clave publica
        PublicKey clave=(PublicKey) ois.readObject();
        System.out.println("La clave recibida es: "+clave);

        //Ciframos con la clave publica

        System.out.println("Escribe texto para cifrar con clave publica del servidor");
        Scanner sc = new Scanner(System.in);
        String texto = sc.nextLine();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, clave);
        //directamente cifrarlo en un array de bytes, y no hacer conversiones a string
        byte[] mensaje = cipher.doFinal(texto.getBytes());
        oos.writeObject(mensaje);

        oos.close();
        ois.close();
        socket.close();

    }
}

