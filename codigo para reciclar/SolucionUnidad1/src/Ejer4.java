public class Ejer4 {
    public static void main(String[] args) {
        Runtime r = Runtime.getRuntime();

        System.out.println("Memoria disponible en JVM: " + r.freeMemory());
        System.out.println("Memoria total de JVM: " + r.totalMemory());
        System.out.println("Numero de procesadores: " + r.availableProcessors());
        System.out.println("Maxima memoria: " + r.maxMemory());

    }
}
