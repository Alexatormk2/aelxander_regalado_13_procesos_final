import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EcoServidor {

    public static void main(String[] args) throws IOException {
        int Puerto=6000;
        ServerSocket servidor=new ServerSocket(Puerto);
        String cadena="";

        System.out.println("Esperando conexion");
        Socket clienteconectado=servidor.accept();
        System.out.println("Cliente conectado");

        //CREO FLUJO DE SALIDA AL CLIENTE

        PrintWriter fsalida=new PrintWriter(clienteconectado.getOutputStream(),true);

        //CREO FLUJO ENTRADA DEL CLIENTE

        BufferedReader fentrada= new BufferedReader (new InputStreamReader(clienteconectado.getInputStream()));

        while ((cadena=fentrada.readLine())!=null)//recibo cadena del cliente
        {
            fsalida.println(cadena.toUpperCase());//envio cadena al cliente
            System.out.println("Recibiendo: \t"+cadena);
        }
        //cerrar streams y conexiones
        fsalida.close();
        fentrada.close();
        clienteconectado.close();
        servidor.close();



    }
}
