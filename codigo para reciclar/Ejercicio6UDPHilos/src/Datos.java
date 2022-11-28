import java.io.Serializable;

public class Datos implements Serializable {

    public int op1;
    public int op2;
    public char operando;
    public int resultado;

    public Datos(int op1, int op2, char operando) {
        this.op1 = op1;
        this.op2 = op2;
        this.operando = operando;
    }

    public Datos(int op1, int op2, char operando, int resultado) {
        this.op1 = op1;
        this.op2 = op2;
        this.operando = operando;
        this.resultado = resultado;
    }

    public int getOp1() {
        return op1;
    }

    public void setOp1(int op1) {
        this.op1 = op1;
    }

    public int getOp2() {
        return op2;
    }

    public void setOp2(int op2) {
        this.op2 = op2;
    }

    public char getOperando() {
        return operando;
    }

    public void setOperando(char operando) {
        this.operando = operando;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }
}
