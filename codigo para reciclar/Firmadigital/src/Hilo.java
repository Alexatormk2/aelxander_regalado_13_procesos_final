import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            // PASO 1, FIRMAR
            // Creamos el par de claves usando el algoritmo RSA
            KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
            KeyPair par = keygen.generateKeyPair();
            PrivateKey privada = par.getPrivate();
            PublicKey publica = par.getPublic();

            //Creamos los flujos
            ObjectOutputStream oos = new ObjectOutputStream(c.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(c.getInputStream());
            //mandamos la clave publica

            oos.writeObject(publica);


            // Creamos la firma digital
            System.out.println("enviamos el mensaje para comprobar");
            String mensaje = "Este mensaje va a ser firmado";

            oos.writeObject(mensaje);
//
//        //  FIRMA CON CLAVE PRIVADA EL MENSAJE
////AL  OBJETO Signature SE LE SUMINISTRAN LOS DATOS A FIRMAR
            Signature dsa = Signature.getInstance("SHA1WITHRSA");
            dsa.initSign(privada);

            dsa.update(mensaje.getBytes());
            byte[] firma = dsa.sign(); //MENSAJE FIRMADO
            oos.writeObject(firma);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}

