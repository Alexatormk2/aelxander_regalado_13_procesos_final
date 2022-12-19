import ibanGenerator.IBANGenerator;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crear_DATS {


    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        IBANGenerator ibanGenerator = new IBANGenerator();
        //crear Cuenta
        File fichero = new File("cuenta.dat");
        FileOutputStream escribirCuenta = new FileOutputStream(fichero);
        ObjectOutputStream itemCuenta = new ObjectOutputStream(escribirCuenta);

        Cuenta cuenta = new Cuenta(999999, ibanGenerator.generateIBAN("Austria"));
        Cuenta cuenta2 = new Cuenta(939, ibanGenerator.generateIBAN("Germany"));
        Cuenta cuenta3 = new Cuenta(20, ibanGenerator.generateIBAN("Netherlands"));


        itemCuenta.writeObject(cuenta);
        itemCuenta.writeObject(cuenta2);
        itemCuenta.writeObject(cuenta3);


        itemCuenta.close();
//crear usuarios por defecto

        File ficheroUser = new File("usuarios.dat");
        FileOutputStream escribirUsuario = new FileOutputStream(ficheroUser);
        ObjectOutputStream itemUser = new ObjectOutputStream(escribirUsuario);
        String marioC = "monedas";
        String contraAdmin = "root";
        String adEncri = encriptar(contraAdmin);
        String contraM = encriptar(marioC);
        Usuarios usuarios = new Usuarios("Admin", "root", adEncri, 120, "admin@gmail.com");
        Usuarios usuarios2 = new Usuarios("Marioano", "Mario21", contraM, 34, "mariano@mushroom.exe");

        itemUser.writeObject(usuarios);
        itemUser.writeObject(usuarios2);
        itemUser.close();

        //crear normas


        File ficheroNormas = new File("readme.txt");
        FileOutputStream escribirNormas = new FileOutputStream(ficheroNormas);
        ObjectOutputStream itemnormas = new ObjectOutputStream(escribirNormas);


        Normas normas1 = new Normas("1.Se deben respetar a los otros usuarios");
        Normas normas2 = new Normas("2.No transferir dinero pagado en negro");
        Normas normas3 = new Normas("3.Pagar las deudas a timpo");

        itemnormas.writeObject(normas1.normas);
        itemnormas.writeObject(normas2.normas);
        itemnormas.writeObject(normas3.normas);
        itemnormas.close();

    }

    static String encriptar(String contra) throws UnsupportedEncodingException, NoSuchAlgorithmException {


        byte[] contraArray = Cliente_Banco.HASHManager.getDigest(contra.getBytes("UTF-8"));
        String resumen = "";

        for (int i = 0; i < contraArray.length; i++) {

            System.out.println(contraArray[i]);
            resumen = resumen + contraArray[i] + ",";
        }
        return resumen;

    }

    public class HASHManager {

        private static final String ALGORITMO = "SHA-256";

        public static byte[] getDigest(byte[] mensaje) throws NoSuchAlgorithmException {
            byte[] resumen = null;
            MessageDigest algoritmo = MessageDigest.getInstance(ALGORITMO);

            algoritmo.reset();
            algoritmo.update(mensaje);
            resumen = algoritmo.digest();
            return resumen;
        }

    }


}
