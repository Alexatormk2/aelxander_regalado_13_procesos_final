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
        //while (true) {
            byte[] recibidos = new byte[1024];
            DatagramPacket paqRecibido = new DatagramPacket(recibidos, recibidos.length);
            //recibo al datagrama
            ds.receive(paqRecibido);
            //convertimos bytes a objeto
            ByteArrayInputStream bais = new ByteArrayInputStream(recibidos);
            ObjectInputStream in = new ObjectInputStream(bais);
            Datos datoscli = (Datos) in.readObject();//obtengo objeto
            char operando = datoscli.getOperando();
            System.out.println(operando);
            System.out.println(datoscli.getOp1());
            int resultado = 0;
            while (operando != '.') {
                if ((operando == '+') || (operando == '-') || (operando == '*') || (operando == ':')) {
                    System.out.println("Esperando primer operador");
                    int op1 = datoscli.getOp1();
                    System.out.println("Primer operador: " + op1);
                    System.out.println("Esperadno segundo operador ");
                    int op2 = datoscli.op2;
                    System.out.println("Segundo operador: " + op2);
                    System.out.println("Calculando resultado");

                    if (operando == '+') {
                        resultado = op1 + op2;
                    } else if (operando == '-') {
                        resultado = op1 - op2;
                    } else if (operando == '*') {
                        resultado = op1 * op2;
                    } else if (operando == ':') {
                        resultado = op1 / op2;
                    }
                    System.out.println("Enviando resultado");
                    datoscli.setResultado(resultado);
                } else {
                    System.out.println("Operacion no reconocida");
                }
                System.out.println("enviando resul: " + resultado);
                Datos dsalida = new Datos(datoscli.getOp1(), datoscli.getOp2(), datoscli.getOperando(), resultado);
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(bs);
                out.writeObject(dsalida);
                DatagramPacket dp;
                dp = new DatagramPacket(bs.toByteArray(), bs.size(), paqRecibido.getAddress(), paqRecibido.getPort());
                // Enviamos
                ds.send(dp);


                byte[] recibidos2 = new byte[1024];
                DatagramPacket paqRecibido2 = new DatagramPacket(recibidos2, recibidos2.length);


                ds.receive(paqRecibido2);
                //convertimos bytes a objeto
                 bais = new ByteArrayInputStream(recibidos2);
                 in = new ObjectInputStream(bais);
                 datoscli = (Datos) in.readObject();//obtengo objeto
                operando = datoscli.getOperando();
                resultado = 0;


            }

        System.out.println("No hay mas datos");



       }
    }

