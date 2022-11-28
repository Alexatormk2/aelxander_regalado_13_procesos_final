import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

public class Servidor {

    public static void main(String[] args) throws IOException {
//el puerto de escucha del servidor será el 8051
        int PUERTO = 8051;

        byte msg[] = new byte[1024];//Tamaño maximo posible 65535

//Creamos el socket UDP del servidor en el pueto asociado
        DatagramSocket s = new DatagramSocket(PUERTO);
        System.out.println("Servidor Activo");

//implementacion del protocolo del servidor en un bucle infinito
        while (true) {
            DatagramPacket recibido = new DatagramPacket(new byte[1024], 1024);

//llega un datagrama
            s.receive(recibido);
            HiloUDP hilo = new HiloUDP(recibido,s);
            hilo.start();
        }
    }
}
