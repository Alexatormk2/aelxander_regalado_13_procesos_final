import java.net.*;
import java.io.*;


public class Servidor
{
    final int                PUERTO          = 5000;
    private ServerSocket     Servidor;
    private Socket           socket;



    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        // Crea el servidor
        Servidor s = new Servidor();
        s.initServer();
    }

    public void initServer() throws IOException, ClassNotFoundException
    {


        System.out.println("Incializando servidor");


        // Crea el Socket de servicio
        Servidor = new ServerSocket(PUERTO);
        socket = new Socket();
        // Espera conexiÃ³n de un cliente
        System.out.println("Esperando conexion cliente");
        while (true) {
            socket = Servidor.accept();

            hilo hilo = new hilo(socket);
            hilo.start();
        }
    }
}
