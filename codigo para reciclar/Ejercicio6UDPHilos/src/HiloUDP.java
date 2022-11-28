import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class HiloUDP extends Thread {
    DatagramPacket paqRecibido;
    char operando;
    Datos datoscli;
    DatagramSocket s;
    byte[] recibidos = new byte[1024];
int resultado;

    public HiloUDP(DatagramPacket paqRecibido, DatagramSocket s, byte[] recibidos) {
        this.paqRecibido = paqRecibido;
        this.s = s;
        this.recibidos = recibidos;
    }

    @Override
    public void run() {
        System.out.println("paquete recibido"+new String( recibidos));
        ByteArrayInputStream bais = new ByteArrayInputStream(recibidos);
        ObjectOutputStream os;
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(bais);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            datoscli = (Datos) in.readObject();
            operando = datoscli.getOperando();
            System.out.println(operando);
            System.out.println(datoscli.getOp1());
             resultado = 0;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
        try {
            os = new ObjectOutputStream(bs);
            os.writeObject(dsalida);
        } catch (IOException e) {
            e.printStackTrace();
        }


        DatagramPacket dp;
        dp = new DatagramPacket(bs.toByteArray(), bs.size(), paqRecibido.getAddress(), paqRecibido.getPort());
        // Enviamos
        try {
            s.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }


            byte[] recibidos2 = new byte[1024];
            DatagramPacket paqRecibido2 = new DatagramPacket(recibidos2, recibidos2.length);


            try {
                s.receive(paqRecibido2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //convertimos bytes a objeto
            bais = new ByteArrayInputStream(recibidos2);

            try {
                in = new ObjectInputStream(bais);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                datoscli = (Datos) in.readObject();//obtengo objeto
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            operando = datoscli.getOperando();
             resultado = 0;



        }

        System.out.println("No hay mas datos");



    }
    }


