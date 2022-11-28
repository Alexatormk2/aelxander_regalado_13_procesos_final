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
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
public class Cliente
{
    final int PUERTO = 5000;
    //    private Socket cliente;
    private String mensaje = "";
    private String key;
    private Cipher desCipher;
    private String mensajeEnviado = "";
    private byte[] mensajeEnviadoCifrado;
    public static void main(String[] args) throws NoSuchAlgorithmException,NoSuchPaddingException, ClassNotFoundException
    {
        try {
            // Crea el cliente
            Cliente c = new Cliente();
            System.setProperty("javax.net.ssl.trustStore", "UsuarioAlmacenSSL");
            System.setProperty("javax.net.ssl.trustStorePassword", "123456");
            c.initClient();
        } catch (InvalidKeyException ex)
        {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void initClient() throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException
    {
        try
        {
            // /////////////////////////////////////////////////////////////////////////
            // Crea el socket y solicita conexión
            SSLSocketFactory fac=(SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket cliente=(SSLSocket) fac.createSocket("localhost", PUERTO);
//            cliente = new Socket("localhost", PUERTO);
            //creamos los flujos
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            //recogemos del flujo la clave simetrica
            SecretKey key = (SecretKey) ois.readObject();
            System.out.println("La clave es : " + key);
            System.out.println("Configurando Cipher para encriptar");
            desCipher = Cipher.getInstance("DES");
            desCipher.init(Cipher.ENCRYPT_MODE, key);
            Scanner sc = new Scanner(System.in);
            System.out.print("Recogiendo mensajes\n");
            do
            {
                System.out.print("Escribir nuevo texto para cifrar (para finalizar: end)\n");
                mensajeEnviado = sc.nextLine();
                // CIFRAR MENSAJE
                mensajeEnviadoCifrado = desCipher.doFinal(mensajeEnviado.getBytes());
                System.out.println("Mensaje cifrado: " + new String(mensajeEnviadoCifrado));
                oos.writeObject(mensajeEnviadoCifrado);
                System.out.println("Mensaje enviado al servidor");
            } while (!mensajeEnviado.equals("end"));
            // cierra salida, entrada y el socket
            oos.close();
            ois.close();
            cliente.close();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
