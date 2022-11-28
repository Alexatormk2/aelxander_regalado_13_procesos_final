public class pr {
    public static void main(String[] args) {
        String m="hola";
        byte[] array=m.getBytes();
        System.out.println(array.toString());
        byte[] array2=m.getBytes();
        System.out.println(array2.toString());
if(array.equals(array2)){}
        System.out.printf("Iguales");
    }
}
