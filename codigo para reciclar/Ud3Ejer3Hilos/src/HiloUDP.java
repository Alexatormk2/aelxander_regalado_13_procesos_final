import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

public class HiloUDP extends Thread{
public DatagramPacket recibido;
    byte msg[] = new byte[1024];
    public DatagramSocket s;

    public HiloUDP(DatagramPacket recibido, DatagramSocket s) {
        this.recibido = recibido;
        this.s = s;
    }

    @Override
    public void run() {
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
            try {
                s.send(paquete);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            String texto = new String("Mensaje vacio");
            msg = texto.getBytes();

//se crea el datagrama que contendrá al mensaje
            DatagramPacket paquete = new DatagramPacket(msg, msg.length, recibido.getAddress(),
                    recibido.getPort());

//se le envia al cliente
            try {
                s.send(paquete);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}


