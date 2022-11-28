


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author eider
 */
public class Hilo extends Thread{

    Socket c=new Socket();


    public Hilo(Socket c) {
        this.c= c;

    }

    public void run() {
        try {
            //Generamos el par de claves
            KeyPairGenerator keygen;

            keygen = KeyPairGenerator.getInstance("RSA");

            System.out.println("Generando par de claves");
            KeyPair par = keygen.generateKeyPair();
            PrivateKey privada=par.getPrivate();
            PublicKey publica=par.getPublic();

            //////////////////////////////////////////////////////////////


            //Creamos los flujos
            ObjectOutputStream oos = new ObjectOutputStream(c.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(c.getInputStream());
            //mandamos la clave publica

            oos.writeObject(publica);
            System.out.println("Enviamos la clave publica cuyo valor es: "+publica);

            //recibimos texto encriptado del cliente

            byte[] mensaje = (byte[]) ois.readObject();
            //preparamos el Cipher para descifrar
            System.out.println("El mensaje cifrado recibido es:"+new String( mensaje));

            Cipher descipher = Cipher.getInstance("RSA");
            descipher.init(Cipher.DECRYPT_MODE, privada);

            String mensaje_descifrado = new String(descipher.doFinal(mensaje));

            System.out.println("Mensaje descifrado con clave privada: " + mensaje_descifrado);

            oos.close();
            ois.close();
            c.close();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


}

