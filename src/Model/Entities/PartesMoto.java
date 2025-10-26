package Model.Entities;

public class PartesMoto {

    private LlantaDelantera llantaDelantera;
    private LlantaTrasera llantaTrasera;
    private Chasis chasis;
    private Motor motor;
    private Asiento asiento;
    private FrenoDelantero frenoDelantero;

    private FrenoTrasero frenoTrasero;
    private Transmision transmision;

    public PartesMoto(LlantaDelantera llantaDelantera, LlantaTrasera llantaTrasera,
                      Chasis chasis, Motor motor, Asiento asiento, FrenoDelantero frenoDelantero) {
        this.llantaDelantera = llantaDelantera;
        this.llantaTrasera = llantaTrasera;
        this.chasis = chasis;
        this.motor = motor;
        this.asiento = asiento;
        this.frenoDelantero = frenoDelantero;
    }

    public PartesMoto(LlantaDelantera llantaDelantera, LlantaTrasera llantaTrasera,
                      Chasis chasis, Motor motor, Asiento asiento, FrenoDelantero frenoDelantero,
                      FrenoTrasero frenoTrasero, Transmision transmision) {
        this(llantaDelantera, llantaTrasera, chasis, motor, asiento, frenoDelantero);
        this.frenoTrasero = frenoTrasero;
        this.transmision = transmision;
    }

    public LlantaDelantera getLlantaDelantera() {
        return llantaDelantera;
    }

    public void setLlantaDelantera(LlantaDelantera llantaDelantera) {
        this.llantaDelantera = llantaDelantera;
    }

    public LlantaTrasera getLlantaTrasera() {
        return llantaTrasera;
    }

    public void setLlantaTrasera(LlantaTrasera llantaTrasera) {
        this.llantaTrasera = llantaTrasera;
    }

    public Chasis getChasis() {
        return chasis;
    }

    public void setChasis(Chasis chasis) {
        this.chasis = chasis;
    }

    public Motor getMotor() {
        return motor;
    }

    public void setMotor(Motor motor) {
        this.motor = motor;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public FrenoDelantero getFrenoDelantero() {
        return frenoDelantero;
    }

    public void setFrenoDelantero(FrenoDelantero frenoDelantero) {
        this.frenoDelantero = frenoDelantero;
    }

    public FrenoTrasero getFrenoTrasero() {
        return frenoTrasero;
    }

    public void setFrenoTrasero(FrenoTrasero frenoTrasero) {
        this.frenoTrasero = frenoTrasero;
    }

    public Transmision getTransmision() {
        return transmision;
    }

    public void setTransmision(Transmision transmision) {
        this.transmision = transmision;
    }
}
