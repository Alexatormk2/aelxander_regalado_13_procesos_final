import java.io.BufferedReader;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("introduce que tipo de dato quieres validar\n");

        System.out.println("1-Matricula\n");
        System.out.println("2-DNI\n");
        Scanner respuesta = new Scanner(System.in);
        int num = respuesta.nextInt();
        if (num == 1) {

            System.out.println("Introduce matricula a validar\n");
            Scanner res = new Scanner(System.in);
            String matricula = res.next();
            System.out.println(matricula);
            if (Main.validarMatricula(matricula)) {
                System.out.println("Matricula valida");

            } else
                System.out.println("Matricula incorrecta");
        } else {
            System.out.println("Introduce DNI a validar");
            String DNI = respuesta.next();

            if (Main.validarDNI(DNI))
                System.out.println("DNI valido");

            else
                System.out.println("DNI incorrecto");

        }
    }




    public static boolean validarMatricula(String matricula) {
        //utilizamos directamente el metodo matches sin necesidad de crear objetos
        //de ningun tipo
        return matricula.matches("^[0-9]{4}-[A-Z]{3}$");

    }
    public static boolean validarDNI(String DNI) {
        //utilizamos directamente el metodo matches sin necesidad de crear objetos
        //de ningun tipo
        return DNI.matches("^[0-9]{8}-[a-zA-Z]$");
    }


}



