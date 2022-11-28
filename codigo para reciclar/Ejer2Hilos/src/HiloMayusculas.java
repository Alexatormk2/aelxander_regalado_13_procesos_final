import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HiloMayusculas extends Thread{

    Socket cliente;

    public HiloMayusculas(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        String cadena="";
        //CREO FLUJO DE SALIDA AL CLIENTE

        PrintWriter fsalida= null;
        try {
            fsalida = new PrintWriter(cliente.getOutputStream(),true);

        //CREO FLUJO ENTRADA DEL CLIENTE

        BufferedReader fentrada= new BufferedReader (new InputStreamReader(cliente.getInputStream()));

        while ((cadena=fentrada.readLine())!=null)//recibo cadena del cliente
        {

            fsalida.println(cadena.toUpperCase());//envio cadena al cliente
            System.out.println("Recibiendo: \t"+cadena);
            System.out.println("Direccion Ip cliente: " + cliente.getInetAddress());

        }

        //cerrar streams y conexiones
        fsalida.close();
        fentrada.close();
        cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
