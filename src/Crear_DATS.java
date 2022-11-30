import java.io.*;

public class Crear_DATS {


    public static void main(String[] args) throws IOException {

        //crear Cuenta
        File fichero = new File("cuenta.dat");
        FileOutputStream escribirCuenta = new FileOutputStream(fichero);
        ObjectOutputStream itemCuenta = new ObjectOutputStream(escribirCuenta);

        Cuenta cuenta = new Cuenta(999999999, "K91M31");
        Cuenta cuenta2 = new Cuenta(2033, "LOA3S1");
        Cuenta cuenta3 = new Cuenta(39323, "PL2332");

        itemCuenta.writeObject(cuenta);
        itemCuenta.writeObject(cuenta2);
        itemCuenta.writeObject(cuenta3);
        itemCuenta.close();
//crear usuarios por defecto

        File ficheroUser = new File("usuarios.dat");
        FileOutputStream escribirUsuario = new FileOutputStream(ficheroUser);
        ObjectOutputStream itemUser = new ObjectOutputStream(escribirUsuario);


        Usuarios usuarios = new Usuarios("Admin", "root", "root", 120, "admin@gmail.com");
        Usuarios usuarios2 = new Usuarios("Marioano", "Mario21", "superMario413", 34, "mariano@mushroom.exe");

        itemUser.writeObject(usuarios);
        itemUser.writeObject(usuarios2);
        itemUser.close();

    }

}
