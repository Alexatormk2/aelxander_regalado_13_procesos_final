import java.io.*;
import java.net.Socket;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Cliente_Banco {
    public static String mensaje;
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    final static int puerto = 5500;

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        //Conectamos al cliente
        Socket socket = new Socket("localhost", puerto);
        // Creamos los flujos
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        System.out.println("Leemos la clave");
        //obtenemos la clave publica
        PublicKey clave = (PublicKey) ois.readObject();
        System.out.println("La clave recibida es: " + clave);


        System.out.println("sdkjgjdasdflhkds.kffkkf");
        ///recibimos mensaje del server
        mensaje = ois.readObject().toString();

        System.out.println(mensaje);
        //escribimos opcion del menu
        System.out.println("Escribe el numero de la opcion");
        int opcion = Integer.parseInt(br.readLine());
        oos.writeObject(opcion);
        System.out.println("opcion mandada");

        if (opcion == 3) {

            System.out.println("Cerrando sesion.........");
            oos.close();
            ois.close();
            socket.close();

        } else if (opcion == 1 || opcion == 2) {

            oos.writeObject(opcion);
            ///recibmimos clave 2
            PublicKey clave2 = (PublicKey) ois.readObject();
            System.out.println("Cargando menu inicio sesion");
            mensaje = ois.readObject().toString();
            System.out.println(mensaje);

            ///leemoms uusarios
            System.out.println();
            for (int k = 0; k < Server_Banco.ListaUsuarios.length; k++) {
                mensaje = ois.readObject().toString();
                System.out.println(mensaje);
            }
            opcion = Integer.parseInt(br.readLine());
            oos.writeObject(opcion);
            mensaje = ois.readObject().toString();
            System.out.println(mensaje);
        }
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

