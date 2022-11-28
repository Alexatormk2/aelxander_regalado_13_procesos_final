import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;


public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        //Conectamos al cliente
        Socket socket = new Socket("localhost", 5000);
        // Creamos los flujos
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        System.out.println("Leemos la clave");
        //obtenemos la clave publica
        PublicKey clave=(PublicKey) ois.readObject();
        System.out.println("La clave recibida es: "+clave);

        String mensaje= ois.readObject().toString();
        System.out.println("mensaje: "+ mensaje);
        System.out.println("Verifico firma");
       mensaje="ee";

//        VERIFICACIÓN DE LA FIRMA

//AL OBJETO signature sE LE suministra los datos a verificar
        Signature verificadsa = Signature.getInstance("SHA1WITHRSA");
        verificadsa.initVerify(clave);

        verificadsa.update(mensaje.getBytes());
        byte[] firma= (byte[]) ois.readObject();
        boolean check = verificadsa.verify(firma);
//
////       Compruebo la veracidad de la firma
        if (check)
            System.out.println("El mensaje es auténtico");
        else System.out.println("Intento de falsificación");
//
//
//
//
    }
}

