import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    static final int PUERTO = 5000;

    public Servidor() {


        try {

            ServerSocket skServidor = new ServerSocket(PUERTO);

            int numCli;

            for (numCli = 0; numCli < 3; numCli++) {
                Socket skCliente = skServidor.accept(); // Crea objeto
                System.out.println("Atiendo al cliente " + numCli);
                OutputStream aux = skCliente.getOutputStream();
                DataOutputStream flujo = new DataOutputStream(aux);
                flujo.writeUTF("Hola cliente " + numCli);
                flujo.close();
                skCliente.close();

            }

            System.out.println("Demasiados clientes por hoy");
            skServidor.close();


        } catch (Exception e) {


            System.out.println(e.getMessage());


        }



    }

    public static void main(String[] arg) {


        new Servidor();


    }

}
