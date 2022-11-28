import java.io.*;
import java.net.Socket;

public class Cliente {

    public static void main(String[] arg) throws IOException, ClassNotFoundException {
        String Host = "localhost";
        int Puerto = 6000;
        System.out.println("PROGRAMA CLIENTE INICIADO ");

        Socket cliente = new Socket(Host, Puerto);
        // flujo de salida Para objetos
        ObjectOutputStream datosal = new ObjectOutputStream(cliente.getOutputStream());
//flujo de entrada Para objetos
        ObjectInputStream datoent = new ObjectInputStream(cliente.getInputStream());
//Se recibe un objeto
        Objeto ope = (Objeto) datoent.readObject();//recibo objeto
        System.out.println("Recibo : " + ope.getNum1() + " ; " + ope.getNum2());
//Modifico el objeto
        ope.setResultado(ope.getNum1() * ope.getNum2());


// Se envía el objeto
        datosal.writeObject(ope);
        System.out.println(" Envio: " + ope.getResultado());
// CERRAR STREAMS Y SOCKETS
        datosal.close();
        datoent.close();
        cliente.close();
    }
}
