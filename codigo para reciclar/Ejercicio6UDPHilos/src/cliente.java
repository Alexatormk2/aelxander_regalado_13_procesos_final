import javax.swing.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class cliente {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        InetAddress direcc = null;

        direcc = InetAddress.getLocalHost();


        // Puerto que hemos usado para el servidor
        int puerto = 1234;
        // Creamos el Socket
        DatagramSocket socket = null;


        socket = new DatagramSocket();
        int n1 = Integer.parseInt(JOptionPane.showInputDialog("Introduce primer operando"));
        int n2 = Integer.parseInt(JOptionPane.showInputDialog("Introduce segundo operando"));
        char op = JOptionPane.showInputDialog("Introduce  operacion").charAt(0);
        while (op!='.') {
            Datos d = new Datos(n1, n2, op);
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bs);
            out.writeObject(d);
            DatagramPacket dp;
            dp = new DatagramPacket(bs.toByteArray(), bs.size(), direcc, puerto);
            // Enviamos
            socket.send(dp);

            byte[] recibidos = new byte[1024];
            DatagramPacket paqRecibido = new DatagramPacket(recibidos, recibidos.length);
            //recibo al datagrama
            socket.receive(paqRecibido);
            System.out.println();
            ByteArrayInputStream bais = new ByteArrayInputStream(recibidos);
            ObjectInputStream in = new ObjectInputStream(bais);
            Datos datosservidor = (Datos) in.readObject();//obtengo objeto
            int res = datosservidor.getResultado();
            System.out.println("El resultado es: " + res);
            n1 = Integer.parseInt(JOptionPane.showInputDialog("Introduce primer operando"));
            n2 = Integer.parseInt(JOptionPane.showInputDialog("Introduce segundo operando"));
            op = JOptionPane.showInputDialog("Introduce  operacion").charAt(0);

        }
        Datos d = new Datos(0, 0, op);
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bs);
        out.writeObject(d);
        DatagramPacket dp;
        dp = new DatagramPacket(bs.toByteArray(), bs.size(), direcc, puerto);
        // Enviamos
        socket.send(dp);
        System.out.println("no hay mas datos");
    }
}

