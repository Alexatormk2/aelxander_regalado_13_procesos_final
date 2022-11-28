import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Cliente {
    public static void main(String args[]) {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    try

    {
        DatagramSocket socketUDP = new DatagramSocket();
        byte[] mensaje = new byte[1024];
        InetAddress IPServidor = InetAddress.getLocalHost();
        int puertoServidor = 8051;
// Introducimos datos por teclado
        System.out.println("Introduce mensaje");
        String cadena = in.readLine();
        mensaje = cadena.getBytes();

        // Construimos un datagrama para enviar el mensaje al servidor
        DatagramPacket peticion
                = new DatagramPacket(mensaje, mensaje.length, IPServidor,
                puertoServidor);

        // Enviamos el datagrama
        socketUDP.send(peticion);

        // Construimos el DatagramPacket que contendrá la respuesta
        byte[] bufer = new byte[1000];
        DatagramPacket respuesta
                = new DatagramPacket(bufer, bufer.length);
        socketUDP.receive(respuesta);

        // Enviamos la respuesta del servidor a la salida estandar

        System.out.println("Respuesta: " +new String(respuesta.getData(), 0, respuesta.getLength()));
        // Cerramos el socket
        socketUDP.close();

    } catch(
    SocketException e)

    {
        System.out.println("Socket: " + e.getMessage());
    } catch(
    IOException e)

    {
        System.out.println("IO: " + e.getMessage());
    }
}
}

