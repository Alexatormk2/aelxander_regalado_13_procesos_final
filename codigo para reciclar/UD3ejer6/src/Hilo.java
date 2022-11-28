import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Hilo extends Thread{
    Socket c=new Socket();

    public Hilo(Socket c) {
        this.c= c;

    }

    @Override
    public void run() {
        try {
            System.out.println("Arrancancdo hilo");
            InputStream is = c.getInputStream();
            OutputStream os = c.getOutputStream();
            System.out.println("Esperando mensaje de operacion");
            byte[] buffer = new byte[1];
            is.read(buffer);
            String operacion = new String(buffer);
            System.out.println("Operacion recibida: " + new String(operacion));
            while (!operacion.equals(".")){
                if (operacion.equals("+") || operacion.equals("-") || operacion.equals("*") || operacion.equals("/")) {
                    System.out.println("Esperando primer operador");
                    int op1 = is.read();
                    System.out.println("Primer operador: " + op1);
                    System.out.println("Esperadno segundo operador ");
                    int op2 = is.read();
                    System.out.println("Segundo operador: " + op2);
                    System.out.println("Calculando resultado");
                    int resultado = 0;
                    if (operacion.equals("+")) {
                        resultado = op1 + op2;
                    } else if (operacion.equals("-")) {
                        resultado = op1 - op2;
                    } else if (operacion.equals("*")) {
                        resultado = op1 * op2;
                    } else if (operacion.equals("/")) {
                        resultado = op1 / op2;
                    }
                    System.out.println("Enviando resultado");
                    os.write(resultado);
                } else {
                    System.out.println("Operacion no reconocida");
                }
                is.read(buffer);

                operacion = new String(buffer);
                System.out.println("Operacion recibida: " + new String(operacion));
            }
        }catch (IOException e) {
            e.printStackTrace();
        } System.out.println("Hilo terminado");
        try {
            c.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
