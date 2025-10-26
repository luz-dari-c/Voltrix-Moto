
package Model.Entities;

import Model.Constants.TipoMotor;
import Utilidades.GeneradorDeIdPartes;


public class Motor {
    private String idMotor;
    private TipoMotor tipo;
    private int cilindrada;
    private int potencia;

    public Motor(TipoMotor tipo, int cilindrada, int potencia) {
        this.idMotor = GeneradorDeIdPartes.generarId("MTR");
        this.tipo = tipo;
        this.cilindrada = cilindrada;
        this.potencia = potencia;
    }

    public String getIdMotor() {
        return idMotor;
    }

    public void setIdMotor(String idMotor) {
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

