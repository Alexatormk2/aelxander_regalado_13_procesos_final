import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) throws IOException {
        try {
            System.out.println("Creando socket cliente ");
            Socket clientsocket = new Socket();
            System.out.println("Estableciendo la conexión");
            InetSocketAddress addr = new InetSocketAddress("localhost", 6000);
            clientsocket.connect(addr);
            InputStream is = clientsocket.getInputStream();
            OutputStream os = clientsocket.getOutputStream();
            String a = JOptionPane.showInputDialog("Indica operacion");
            System.out.println("Enviando petición calculo");
            while (!a.equals(".")) {

                os.write(a.getBytes());
                int op1 = Integer.parseInt(JOptionPane.showInputDialog("Indica primer operando"));
                System.out.println("Enviando primer operando");

                os.write(op1);
                int op2 = Integer.parseInt(JOptionPane.showInputDialog("Indica primer operando"));
                System.out.println("Enviando segundo operando");
                os.write(op2);

                System.out.println("Recibiendo resultado");
                int resultado = is.read();
                System.out.println(" Resultado de la operacion: " + resultado);
                a = JOptionPane.showInputDialog("Indica operacion");
                System.out.println("Enviando petición calculo");
            }
            os.write(a.getBytes());
            System.out.println("Cerrando el socket cliente ");
            clientsocket.close();
            System.out.println("Terminado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
