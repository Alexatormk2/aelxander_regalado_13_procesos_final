import java.io.Serializable;

public class Cuenta implements Serializable {

    double saldo;
    String idCuenta;

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(String idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Cuenta(double saldo, String idCuenta) {
        this.saldo = saldo;
        this.idCuenta = idCuenta;
    }

    public Cuenta() {
    }
}
