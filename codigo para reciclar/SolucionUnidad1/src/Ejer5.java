import java.io.File;

public class Ejer5 {
    public static void main(String[] args) {
        ProcessBuilder pB=new ProcessBuilder();
        pB.directory(new File("c/:psp/"));
        java.util.Map<String,String> env=pB.environment();
        System.out.println(env);
        System.out.println("Num procesadores:"+ env.get("NUMBER_OF_PROCESSORS"));
        System.out.println(env.get("LOCALAPPDATA"));
    }

}

