import java.io.*;
        import java.util.regex.*;
        import java.util.logging.*;


public class ejer1 {

    // variables globales
    private String login;
    private String fichero;
    Logger logger = Logger.getLogger("MyLog");

    // ********************************************************
    private void mostrar_fichero(){
        String strLine;

        try{
            FileInputStream fstream = new FileInputStream(fichero);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            while ((strLine = br.readLine()) != null)
                System.out.println(strLine);

            in.close();

            logger.log(Level.INFO,"\tEl fichero se ha visualizado correctamente");
        }catch (Exception e){
            System.out.println("\tError, el fichero no existe");
            logger.log(Level.WARNING,"\tEl fichero solicitado no existe");
        }


    }

    // ********************************************************
    private void leer_fichero(){
        fichero=new String();

        Pattern pat=null;
        Matcher mat=null;

        // para leer del teclado
        BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));


        try{
            do {

                System.out.println("Introduce el fichero que quieres ver (nombre.extensión):");
                fichero=reader.readLine();

                pat=Pattern.compile("[a-z]{1,8}.[a-z]{3}");
                mat=pat.matcher(fichero);
            }while(!mat.find());

        } catch( Exception e ) {
            System.out.println( e.getMessage() );
            logger.log(Level.SEVERE,"Error al leer el nombre del fichero" + e.getMessage());
        }

    }

    // ********************************************************
    private void leer_login(){
        login=new String();

        Pattern pat=null;
        Matcher mat=null;

        // para leer del teclado
        BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));


        try{
            do {

                System.out.println("Introduce tu login :");
                login=reader.readLine();
                System.out.println(login);
                pat=Pattern.compile("^([\\w])*@[\\w]*$");

                mat=pat.matcher(login);
            }while(!mat.matches());

        } catch( Exception e ) {
            System.out.println( e.getMessage() );
            logger.log(Level.SEVERE,"Error al leer el login del usuario" + e.getMessage());
        }

    }



    // ********************************************************
    private void iniciar_log(){
        FileHandler fh;
        try {

            // Configuro el logger y establezco el formato
           fh = new FileHandler("./log_actividad.log", true);
           // fh=new FileHandler("./log_XML.log",true);
            logger.addHandler(fh);//para añadir las lineas del log al fichero
            logger.setLevel(Level.ALL);
          logger.setUseParentHandlers(false);

           // XMLFormatter formatter = new XMLFormatter ();
            SimpleFormatter formatter = new SimpleFormatter();

            fh.setFormatter(formatter);
            //para sacar los mensajes por consola
            Handler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // ********************************************************
    public ejer1(){
        iniciar_log();



        leer_login();
        System.out.println("\tSe ha conectado el usuario: " + login);
        logger.log(Level.INFO,"Se ha conectado el usuario: " + login);

        leer_fichero();
        System.out.println("\tEl cliente quiere leer el fichero: " + fichero);
        logger.log(Level.INFO,"El cliente quiere leer el fichero: " + fichero);

        mostrar_fichero();




    }

    public static void main( String[] arg ) {
        new ejer1();
    }

}

