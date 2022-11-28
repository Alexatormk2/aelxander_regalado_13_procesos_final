import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Cliente {

    static final String HOST = "localhost";
    static final int PUERTO = 4999;

    public Cliente() throws IOException {


        try {


            Socket skCliente = new Socket(HOST, PUERTO);
            InputStream aux = skCliente.getInputStream();//Creamos un Input Stream para recibir las respuestas del server

            DataInputStream flujo = new DataInputStream(aux);
            System.out.println("Mensaje escrito desde el server"+flujo.readUTF());
            aux.close();
            skCliente.close();


        } catch (Exception e) {


            System.out.println(e.getMessage());


        }


    }

    public static void main(String[] arg) throws IOException {


        new Cliente();


    }
}
