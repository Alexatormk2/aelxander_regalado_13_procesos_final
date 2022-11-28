import java.net.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class Servidor
{
    final int PUERTO = 5000;
    //    private ServerSocket server;
//    private Socket socket;
    private byte[] mensajeRecibido;
    private KeyGenerator keygen;
    private Cipher desCipher;
    String mensajeRecibidoDescifrado="";
    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        // Crea el servidor
        Servidor s = new Servidor();
        s.initServer();
    }
    public void initServer() throws IOException, ClassNotFoundException
    {
        System.out.println("Inicializando servidor");
        // CREAR CLAVE DES///////////////////////////////////////////////
        System.out.println("Obteniendo generador de claves con cifrado DES");
        System.setProperty("javax.net.ssl.keyStore","AlmacenSSL.jks");
        System.setProperty("javax.net.ssl.keyStorePassword","123456");
        try
        {
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
        System.out.println("Obteniendo objeto Cipher con cifrado DES");
        try
        {
            desCipher = Cipher.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Configurando Cipher para desencriptar");
        try
        {
            desCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (InvalidKeyException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ////////////////////////////////////////////////////////////////
        // Crea el Socket de servicio
        SSLServerSocketFactory fac=(SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket server=(SSLServerSocket) fac.createServerSocket(PUERTO);
//        server = new ServerSocket(PUERTO);
//        socket = new Socket();
        // Espera conexión de un cliente
        System.out.println("Esperando conexion cliente");
        SSLSocket socket=(SSLSocket) server.accept();
//        socket = server.accept();
        // Enviamos la clave
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        oos.writeObject(key);
        try
        {
            do {
                //
                mensajeRecibido = (byte[]) ois.readObject();
                mensajeRecibidoDescifrado = new String(desCipher.doFinal(mensajeRecibido));
                System.out.println("El texto enviado por el cliente y descifrado por el servidor es : " +
                        new String(mensajeRecibidoDescifrado));
            } while (!mensajeRecibidoDescifrado.equals("end"));

        } catch (IllegalBlockSizeException ex)
        {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex)
        {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        // cierra los paquetes de datos, el socket y el servidor
        ois.close();
        oos.close();
        socket.close();
        server.close();
        System.out.println("Fin de la conexion");
    }
}
