import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


public class RegistradorCredenciales2 {

    private static final String ENCODING_TYPE="UTF-8";

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Scanner sc= new Scanner(System.in);
        System.out.println("introduce identificador (email) :");
        String identificacdor=sc.nextLine();
        String password=sc.nextLine();
        //byte[] resumen=HASHManager.getDigest(password.getBytes(ENCODING_TYPE));
        byte[] resumen=HASHManager.getDigest(password.getBytes());
        File fichero = null;
        PrintWriter pw = null;
        fichero = new File("credenciales.cre");
        Files.write(fichero.toPath(),resumen);


        //Files.write(new File(identificacdor+ ".credencial").toPath(),resumen);
       sc.close();

    }
}
