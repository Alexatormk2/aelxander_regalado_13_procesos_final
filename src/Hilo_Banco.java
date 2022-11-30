


import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.IllegalFormatCodePointException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


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
                            System.out.println("opcion 2 sin hacer");
                            oos.writeObject(mensajeUTF = "Opcion 2 en obras");
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


                byte[] mensaje = (byte[]) ois.readObject();
                //preparamos el Cipher para descifrar

                Cipher descipher = Cipher.getInstance("RSA");
                descipher.init(Cipher.DECRYPT_MODE, privada);

                String mensaje_descifrado = new String(descipher.doFinal(mensaje));

                if (mensaje_descifrado.equals(Server_Banco.usuarioAactual.contraseina)) {

                    oos.writeObject(mensajeUTF = "Inicio de sesion con exicto bienvenido: " + Server_Banco.usuarioAactual.nombre);
                    System.out.println(mensaje_descifrado);
                } else {

                    oos.writeObject(mensajeUTF = " Error contraseina incorrecta, volviendo a menu principal");
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
        }
    }

}

