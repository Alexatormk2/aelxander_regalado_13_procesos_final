import java.io.*;
import java.net.Socket;
import java.util.Random;

public class Hilo extends Thread{

    Socket s;

    public Hilo(Socket s) {
        this.s = s;
    }

    public void run() {

        ObjectOutputStream outObjeto = null;
        try {
            outObjeto = new ObjectOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Primer digito");
        Random r =new Random();
        int n1 ;
            n1=r.nextInt(10);

        System.out.println("segundo digito");
        int n2;
        n2 = r.nextInt(10);



// se prepara un objeto y se envia

        Objeto cal = new Objeto(n1,n2, 0);
        try {
            outObjeto.writeObject(cal);//enviando objeto
        System.out.println(" Envio: " + cal.getNum1() + " ; " + cal.getNum2());

// Se obtiene un stream para leer objetos
        ObjectInputStream inObjeto = new ObjectInputStream(s.getInputStream());
        Objeto resul = (Objeto) inObjeto.readObject();
        System.out.println(" Recibo resultado: " + resul.getResultado());
//cerrar streams y sockets
        outObjeto.close();
        inObjeto.close();
        s.close();
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }




    }
}
