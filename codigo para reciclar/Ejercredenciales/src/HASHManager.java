import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HASHManager {
    private static final String ALGORITMO="SHA-256";
    public static byte[] getDigest(byte[] mensaje) throws NoSuchAlgorithmException {
        byte[] resumen=null;
        MessageDigest algoritmo=MessageDigest.getInstance(ALGORITMO);
        algoritmo.reset();
        algoritmo.update(mensaje);
        resumen=algoritmo.digest();
        System.out.println(resumen);
        return resumen;



    }
    public static boolean compararResumenes (byte[] resumen1, byte[] resumen2) throws NoSuchAlgorithmException {
        boolean sonIguales;
       MessageDigest algoritmo= MessageDigest.getInstance(ALGORITMO);
       algoritmo.reset();
       sonIguales=algoritmo.isEqual(resumen1,resumen2);
        System.out.println(resumen1);
       return sonIguales;



    }
}
