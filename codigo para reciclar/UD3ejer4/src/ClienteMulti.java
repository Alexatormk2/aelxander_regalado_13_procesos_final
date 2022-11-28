import java.io.IOException;
import java.net.*;

public class ClienteMulti {

    public static void main(String args[]) throws IOException {
        //Se crea el socket multicast

        MulticastSocket escucha = new MulticastSocket(12345);
        /*
         * 1- Recogemos la IP/dirección.
         * 2- Creamos el grupo: pasamos la IP y el puerto (el mismo del socket multicast).
         * 3- Recogemos la interfaz de red: en este caso ponemos 'localhost' porque estamos en local.
         */
        InetAddress dirIP = InetAddress.getByName("225.0.0.1");
        InetSocketAddress grupo = new InetSocketAddress(dirIP, 12345);
        NetworkInterface red = NetworkInterface.getByName("localhost");

        // Nos unimos al grupo multicast:
        escucha.joinGroup(grupo, red);
        String msg = "";
        byte[] buf = new byte[1000];
        while (!msg.trim().equals("*")) {
            //recibe el paquete del serverMulticast
            DatagramPacket paquete = new DatagramPacket(buf, buf.length);
            escucha.receive(paquete);
            msg = new String(paquete.getData());
            System.out.println("Recibo: " + msg.trim());
            buf = new byte[1000];

        }
        escucha.leaveGroup(grupo,red);//Abandonamos el grupo
        escucha.close();
    }
}
