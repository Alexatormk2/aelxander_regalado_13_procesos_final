import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;



public class RegistradorCredenciales {

    private static final String ENCODING_TYPE="UTF-8";

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Scanner sc= new Scanner(System.in);
        System.out.println("introduce identificador (email) :");
        String identificacdor=sc.nextLine();
        String password=sc.nextLine();
        byte[] resumen=HASHManager.getDigest(password.getBytes(ENCODING_TYPE));
        Files.write(new File(identificacdor+ ".credencial").toPath(),resumen);
       sc.close();

    }
}
