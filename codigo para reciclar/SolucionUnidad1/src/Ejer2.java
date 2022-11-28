import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ejer2 {
    public static void main(String[] args) throws IOException {

/*
//Con Runtime
        Process directorio=Runtime.getRuntime().exec
                ("cmd /c dir");
        BufferedReader lectura= new BufferedReader
                (new InputStreamReader(directorio.getInputStream()));

        String linea=null;
        while((linea= lectura.readLine())!=null){

            System.out.println(linea);



        }
*/
//Con ProcessBuilder
        ProcessBuilder pb = new ProcessBuilder
                ("CMD", "/C", "DIR");
        pb.inheritIO();
        pb.start();
    }
}
