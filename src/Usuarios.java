import java.io.Serializable;

public class Usuarios implements Serializable {

    String nombre;
    String usuario;
    String contraseina;
    int edad;
    String email;


    public Usuarios(String nombre, String usuario, String contraseina, int edad, String email) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.contraseina = contraseina;
        this.edad = edad;
        this.email = email;
    }

    public Usuarios() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseina() {
        return contraseina;
    }

    public void setContraseina(String contraseina) {
        this.contraseina = contraseina;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
