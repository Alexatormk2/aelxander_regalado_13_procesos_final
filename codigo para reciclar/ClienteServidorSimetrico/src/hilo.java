import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author eider
 */
public class hilo extends Thread {

    Socket c = new Socket();

    public hilo(Socket c) {
        this.c = c;

    }

    public void run() {
        try {
            byte[] mensajeRecibido = null;

            KeyGenerator keygen = null;
            Cipher desCipher = null;
            String mensajeRecibidoDescifrado = "";

            System.out.println("Obteniendo generador de claves con cifrado DES");
            try {
                keygen = KeyGenerator.getInstance("DES");
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("Generando clave");
            SecretKey key = keygen.generateKey();
            // //////////////////////////////////////////////////////////////
            // CONVERTIR CLAVE A STRING Y VISUALIZAR/////////////////////////
            // obteniendo la version codificada en base 64 de la clave

            System.out.println("La clave es: " + key);

            // //////////////////////////////////////////////////////////////
            // CREAR CIFRADOR Y PONER EN MODO DESCIFRADO//////////////////
            System.out.println("Obteniendo objeto Cipher con cifraddo DES");
            try {
                desCipher = Cipher.getInstance("DES");
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("Configurando Cipher para desencriptar");
            try {
                desCipher.init(Cipher.DECRYPT_MODE, key);
            } catch (InvalidKeyException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Enviamos la clave
            ObjectOutputStream oos = new ObjectOutputStream(c.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(c.getInputStream());
            oos.writeObject(key);
            try {
                do {
                    try {
                        //
                        mensajeRecibido = (byte[]) ois.readObject();

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(hilo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    mensajeRecibidoDescifrado = new String(desCipher.doFinal(mensajeRecibido));
                    System.out.println("El texto enviado por el cliente y descifrado por el servidor es : " + new String(mensajeRecibidoDescifrado));

                } while (!mensajeRecibidoDescifrado.equals("end"));

            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }

            // cierra los paquetes de datos, el socket y el servidor
            ois.close();
            oos.close();

            c.close();

            System.out.println("Fin de la conexion");


        } catch (IOException ex) {
            Logger.getLogger(hilo.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}
