import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {



        public static void main(String[] args) throws IOException {
            String host="localhost";
            int puerto=6000;
            Socket Cliente=new Socket(host,puerto);
            //CREO FLUJO DE SALIDA AL SERVIDOR
            PrintWriter fsalida= new PrintWriter(Cliente.getOutputStream(),true);
            //CREO FLUJO DE entrada AL SERVIDOR
            BufferedReader fentrada=new BufferedReader
                    (new InputStreamReader(Cliente.getInputStream()));

            //FLUJO PARA ENTRADA ESTANDAR
            BufferedReader in= new BufferedReader
                    (new InputStreamReader(System.in));

            String cadena, eco="";
            System.out.println("Introduce cadena");
            cadena=in.readLine();//lectura por teclado
            while(!cadena.equalsIgnoreCase("fin")){
                fsalida.println(cadena);//envio cadena al servidor
                eco=fentrada.readLine();//recibo cadena del servidor
                System.out.println("=>ECO "+eco);
                System.out.println("Introduce cadena");
                cadena=in.readLine();//lectura por teclado

            }
            fsalida.close();
            fentrada.close();
            in.close();
            Cliente.close();
        }
    }


