
package Model.Entities;

import Model.Constants.TipoMotor;


public class Motor {
    private int idMotor;
    private TipoMotor tipo;
    private int cilindrada;
    private int potencia;

    public Motor(int idMotor, TipoMotor tipo, int cilindrada, int potencia) {
        this.idMotor = idMotor;
        this.tipo = tipo;
        this.cilindrada = cilindrada;
        this.potencia = potencia;
    }

    public int getIdMotor() {
        return idMotor;
    }

    public void setIdMotor(int idMotor) {
        this.idMotor = idMotor;
    }

    public TipoMotor getTipo() {
        return tipo;
    }

    public void setTipo(TipoMotor tipo) {
        this.tipo = tipo;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }
}

