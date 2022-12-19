import java.awt.*;
import java.io.*;
import java.net.Socket;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Cliente_Banco {
    public static String mensaje;
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    final static int puerto = 5500;
    static ObjectOutputStream oos;
    static ObjectInputStream ois;
    static Socket socket;

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, SignatureException {
        System.setProperty("javax.net.ssl.trustStore", "almacenUser");
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");
        int opcion = 0;
        while (opcion != 3) {

            //Conectamos al cliente
            Socket socket = new Socket("localhost", puerto);
            // Creamos los flujos
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            PublicKey clave = (PublicKey) ois.readObject();
            System.out.println("Leemos la clave");
            //obtenemos la clave publica

            System.out.println("La clave recibida es: " + clave);


            System.out.println("sdkjgjdasdflhkds.kffkkf");
            ///recibimos mensaje del server
            mensaje = ois.readObject().toString();

            System.out.println(mensaje);
            //escribimos opcion del menu
            System.out.println("Escribe el numero de la opcion");
            opcion = Integer.parseInt(br.readLine());

            System.out.println("opcion mandada");

            if (opcion == 3) {
                oos.writeObject(opcion);
                System.out.println("Cerrando sesion.........");
                oos.close();
                ois.close();
                socket.close();

            } else if (opcion == 1 || opcion == 2) {

                oos.writeObject(opcion);


                switch (opcion) {

                    case 1:

                        ///recibmimos clave 2

                        ////////////////////////////////////////
                        System.out.println("clave2 recibida");
                        PublicKey clave2 = (PublicKey) ois.readObject();
                        /////////////////////////////////
                        System.out.println("Cargando menu inicio sesion");

                        mensaje = ois.readObject().toString();
                        System.out.println(mensaje);

                        ///leemoms uusarios
                        System.out.println("Leer usuarios");

                        mensaje = ois.readObject().toString();
                        while (!mensaje.equals("salir")) {
                            System.out.println(mensaje);
                            mensaje = ois.readObject().toString();


                        }
                        System.out.println("da dato para mandar");

                        int opcion2 = Integer.parseInt(br.readLine());
                        System.out.println("mandamos seleccion");
                        oos.writeObject(opcion2);
                        System.out.println("leemos siguietne paso");
                        ///cifrado de contraseina de inicio sesion
                        /////////////////
                        mensaje = ois.readObject().toString();
                        System.out.println(mensaje);
                        Scanner sc = new Scanner(System.in);
                        String texto = sc.nextLine();
                        String encri = encriptar(texto);
                        Cipher cipher = Cipher.getInstance("RSA");
                        cipher.init(Cipher.ENCRYPT_MODE, clave2);
                        //directamente cifrarlo en un array de bytes, y no hacer conversiones a string
                        byte[] mensajeCifrado = cipher.doFinal(encri.getBytes());

                        oos.writeObject(mensajeCifrado);

                        mensaje = ois.readObject().toString();
                        Runtime.getRuntime().exec("Notepad.exe readmne.txt");
                        System.out.println("Acepta las normas ?" +
                                "1.SI" +
                                "2.No");
                        int normas = 0;
                        normas = Integer.parseInt(br.readLine());
                        switch (normas) {
                            case 1:
                                System.out.println("Cargando menu de usuario");
                                break;

                            case 2:
                                System.out.println("Cerrando programa");
                                System.exit(0);
                                break;

                        }

                        menuOperaciones();

                        break;
                    case 2:
                        PublicKey clave3 = (PublicKey) ois.readObject();
                        System.out.println("clave crear usuario recibida");

                        mensaje = ois.readObject().toString();
                        System.out.println(mensaje);
                        String nombre = br.readLine();
                        cipher = Cipher.getInstance("RSA");
                        cipher.init(Cipher.ENCRYPT_MODE, clave3);
                        //directamente cifrarlo en un array de bytes, y no hacer conversiones a string
                        mensajeCifrado = cipher.doFinal(nombre.getBytes());

                        oos.writeObject(mensajeCifrado);
                        mensaje = ois.readObject().toString();
                        System.out.println(mensaje);
                        //mandar dni
                        String dni = br.readLine();
                        cipher = Cipher.getInstance("RSA");
                        cipher.init(Cipher.ENCRYPT_MODE, clave3);
                        //directamente cifrarlo en un array de bytes, y no hacer conversiones a string
                        mensajeCifrado = cipher.doFinal(dni.getBytes());
                        oos.writeObject(mensajeCifrado);
                        //recibier si esta bien o no
                        mensaje = ois.readObject().toString();
                        System.out.println(mensaje);
                        //si esta correcto se sigue adelante
                        mensaje = ois.readObject().toString();
                        System.out.println(mensaje);
                        //mandar email
                        String email = br.readLine();
                        cipher = Cipher.getInstance("RSA");
                        cipher.init(Cipher.ENCRYPT_MODE, clave3);
                        //directamente cifrarlo en un array de bytes, y no hacer conversiones a string
                        mensajeCifrado = cipher.doFinal(email.getBytes());
                        oos.writeObject(mensajeCifrado);

                        //recibir si esta mal o bien
                        mensaje = ois.readObject().toString();
                        System.out.println(mensaje);
                        //seguir adelante en caso de si
                        mensaje = ois.readObject().toString();
                        System.out.println(mensaje);
                        ////////////////////////////
                        //contra
                        String contra = br.readLine();
                        cipher = Cipher.getInstance("RSA");
                        cipher.init(Cipher.ENCRYPT_MODE, clave3);
                        String encri2 = encriptar(contra);
                        mensajeCifrado = cipher.doFinal(encri2.getBytes());
                        oos.writeObject(mensajeCifrado);
                        /////////////////////////
                        //siguiente paso
                        mensaje = ois.readObject().toString();
                        System.out.println(mensaje);
                        int edad = Integer.parseInt(br.readLine());
                        oos.writeObject(edad);
                        mensaje = ois.readObject().toString();
                        System.out.println(mensaje);
                        String usuario = br.readLine();
                        oos.writeObject(usuario);
                        break;

                }
            }


            Server_Banco.guardarDatosUsuario();
            Server_Banco.guardarDatosCuentas();
            oos.close();
            ois.close();
            socket.close();
        }
    }

    public static void menuOperaciones() throws IOException, ClassNotFoundException {


        int opcion = 0;
        int cuentaOp = 0;
        ///recibmimos clave 2
        String mensaje;
        ////////////////////////////////////////
        System.out.println("clave2 recibida");

        PublicKey clave = (PublicKey) ois.readObject();
        do {

            try {


                mensaje = ois.readObject().toString();
                System.out.println(mensaje);
                System.out.println("dar opcion");
                opcion = Integer.parseInt(br.readLine());
                oos.writeObject(opcion);
                System.out.println("switch comienzo");
                switch (opcion) {

                    case 1:

                        //recibimos los codigos de las cuentas para selecionar una
                        mensaje = (String) ois.readObject();
                        System.out.println(mensaje);
                        while (!mensaje.equals("salir")) {
                            System.out.println(mensaje);
                            mensaje = ois.readObject().toString();


                        }
                        //mandamos opcion seleccionada
                        cuentaOp = Integer.parseInt(br.readLine());
                        oos.writeObject(cuentaOp);
                        //recibir respuesta
                        mensaje = (String) ois.readObject();
                        System.out.println(mensaje);

                        break;
                    case 2:
                        //ingresar
                        mensaje = (String) ois.readObject();
                        System.out.println(mensaje);
                        while (!mensaje.equals("salir")) {
                            System.out.println(mensaje);
                            mensaje = ois.readObject().toString();


                        }
                        cuentaOp = Integer.parseInt(br.readLine());
                        oos.writeObject(cuentaOp);

                        //recibir respuesta
                        mensaje = (String) ois.readObject();
                        System.out.println(mensaje);
                        double ingresar = Integer.parseInt(br.readLine());
                        oos.writeObject(ingresar);


                        mensaje = ois.readObject().toString();
                        System.out.println(mensaje);
                        break;
                    case 3:
                        //retirar
                        mensaje = (String) ois.readObject();
                        System.out.println(mensaje);
                        while (!mensaje.equals("salir")) {
                            System.out.println(mensaje);
                            mensaje = ois.readObject().toString();


                        }
                        cuentaOp = Integer.parseInt(br.readLine());
                        oos.writeObject(cuentaOp);

                        //recibir valores
                        mensaje = (String) ois.readObject();
                        System.out.println(mensaje);
                        double retirar = Integer.parseInt(br.readLine());
                        oos.writeObject(retirar);


                        mensaje = ois.readObject().toString();
                        System.out.println(mensaje);

                        break;
                    case 4:
//transferir
                        mensaje = (String) ois.readObject();
                        System.out.println(mensaje);
                        while (!mensaje.equals("salir")) {
                            System.out.println(mensaje);
                            mensaje = ois.readObject().toString();
                        }

                        cuentaOp = Integer.parseInt(br.readLine());
                        oos.writeObject(cuentaOp);
                        System.out.println("afuera loop parte 1 de transferencia");
                        //recibir valores
                        mensaje = (String) ois.readObject();
                        System.out.println(mensaje);
                        double transferir = Integer.parseInt(br.readLine());
                        oos.writeObject(transferir);


                        mensaje = ois.readObject().toString();
                        System.out.println(mensaje);

//cuenta que recibe
                        mensaje = (String) ois.readObject();
                        System.out.println(mensaje);
                        while (!mensaje.equals("salir")) {
                            System.out.println(mensaje);
                            mensaje = ois.readObject().toString();


                        }
                        cuentaOp = Integer.parseInt(br.readLine());
                        oos.writeObject(cuentaOp);
                        mensaje = ois.readObject().toString();
                        System.out.println(mensaje);

                        break;
                    case 5:

                        Server_Banco.guardarDatosUsuario();
                        Server_Banco.guardarDatosCuentas();
                        oos.close();
                        ois.close();
                        socket.close();
                        break;

                }

            } catch (NumberFormatException e) {
                System.out.println("se espereba un numero");

            }
        } while (opcion != 5);

        /////////////////////////////////

    }

    static String encriptar(String contra) throws UnsupportedEncodingException, NoSuchAlgorithmException {


        byte[] contraArray = HASHManager.getDigest(contra.getBytes("UTF-8"));
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

