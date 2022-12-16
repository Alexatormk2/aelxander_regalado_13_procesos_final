import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class Server_Banco {
    public static int usuariosRegistrados = 0;
    final static int puerto = 5500;

    public static Cuenta[] ListaCuentas = new Cuenta[220];
    public static Usuarios[] ListaUsuarios = new Usuarios[220];
    public static Usuarios usuarioAactual;


    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, ClassNotFoundException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        ServerSocket s;
        Socket c;
        s = new ServerSocket(puerto);
        System.out.println("Servidor iniciado");
        cargarUsuarioss();
        while (true) {
            c = s.accept(); //esperando cliente
            Hilo_Banco hiloBanco = new Hilo_Banco(c);
            hiloBanco.start();

            guardarDatosUsuario();
            guardarDatosCuentas();
        }

    }


    public static void cargarUsuarioss() throws IOException {
//Carga los datos de los dat de usuario a las listas para usarlos despues
        File ficheroUser = new File("usuarios.dat");
        FileInputStream fileDEntro = new FileInputStream(ficheroUser);
        ObjectInputStream dataGET = new ObjectInputStream(fileDEntro);
        int aj = 0;
        try {
            while (true) {

                Usuarios usuarios = (Usuarios) dataGET.readObject();
                String nombre = usuarios.getNombre();
                String user = usuarios.getUsuario();
                int edad = usuarios.getEdad();
                String contra = usuarios.getContraseina();
                String email = usuarios.getEmail();
                System.out.println(nombre + "  " + " " + user);
                Usuarios a = new Usuarios(nombre, user, contra, edad, email);


                ListaUsuarios[aj] = a;
                int ka = ListaUsuarios.length;
                System.out.println("Lista ===" + ka);
                aj++;
                usuariosRegistrados++;
                System.out.println("total usuarios----" + usuariosRegistrados);

            }
        } catch (EOFException e) {
            System.out.println(" Fin de la carga de usuarios");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        dataGET.close();


        //Carga de las cuentas

        File ficheroCuenta = new File("cuenta.dat");
        FileInputStream fileDEntroCuenta = new FileInputStream(ficheroCuenta);
        ObjectInputStream dataGETCuenta = new ObjectInputStream(fileDEntroCuenta);
        int ax = 0;
        try {
            while (true) {

                Cuenta cuenta = (Cuenta) dataGETCuenta.readObject();
                String idCuenta = cuenta.getIdCuenta();
                double saldo = cuenta.getSaldo();


                Cuenta c = new Cuenta(saldo, idCuenta);
                ListaCuentas[ax] = c;
                ax++;

            }
        } catch (EOFException e) {
            System.out.println(" Fin de la carga de datos");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        dataGETCuenta.close();
    }


    //guardado de cuentas


    public static void guardarDatosUsuario() throws IOException {
        //Coge los datos de las listas los vuelve a meter al dat
        File ficheroUsuarios = new File("usuarios.dat");
        FileOutputStream escribirUser = new FileOutputStream(ficheroUsuarios);
        ObjectOutputStream itemUser = new ObjectOutputStream(escribirUser);
        Usuarios surv = usuarioAactual;
        itemUser.writeObject(usuarioAactual);
        for (int a = 0; a < ListaUsuarios.length; a++) {

            if (ListaUsuarios[a] == null) {
                System.out.println("No hya usuarios para guardar o ya estan todos");
                break;
            } else if (ListaUsuarios[a] == usuarioAactual) {

                try {
                    ListaUsuarios[a] = usuarioAactual;
                } catch (Exception e) {
                    System.out.println("Error inesperado");
                }

            }
            usuarioAactual = ListaUsuarios[a];
            if (ListaUsuarios[a] == surv) {
                continue;
            } else {
                itemUser.writeObject(ListaUsuarios[a]);
            }
        }


    }

    public static void guardarDatosCuentas() throws IOException {
        //Coge los datos de las listas los vuelve a meter al dat
        File ficheroCuenta = new File(".//cuenta.dat");
        FileOutputStream escribirCuenta = new FileOutputStream(ficheroCuenta);
        ObjectOutputStream itemCuenta = new ObjectOutputStream(escribirCuenta);


        for (int a = 0; a < ListaCuentas.length; a++) {

            if (ListaCuentas[a] == null) {
                System.out.println("Lista de cuentas guardada");
                break;
            } else {

                try {
                    itemCuenta.writeObject(ListaCuentas[a]);

                } catch (Exception e) {
                    System.out.println("Error inesperado");
                }


            }

        }


    }
}
