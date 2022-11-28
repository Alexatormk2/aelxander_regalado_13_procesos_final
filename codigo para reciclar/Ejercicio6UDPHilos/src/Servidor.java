import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Servidor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("LocalHost = " +
                InetAddress.getLocalHost().toString());

        DatagramSocket ds = null;

        ds = new DatagramSocket(1234);
        while (true) {
            byte[] recibidos = new byte[1024];
            DatagramPacket paqRecibido = new DatagramPacket(recibidos, recibidos.length);
            //recibo al datagrama
            ds.receive(paqRecibido);
            //convertimos bytes a objeto
            HiloUDP hilo = new HiloUDP(paqRecibido, ds,recibidos);
            hilo.start();
        }
    }
}
