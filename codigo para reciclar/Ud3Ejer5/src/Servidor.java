import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] arg) throws IOException, ClassNotFoundException {

        int numeropuerto = 6000;// puerto

        ServerSocket servidor = new ServerSocket(numeropuerto);
        System.out.println("Esperando al cliente. . . . .");

        Socket cliente = servidor.accept();

// Se prepara un flujo de salida para objetos
        ObjectOutputStream outObjeto = new ObjectOutputStream(cliente.getOutputStream());

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Primer digito");
        int n1 = Integer.parseInt(in.readLine());
        System.out.println("segundo digito");
        int n2 = Integer.parseInt(in.readLine());



// se prepara un objeto y se envia

        Objeto cal = new Objeto(n1,n2, 0);
        outObjeto.writeObject(cal);//enviando objeto
        System.out.println(" Envio: " + cal.getNum1() + " ; " + cal.getNum2());

// Se obtiene un stream para leer objetos
        ObjectInputStream inObjeto = new ObjectInputStream(cliente.getInputStream());
        Objeto resul = (Objeto) inObjeto.readObject();
        System.out.println(" Recibo resultado: " + resul.getResultado());
//cerrar streams y sockets
        outObjeto.close();
        inObjeto.close();
        cliente.close();
        servidor.close();
    }
}
