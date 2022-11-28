import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class ValidadorCredenciales {
    private static final String ENCODING_TYPE="UTF-8";

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Scanner sc= new Scanner(System.in);
        System.out.println("introduce identificador (email) :");
        String identificador=sc.nextLine();
        String password=sc.nextLine();
        File f=new File("credenciales.cre");
        byte[] resumen=HASHManager.getDigest(password.getBytes(ENCODING_TYPE));
        //byte[] resumen_almacenado= Files.readAllBytes(new File(identificador+".credencial").toPath());
        byte[] resumen_almacenado= Files.readAllBytes(f.toPath());
        if(HASHManager.compararResumenes(resumen,resumen_almacenado)){
            System.out.println("Autorizado");
        }else {
            System.out.println("Error de validacion");
        }
        sc.close();
    }
}
