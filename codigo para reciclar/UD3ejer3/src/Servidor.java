import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

public class Servidor {

    public static void main(String[] args) throws IOException {
//el puerto de escucha del servidor será el 8051
        int PUERTO = 8052;

        byte msg[] = new byte[32];//Tamaño maximo posible 65535

//Creamos el socket UDP del servidor en el pueto asociado
        DatagramSocket s = new DatagramSocket(PUERTO);
        System.out.println("Servidor Activo");

//implementacion del protocolo del servidor en un bucle infinito
        while (true) {
            DatagramPacket recibido = new DatagramPacket(new byte[1024], 1024);

//llega un datagrama
            s.receive(recibido);
            String mensaje = new String(recibido.getData());
            System.out.println("Ha llegado una peticion \n " + mensaje);
            System.out.println("Procedente de :" + recibido.getAddress());
            System.out.println("En el puerto :" + recibido.getPort());
            System.out.println("Sirviendo la petición");

//se prepara el mensaje a enviar con la fecha del sistema
            if (mensaje.trim().equalsIgnoreCase("hora")) {
                String message = new String("HORA DEL SERVIDOR " + new Date());
                msg = message.getBytes();

//se crea el datagrama que contendrá al mensaje
                DatagramPacket paquete = new DatagramPacket(msg, msg.length, recibido.getAddress(),
                        recibido.getPort());

//se le envia al cliente
                s.send(paquete);

            } else {
                String texto = new String("Mensaje vacio");
                msg = texto.getBytes();

//se crea el datagrama que contendrá al mensaje
                DatagramPacket paquete = new DatagramPacket(msg, msg.length, recibido.getAddress(),
                        recibido.getPort());

//se le envia al cliente
                s.send(paquete);

            }
        }
    }
}
