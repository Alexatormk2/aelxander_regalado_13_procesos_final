

import com.neovisionaries.i18n.CountryCode;
import ibanGenerator.IBANGenerator;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Hilo_Banco extends Thread {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Socket c = new Socket();
    String mensajeUTF;
    int opcion;
    public static ObjectOutputStream oos;
    public static ObjectInputStream ois;

    public Hilo_Banco(Socket c) {
        this.c = c;

    }


    public void run() {
        try {


            System.setProperty("javax.net.ssl.keyStore", "AlmacenSSL.jks");
            System.setProperty("javax.net.ssl.keyStorePassword", "123456");
            oos = new ObjectOutputStream(c.getOutputStream());
            ois = new ObjectInputStream(c.getInputStream());


            //Generamos el par de claves
            KeyPairGenerator keygen;

            keygen = KeyPairGenerator.getInstance("RSA");

            System.out.println("Generando par de claves");
            KeyPair par = keygen.generateKeyPair();
            PrivateKey privada = par.getPrivate();
            PublicKey publica = par.getPublic();
            do {

                //////////////////////////////////////////////////////////////
                try {

                    //Creamos los flujos

                    //mandamos la clave publica
                    oos.writeObject(publica);
                    System.out.println("Enviamos la clave publica cuyo valor es: " + publica);
                    System.out.println("mensaje antes de enviar");
                    mensajeUTF = "Saludos querido cliente por favor inicio sesion(1) o cree usuario nuevo y cuenta nueva(2) o salga(3) de el numero correspondiente a la opcion";
                    System.out.println("mensaje enviado");
                    oos.writeObject(mensajeUTF);

                    System.out.println("esperar int para switch");
                    opcion = (int) ois.readObject();
                    System.out.println(opcion + "_____se a recibido para le switch");


                    switch (opcion) {
                        case 1:
                            inicioSesion();

                            break;
                        case 2:
                            CrearUsuario();


                            break;
                        case 3:
                            System.out.println("Desconectando usuario.....");
                            break;
                        default:

                            break;
                    }

                } catch (IOException e) {
                    System.out.println("Error inesperado");
                } catch (NumberFormatException e) {
                    System.out.println("Se esperaba un numero");
                    oos.writeObject(mensajeUTF = "Se esperaba un numero ,no un caracter no numerico o numero decimal revise por favor");
                }

                //recibimos texto encriptado del cliente

                byte[] mensaje = (byte[]) ois.readObject();
                //preparamos el Cipher para descifrar
                System.out.println("El mensaje cifrado recibido es:" + new String(mensaje));

                Cipher descipher = Cipher.getInstance("RSA");
                descipher.init(Cipher.DECRYPT_MODE, privada);

                String mensaje_descifrado = new String(descipher.doFinal(mensaje));

                System.out.println("Mensaje descifrado con clave privada: " + mensaje_descifrado);

            } while (opcion != 3);


            oos.close();
            ois.close();
            c.close();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Hilo_Banco.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Hilo_Banco.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Hilo_Banco.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Hilo_Banco.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Hilo_Banco.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Hilo_Banco.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Hilo_Banco.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public void inicioSesion() throws IOException, NoSuchAlgorithmException {
        try {

            //Generamos el par de claves
            KeyPairGenerator keygen;

            keygen = KeyPairGenerator.getInstance("RSA");

            System.out.println("Generando par de claves");
            KeyPair par = keygen.generateKeyPair();
            PrivateKey privada = par.getPrivate();
            PublicKey publica = par.getPublic();
            //mandamos la clave publica
            System.out.println("Mandando cla clave2");
            oos.writeObject(publica);
            ///////////////////////////
            String contra;

            oos.writeObject(mensajeUTF = "Selecciona uno de estos usuarios");
            System.out.println(mensajeUTF);
            System.out.println("cargando for usuarios");

            for (int k = 0; k < Server_Banco.ListaUsuarios.length; k++) {
                if (Server_Banco.ListaUsuarios[k] == null) {
                    System.out.println("null ser");

                    break;
                } else {
                    oos.writeObject(mensajeUTF = k + "__" + Server_Banco.ListaUsuarios[k].nombre);
                    System.out.println(mensajeUTF);
                    System.out.println(k + "__Contando");
                }
            }
            oos.writeObject(mensajeUTF = "salir");
            System.out.println("Recibimos int del usuario");

            int opcionUser = 0;
            System.out.println("opcion user ==" + opcionUser);
            opcionUser = (int) ois.readObject();
            System.out.println("se a recibido_____" + opcionUser);
            if (Server_Banco.ListaUsuarios[opcionUser] == null) {

                oos.writeObject(mensajeUTF = "Ese valor no es valido tiene que ser uno de los numeros listados arriba, volviendo al menu principal");
            } else {
                Server_Banco.usuarioAactual = Server_Banco.ListaUsuarios[opcionUser];
                oos.writeObject(mensajeUTF = "Da la contraseña del usuario");

                byte[] mensaje = (byte[]) ois.readObject();
                //preparamos el Cipher para descifrar

                Cipher descipher = Cipher.getInstance("RSA");
                descipher.init(Cipher.DECRYPT_MODE, privada);

                String mensaje_descifrado = new String(descipher.doFinal(mensaje));

                if (mensaje_descifrado.equals(Server_Banco.usuarioAactual.contraseina)) {

                    oos.writeObject(mensajeUTF = "Inicio de sesion con exicto bienvenido: " + Server_Banco.usuarioAactual.nombre);
                    System.out.println(mensaje_descifrado);

                    menu();

                } else {

                    oos.writeObject(mensajeUTF = " Error contraseina incorrecta, saliendo ;D");
                    System.out.println(mensajeUTF);

                }

            }


        } catch (NumberFormatException as) {

            oos.writeUTF(mensajeUTF = "Error de formato, se esperaba un valor numerico");


        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    public void CrearUsuario() throws IOException, NoSuchAlgorithmException, ClassNotFoundException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Matcher matDni = null;
        Matcher matEmail = null;
        Matcher matContra = null;
        Pattern paternDNI = Pattern.compile("^[0-9]{8}[a-zA-Z]{1}$");
        Pattern paternEmail = Pattern.compile("^(.+)@(.+).[a-zA-Z]{3}$");
        Pattern paternContraseña = Pattern.compile("^(?=.[a-z].[a-z])(?=.[! \"#...\\d].[!\"#...\\d]).{8,}$");

        //Generamos el par de claves
        KeyPairGenerator keygen;

        keygen = KeyPairGenerator.getInstance("RSA");

        System.out.println("Generando par de claves");
        KeyPair par = keygen.generateKeyPair();
        PrivateKey privada = par.getPrivate();
        PublicKey publica = par.getPublic();
        //mandamos la clave publica
        oos.writeObject(publica);
        System.out.println("Mandando cla clave2");

        oos.writeObject(mensajeUTF = "Da el nombre de usuario");


        byte[] mensaje = (byte[]) ois.readObject();
        //preparamos el Cipher para descifrar

        Cipher descipher = Cipher.getInstance("RSA");
        descipher.init(Cipher.DECRYPT_MODE, privada);

        String nombre = new String(descipher.doFinal(mensaje));

        oos.writeObject(mensajeUTF = "Da el dni");
        mensaje = (byte[]) ois.readObject();
        descipher = Cipher.getInstance("RSA");
        descipher.init(Cipher.DECRYPT_MODE, privada);
        String dni = new String(descipher.doFinal(mensaje));
        matDni = paternDNI.matcher(dni);
        if (!matDni.find()) {
            oos.writeObject(mensajeUTF = "Dni incorrecto__ cerrdando ;D");
            Cliente_Banco.socket.close();
        } else {
            oos.writeObject(mensajeUTF = "DNI correcto");
            //pasandoa  siguiente paso
            oos.writeObject(mensajeUTF = "Da el email");
            mensaje = (byte[]) ois.readObject();
            descipher = Cipher.getInstance("RSA");
            descipher.init(Cipher.DECRYPT_MODE, privada);
            String email = new String(descipher.doFinal(mensaje));
            matEmail = paternEmail.matcher(email);
            if (!matEmail.find()) {

                oos.writeObject(mensajeUTF = " Email sin seguir el patron de correo: incorrecto--- cerranado :D");

                Cliente_Banco.socket.close();
            } else {
                oos.writeObject(mensajeUTF = "Email correcto");
                //pasando a siguiente paso

                oos.writeObject(mensajeUTF = "Da la contraseña");
                mensaje = (byte[]) ois.readObject();
                descipher = Cipher.getInstance("RSA");
                descipher.init(Cipher.DECRYPT_MODE, privada);
                String contra = new String(descipher.doFinal(mensaje));
                matContra = paternContraseña.matcher(contra);
                if (!matContra.find()) {
                    oos.writeObject(mensajeUTF = "Contraseña no cumple los requisitos minimos: incorrecto__cerrando");
                    Cliente_Banco.socket.close();
                } else {

                    oos.writeObject(mensajeUTF = "Contraseña correcto");
                    //pasandoa  siguiente paso
                    oos.writeObject(mensajeUTF = "Da la edad");
                    int edad = (int) ois.readObject();
                    oos.writeObject(mensajeUTF = "Da nombre de usuario");
                    String usuario = (String) ois.readObject();


                    Usuarios user = new Usuarios(nombre, usuario, contra, edad, email);
                    for (int a = 0; a < Server_Banco.ListaUsuarios.length; a++) {

                        if (Server_Banco.ListaUsuarios[a] == null) {
                            Server_Banco.ListaUsuarios[a] = user;
                            CrearCuentasUser();
                            break;
                        }


                    }
                    inicioSesion();

                }

            }
        }
    }

    public void menu() throws NoSuchAlgorithmException, IOException, ClassNotFoundException, InvalidKeyException, SignatureException {
        int cuentaSelec = 0;
        int opcion = 0;
        KeyPairGenerator keygen;

        keygen = KeyPairGenerator.getInstance("RSA");

        System.out.println("Generando par de claves");
        KeyPair par = keygen.generateKeyPair();
        PrivateKey privada = par.getPrivate();
        PublicKey publica = par.getPublic();
        do {
            //firma



            //mandamos la clave publica
            oos.writeObject(publica);
            System.out.println("Mandando cla clave2");
            System.out.println("Saludos que desea hacer:" +
                    "(1)Ver saldo" +
                    "(2)Ingresar saldo" +
                    "(3)Retirar saldo");
            oos.writeObject(mensajeUTF = "Saludos que desea hacer:" +
                    "(1)Ver saldo" +
                    "(2)Ingresar saldo" +
                    "(3)Retirar saldo" +
                    "(4)Transferencia" +
                    "(5)Salir");
            System.out.println("recibir valor");
            opcion = (int) ois.readObject();
            int contador = 0;
            boolean fin = true;
            int conta = 0;
            switch (opcion) {

                case 1:

                    oos.writeObject(mensajeUTF = "Selecciona una de las cuentas a ver");
                    System.out.println(mensajeUTF);
                    for (contador = 0; contador < Server_Banco.ListaCuentas.length; contador++) {

                        if (Server_Banco.ListaCuentas[contador] == null) {
                            System.out.println("salir del for");
                            break;
                        }
                    }

                    do {
                        if (Server_Banco.ListaCuentas[conta] == null) {
                            oos.writeObject(mensajeUTF = "salir");
                            System.out.println(mensajeUTF);
                            break;

                        } else
                            oos.writeObject(mensajeUTF = conta + "_" + Server_Banco.ListaCuentas[conta].idCuenta);
                        System.out.println(mensajeUTF);
                        conta++;

                    }

                    while (true);
                    System.out.println("afuera de los loops");

                    //recibier opcion por parte de cliente
                    cuentaSelec = (int) ois.readObject();
                    System.out.println(cuentaSelec);
                    oos.writeObject(mensajeUTF = "El saldo de la cuenta es_" + Server_Banco.ListaCuentas[cuentaSelec].saldo);


                    break;
                case 2:
                    oos.writeObject(mensajeUTF = "Selecciona una de las cuentas a ver");
                    System.out.println(mensajeUTF);
                    for (contador = 0; contador < Server_Banco.ListaCuentas.length; contador++) {

                        if (Server_Banco.ListaCuentas[contador] == null) {
                            System.out.println("salir del for");
                            break;
                        }
                    }

                    do {
                        if (Server_Banco.ListaCuentas[conta] == null) {
                            oos.writeObject(mensajeUTF = "salir");
                            System.out.println(mensajeUTF);
                            break;

                        } else
                            oos.writeObject(mensajeUTF = conta + "_" + Server_Banco.ListaCuentas[conta].idCuenta);
                        System.out.println(mensajeUTF);
                        conta++;

                    }

                    while (true);
                    System.out.println("afuera de los loops");

                    //recibier opcion por parte de cliente
                    cuentaSelec = (int) ois.readObject();
                    System.out.println(cuentaSelec);
                    oos.writeObject(mensajeUTF = "Cuanto quieres ingresar");
//recibir cantidad
                    double ingresar = (double) ois.readObject();
                    ingresar = Server_Banco.ListaCuentas[cuentaSelec].saldo + ingresar;
                    Server_Banco.ListaCuentas[cuentaSelec].setSaldo(ingresar);
                    oos.writeObject(mensajeUTF = "La cantidad a sido ingresada, saldo de la cuenta es_" + Server_Banco.ListaCuentas[cuentaSelec].saldo);

                    break;
                case 3:
///retirar
                    oos.writeObject(mensajeUTF = "Selecciona una de las cuentas a retirar saldo");
                    System.out.println(mensajeUTF);
                    for (contador = 0; contador < Server_Banco.ListaCuentas.length; contador++) {

                        if (Server_Banco.ListaCuentas[contador] == null) {
                            System.out.println("salir del for");
                            break;
                        }
                    }

                    do {
                        if (Server_Banco.ListaCuentas[conta] == null) {
                            oos.writeObject(mensajeUTF = "salir");
                            System.out.println(mensajeUTF);
                            break;

                        } else
                            oos.writeObject(mensajeUTF = conta + "_" + Server_Banco.ListaCuentas[conta].idCuenta);
                        System.out.println(mensajeUTF);
                        conta++;

                    }

                    while (true);
                    System.out.println("afuera de los loops");

                    //recibier opcion por parte de cliente
                    cuentaSelec = (int) ois.readObject();
                    System.out.println(cuentaSelec);
                    oos.writeObject(mensajeUTF = "Cuanto quieres ingresar");
//recibir cantidad
                    double restar = (double) ois.readObject();
                    restar = Server_Banco.ListaCuentas[cuentaSelec].saldo - restar;
                    Server_Banco.ListaCuentas[cuentaSelec].setSaldo(restar);
                    oos.writeObject(mensajeUTF = "La cantidad a sido retirada, saldo de la cuenta es_" + Server_Banco.ListaCuentas[cuentaSelec].saldo);

                    break;
                case 4:
                    //transferir
                    oos.writeObject(mensajeUTF = "Selecciona la cuentas para empezar a transferir");
                    System.out.println(mensajeUTF);
                    for (contador = 0; contador < Server_Banco.ListaCuentas.length; contador++) {

                        if (Server_Banco.ListaCuentas[contador] == null) {
                            System.out.println("salir del for");
                            break;
                        }
                    }

                    do {
                        if (Server_Banco.ListaCuentas[conta] == null) {

                            oos.writeObject(mensajeUTF = "salir");
                            System.out.println(mensajeUTF);
                            break;

                        } else {
                            oos.writeObject(mensajeUTF = conta + "_" + Server_Banco.ListaCuentas[conta].idCuenta);
                            System.out.println(mensajeUTF);
                            conta++;
                        }

                    }

                    while (true);
                    System.out.println("afuera de los loops");

                    //recibier opcion por parte de cliente
                    cuentaSelec = (int) ois.readObject();
                    System.out.println("aaa" + cuentaSelec);
                    System.out.println(cuentaSelec);
                    oos.writeObject(mensajeUTF = "Cuanto quieres transferir");
//recibir cantidad
                    double transfeir = (double) ois.readObject();
                    transfeir = Server_Banco.ListaCuentas[cuentaSelec].saldo - transfeir;
                    Server_Banco.ListaCuentas[cuentaSelec].setSaldo(transfeir);
                    oos.writeObject(mensajeUTF = "Seleciona la cuenta que lo recibe");
                    conta = 0;

                    do {
                        if (conta == cuentaSelec) {
                            System.out.println(" ");
                        }
                        if (Server_Banco.ListaCuentas[conta] == null) {
                            oos.writeObject(mensajeUTF = "salir");
                            System.out.println(mensajeUTF);

                            break;

                        } else {
                            oos.writeObject(mensajeUTF = conta + "_" + Server_Banco.ListaCuentas[conta].idCuenta);
                            System.out.println(mensajeUTF);
                            conta++;
                        }
                    }

                    while (true);

                    System.out.println("afuera de los loops");

                    //recibier opcion por parte de cliente
                    int cuentatr = (int) ois.readObject();
                    System.out.println(cuentatr);

                    transfeir = Server_Banco.ListaCuentas[cuentatr].saldo + transfeir;
                    Server_Banco.ListaCuentas[cuentatr].setSaldo(transfeir);
                    oos.writeObject(mensajeUTF = "La cantidad a sido transferidad, saldo de la cuenta es_" + Server_Banco.ListaCuentas[cuentatr].saldo);

                    break;


            }

        } while (opcion != 5);

    }

    public void CrearCuentasUser() throws IOException {


        oos.writeObject(mensajeUTF = "Se esta generando el iban de la cuenta espere");
        IBANGenerator ibanGenerator = new IBANGenerator();
        String id = ibanGenerator.generateIBAN("Austria");

        Cuenta cuenta = new Cuenta(0, id);
        for (int a = 0; a < Server_Banco.ListaCuentas.length; a++) {

            if (Server_Banco.ListaCuentas[a] == null) {
                Server_Banco.ListaCuentas[a] = cuenta;
                break;
            }
        }


    }


}

