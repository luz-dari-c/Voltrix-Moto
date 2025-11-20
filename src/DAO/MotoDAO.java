package DAO;

import Model.Constants.*;
import Model.Entities.Asiento;
import Model.Entities.Chasis;
import Model.Entities.Freno;
import Model.Entities.FrenoDelantero;
import Model.Entities.FrenoTrasero;
import Model.Entities.Llanta;
import Model.Entities.LlantaDelantera;
import Model.Entities.LlantaTrasera;
import Model.Entities.Moto;
import Model.Entities.Motor;
import Model.Entities.PartesMoto;
import Model.Entities.Transmision;
import Utilidades.LocalDateAdapter;
import Utilidades.GeneradorDeIdPartes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MotoDAO {

    private static final String RUTA_JSON = "src/Resources/Data/motos.json";
    private final Gson gson;
    private static MotoDAO instancia;

    public MotoDAO() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        crearDirectoriosSiNoExisten();
    }

    public static synchronized MotoDAO getInstancia() {
        if (instancia == null) {
            instancia = new MotoDAO();
        }
        return instancia;
    }

    public List<Moto> cargarTodas() {
        File archivo = new File(RUTA_JSON);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                guardarTodas(new ArrayList<>());
                return new ArrayList<>();
            } catch (IOException e) {
                System.err.println("Error al crear archivo JSON: " + e.getMessage());
                return new ArrayList<>();
            }
        }

        if (archivo.length() == 0) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(RUTA_JSON)) {
            Type tipoListaMoto = new TypeToken<ArrayList<Moto>>() {
            }.getType();
            List<Moto> motos = gson.fromJson(reader, tipoListaMoto);
            return motos != null ? motos : new ArrayList<>();
        } catch (JsonSyntaxException | IOException e) {
            System.err.println("Error al leer archivo JSON: " + e.getMessage());
            guardarTodas(new ArrayList<>());
            return new ArrayList<>();
        }
    }

    public boolean guardarMoto(Moto moto) {
        if (moto == null) {
            return false;
        }

        List<Moto> motos = cargarTodas();

        boolean existe = motos.stream().anyMatch(m -> m.getPlaca().equalsIgnoreCase(moto.getPlaca()));
        if (existe) {
            System.err.println(" Ya existe una moto con la placa: " + moto.getPlaca());
            return false;
        }

        if (moto.getPlaca().startsWith("BSE-")) {
            boolean yaExisteBase = motos.stream()
                    .anyMatch(m -> m.getPlaca().startsWith("BSE-") && m.getTipoMoto() == moto.getTipoMoto());

            if (yaExisteBase) {
                System.err.println(" Ya existe una moto base para el tipo " + moto.getTipoMoto());
                return false;
            }
        }

        if (moto.getIdMoto() == 0) {
            moto.setIdMoto(motos.size() + 1);
        }

        if (moto.getEstado() == null) {
            moto.setEstado(EstadoMoto.DISPONIBLE);
        }

        PartesMoto partes = moto.getPartesMoto();
        if (partes != null) {

            if (partes.getMotor() != null && partes.getMotor().getIdMotor() == null) {
                partes.getMotor().setIdMotor(GeneradorDeIdPartes.generarId("MTR"));
            }

            if (partes.getChasis() != null && partes.getChasis().getIdChasis() == null) {
                partes.getChasis().setIdChasis(GeneradorDeIdPartes.generarId("CHS"));
            }

            if (partes.getAsiento() != null && partes.getAsiento().getIdAsiento() == null) {
                partes.getAsiento().setIdAsiento(GeneradorDeIdPartes.generarId("AST"));
            }

            if (partes.getFrenoDelantero() != null && partes.getFrenoDelantero().getIdFrenoDelantero() == null) {
                partes.getFrenoDelantero().setIdFrenoDelantero(GeneradorDeIdPartes.generarId("FRD"));
            }

            if (partes.getFrenoTrasero() != null && partes.getFrenoTrasero().getIdFrenoTrasero() == null) {
                partes.getFrenoTrasero().setIdFrenoTrasero(GeneradorDeIdPartes.generarId("FRT"));
            }

            if (partes.getLlantaDelantera() != null && partes.getLlantaDelantera().getIdLlantaDelantera() == null) {
                partes.getLlantaDelantera().setIdLlantaDelantera(GeneradorDeIdPartes.generarId("LLD"));
            }

            if (partes.getLlantaTrasera() != null && partes.getLlantaTrasera().getIdLlantaTrasera() == null) {
                partes.getLlantaTrasera().setIdLlantaTrasera(GeneradorDeIdPartes.generarId("LLT"));
            }

            if (partes.getTransmision() != null && partes.getTransmision().getIdTransmision() == null) {
                partes.getTransmision().setIdTransmision(GeneradorDeIdPartes.generarId("TRS"));
            }
        }

        int idExistente = moto.getIdMoto();
        boolean idDuplicado = motos.stream().anyMatch(m -> m.getIdMoto() == idExistente);

        if (idDuplicado || idExistente == 0) {
            int nuevoId = motos.stream()
                    .mapToInt(Moto::getIdMoto)
                    .max()
                    .orElse(0) + 1;
            moto.setIdMoto(nuevoId);
        }

        motos.add(moto);
        guardarTodas(motos);
        return true;

    }

    public boolean eliminarMoto(int idMoto) {
        List<Moto> motos = cargarTodas();
        Moto moto = motos.stream()
                .filter(m -> m.getIdMoto() == idMoto)
                .findFirst()
                .orElse(null);

        if (moto == null) {
            System.err.println("Moto con ID " + idMoto + " no encontrada.");
            return false;
        }

        motos.remove(moto);
        guardarTodas(motos);
        return true;
    }

   
    public boolean actualizarPorPlacaBase(
            String placaBase,
            String nuevoModelo,
            Boolean nuevaParrilla,
            Boolean nuevoMaletero,
            Integer nuevoCilindraje,
            // Motor
            TipoMotor nuevoTipoMotor,
            Integer nuevaCilindradaMotor,
            Integer nuevaPotenciaMotor,
            // Chasis
            MaterialChasis nuevoMaterialChasis,
            TipoChasis nuevoTipoChasis,
            // Asiento
            MaterialAsiento nuevoMaterialAsiento,
            CapacidadAsiento nuevaCapacidadAsiento,
            // Transmisión
            TipoTransmision nuevoTipoTransmision,
            TipoVelocidades nuevasVelocidades,
            // Llanta
            String tipoLlantaSeleccionada,
            MedidaLlanta nuevaMedidaLlanta,
            String nuevaMarcaLlanta,
            MaterialLlanta nuevoMaterialLlanta,
            String nuevoModeloLlanta,
            // Freno
            String tipoFrenoSeleccionado,
            String nuevaMarcaFreno,
            MaterialFreno nuevoMaterialFreno,
            String nuevoModeloFreno
    ) {
        List<Moto> motos = cargarTodas();
        Moto motoBase = null;

        for (Moto moto : motos) {
            if (moto.getPlaca().equalsIgnoreCase(placaBase) && moto.getPlaca().startsWith("BSE-")) {
                motoBase = moto;
                break;
            }
        }

        if (motoBase == null) {
            System.err.println("No se encontró una moto base con placa: " + placaBase);
            return false;
        }

        TipoMoto tipoMotoBase = motoBase.getTipoMoto();
        boolean hayCambios = false;

        if (nuevoModelo != null && !nuevoModelo.trim().isEmpty()) {
            motoBase.setModelo(nuevoModelo);
            hayCambios = true;
        }
        if (nuevaParrilla != null) {
            motoBase.setTieneParrilla(nuevaParrilla);
            hayCambios = true;
        }
        if (nuevoMaletero != null) {
            motoBase.setTieneMaletero(nuevoMaletero);
            hayCambios = true;
        }
        if (nuevoCilindraje != null && nuevoCilindraje > 0) {
            motoBase.setCilindraje(nuevoCilindraje);
            hayCambios = true;
        }

        PartesMoto partesBase = motoBase.getPartesMoto();
        if (partesBase != null) {

            if (partesBase.getMotor() != null) {
                if (nuevoTipoMotor != null) {
                    partesBase.getMotor().setTipo(nuevoTipoMotor);
                    hayCambios = true;
                }
                if (nuevaCilindradaMotor != null) {
                    partesBase.getMotor().setCilindrada(nuevaCilindradaMotor);
                    hayCambios = true;
                }
                if (nuevaPotenciaMotor != null) {
                    partesBase.getMotor().setPotencia(nuevaPotenciaMotor);
                    hayCambios = true;
                }
            }

            if (partesBase.getChasis() != null) {
                if (nuevoMaterialChasis != null) {
                    partesBase.getChasis().setMaterial(nuevoMaterialChasis);
                    hayCambios = true;
                }
                if (nuevoTipoChasis != null) {
                    partesBase.getChasis().setTipo(nuevoTipoChasis);
                    hayCambios = true;
                }
            }

            if (partesBase.getAsiento() != null) {
                if (nuevoMaterialAsiento != null) {
                    partesBase.getAsiento().setMaterial(nuevoMaterialAsiento);
                    hayCambios = true;
                }
                if (nuevaCapacidadAsiento != null) {
                    partesBase.getAsiento().setCapacidad(nuevaCapacidadAsiento);
                    hayCambios = true;
                }
            }

            if (partesBase.getTransmision() != null) {
                if (nuevoTipoTransmision != null) {
                    partesBase.getTransmision().setTipoTransmision(nuevoTipoTransmision);
                    hayCambios = true;
                }
                if (nuevasVelocidades != null) {
                    partesBase.getTransmision().setVelocidades(nuevasVelocidades);
                    hayCambios = true;
                }
            }

            if (tipoLlantaSeleccionada != null) {
                switch (tipoLlantaSeleccionada) {
                    case "Ambas":
                        actualizarLlanta(partesBase.getLlantaDelantera(), nuevaMedidaLlanta, nuevaMarcaLlanta, nuevoMaterialLlanta, nuevoModeloLlanta);
                        actualizarLlanta(partesBase.getLlantaTrasera(), nuevaMedidaLlanta, nuevaMarcaLlanta, nuevoMaterialLlanta, nuevoModeloLlanta);
                        hayCambios = true;
                        break;
                    case "Llanta Delantera":
                        actualizarLlanta(partesBase.getLlantaDelantera(), nuevaMedidaLlanta, nuevaMarcaLlanta, nuevoMaterialLlanta, nuevoModeloLlanta);
                        hayCambios = true;
                        break;
                    case "Llanta Trasera":
                        actualizarLlanta(partesBase.getLlantaTrasera(), nuevaMedidaLlanta, nuevaMarcaLlanta, nuevoMaterialLlanta, nuevoModeloLlanta);
                        hayCambios = true;
                        break;
                }
            }

            if (tipoFrenoSeleccionado != null) {
                switch (tipoFrenoSeleccionado) {
                    case "Ambos":
                        actualizarFreno(partesBase.getFrenoDelantero(), nuevaMarcaFreno, nuevoMaterialFreno, nuevoModeloFreno);
                        actualizarFreno(partesBase.getFrenoTrasero(), nuevaMarcaFreno, nuevoMaterialFreno, nuevoModeloFreno);
                        hayCambios = true;
                        break;
                    case "Freno Delantero":
                        actualizarFreno(partesBase.getFrenoDelantero(), nuevaMarcaFreno, nuevoMaterialFreno, nuevoModeloFreno);
                        hayCambios = true;
                        break;
                    case "Freno Trasero":
                        actualizarFreno(partesBase.getFrenoTrasero(), nuevaMarcaFreno, nuevoMaterialFreno, nuevoModeloFreno);
                        hayCambios = true;
                        break;
                }
            }
        }

        if (!hayCambios) {
            System.err.println("No se realizó ningún cambio (todos los campos vacíos)");
            return false;
        }

        for (Moto moto : motos) {
            if (moto.getTipoMoto() == tipoMotoBase
                    && !moto.getPlaca().startsWith("BSE-")
                    && moto.getEstado() == EstadoMoto.DISPONIBLE) {
                copiarCambios(motoBase, moto);
            }
        }

        guardarTodas(motos);
        return true;
    }

    private void actualizarLlanta(Llanta llanta, MedidaLlanta medida, String marca, MaterialLlanta material, String modelo) {
        if (llanta == null) {
            return;
        }
        if (medida != null) {
            llanta.setMedida(medida);
        }
        if (marca != null && !marca.isEmpty()) {
            llanta.setMarca(marca);
        }
        if (material != null) {
            llanta.setMaterial(material);
        }
        if (modelo != null && !modelo.isEmpty()) {
            llanta.setModelo(modelo);
        }
    }

    private void actualizarFreno(Freno freno, String marca, MaterialFreno material, String modelo) {
        if (freno == null) {
            return;
        }
        if (marca != null && !marca.isEmpty()) {
            freno.setMarca(marca);
        }
        if (material != null) {
            freno.setMaterial(material);
        }
        if (modelo != null && !modelo.isEmpty()) {
            freno.setModelo(modelo);
        }
    }

    private void copiarCambios(Moto origen, Moto destino) {
        if (origen == null || destino == null) {
            return;
        }

        destino.setModelo(origen.getModelo());
        destino.setTieneParrilla(origen.isTieneParrilla());
        destino.setTieneMaletero(origen.isTieneMaletero());
        destino.setCilindraje(origen.getCilindraje());

        PartesMoto partesOrigen = origen.getPartesMoto();
        PartesMoto partesDestino = destino.getPartesMoto();

        if (partesOrigen == null || partesDestino == null) {
            return;
        }

        if (partesOrigen.getMotor() != null && partesDestino.getMotor() != null) {
            partesDestino.getMotor().setTipo(partesOrigen.getMotor().getTipo());
            partesDestino.getMotor().setCilindrada(partesOrigen.getMotor().getCilindrada());
            partesDestino.getMotor().setPotencia(partesOrigen.getMotor().getPotencia());
        }

        if (partesOrigen.getChasis() != null && partesDestino.getChasis() != null) {
            partesDestino.getChasis().setMaterial(partesOrigen.getChasis().getMaterial());
            partesDestino.getChasis().setTipo(partesOrigen.getChasis().getTipo());
        }

        if (partesOrigen.getAsiento() != null && partesDestino.getAsiento() != null) {
            partesDestino.getAsiento().setMaterial(partesOrigen.getAsiento().getMaterial());
            partesDestino.getAsiento().setCapacidad(partesOrigen.getAsiento().getCapacidad());
        }

        if (partesOrigen.getTransmision() != null && partesDestino.getTransmision() != null) {
            partesDestino.getTransmision().setTipoTransmision(partesOrigen.getTransmision().getTipoTransmision());
            partesDestino.getTransmision().setVelocidades(partesOrigen.getTransmision().getVelocidades());
        }

        if (partesOrigen.getLlantaDelantera() != null && partesDestino.getLlantaDelantera() != null) {
            partesDestino.getLlantaDelantera().setMedida(partesOrigen.getLlantaDelantera().getMedida());
            partesDestino.getLlantaDelantera().setMarca(partesOrigen.getLlantaDelantera().getMarca());
            partesDestino.getLlantaDelantera().setMaterial(partesOrigen.getLlantaDelantera().getMaterial());
            partesDestino.getLlantaDelantera().setModelo(partesOrigen.getLlantaDelantera().getModelo());
        }
        if (partesOrigen.getLlantaTrasera() != null && partesDestino.getLlantaTrasera() != null) {
            partesDestino.getLlantaTrasera().setMedida(partesOrigen.getLlantaTrasera().getMedida());
            partesDestino.getLlantaTrasera().setMarca(partesOrigen.getLlantaTrasera().getMarca());
            partesDestino.getLlantaTrasera().setMaterial(partesOrigen.getLlantaTrasera().getMaterial());
            partesDestino.getLlantaTrasera().setModelo(partesOrigen.getLlantaTrasera().getModelo());
        }

        if (partesOrigen.getFrenoDelantero() != null && partesDestino.getFrenoDelantero() != null) {
            partesDestino.getFrenoDelantero().setMarca(partesOrigen.getFrenoDelantero().getMarca());
            partesDestino.getFrenoDelantero().setMaterial(partesOrigen.getFrenoDelantero().getMaterial());
            partesDestino.getFrenoDelantero().setModelo(partesOrigen.getFrenoDelantero().getModelo());
        }
        if (partesOrigen.getFrenoTrasero() != null && partesDestino.getFrenoTrasero() != null) {
            partesDestino.getFrenoTrasero().setMarca(partesOrigen.getFrenoTrasero().getMarca());
            partesDestino.getFrenoTrasero().setMaterial(partesOrigen.getFrenoTrasero().getMaterial());
            partesDestino.getFrenoTrasero().setModelo(partesOrigen.getFrenoTrasero().getModelo());
        }
    }

    public boolean duplicarMotoPorTipo(
            TipoMoto tipoMoto,
            TipoColorMoto nuevoColor,
            String nuevaMarca,
            int cantidad
    ) {
        if (cantidad <= 0) {
            System.err.println("La cantidad debe ser un número positivo mayor que 0.");
            return false;
        }

        List<Moto> motos = cargarTodas();

        Moto base = motos.stream()
                .filter(m -> m.getTipoMoto() == tipoMoto && m.getPlaca().startsWith("BSE-"))
                .findFirst()
                .orElse(null);

        if (base == null) {
            System.err.println("No existe una moto base para el tipo: " + tipoMoto);
            return false;
        }

        boolean todasGuardadas = true;

        for (int i = 0; i < cantidad; i++) {

            PartesMoto p = base.getPartesMoto();

            PartesMoto partesClonadas = new PartesMoto(
                    (p.getLlantaDelantera() != null)
                    ? new LlantaDelantera(
                            p.getLlantaDelantera().getMedida(),
                            p.getLlantaDelantera().getMarca(),
                            p.getLlantaDelantera().getModelo(),
                            p.getLlantaDelantera().getMaterial()
                    )
                    : null,
                    (p.getLlantaTrasera() != null)
                    ? new LlantaTrasera(
                            p.getLlantaTrasera().getMedida(),
                            p.getLlantaTrasera().getMarca(),
                            p.getLlantaTrasera().getModelo(),
                            p.getLlantaTrasera().getMaterial()
                    )
                    : null,
                    (p.getChasis() != null)
                    ? new Chasis(
                            p.getChasis().getMaterial(),
                            p.getChasis().getTipo()
                    )
                    : null,
                    (p.getMotor() != null)
                    ? new Motor(
                            p.getMotor().getTipo(),
                            p.getMotor().getCilindrada(),
                            p.getMotor().getPotencia()
                    )
                    : null,
                    (p.getAsiento() != null)
                    ? new Asiento(
                            p.getAsiento().getMaterial(),
                            p.getAsiento().getCapacidad()
                    )
                    : null,
                    (p.getFrenoDelantero() != null)
                    ? new FrenoDelantero(
                            p.getFrenoDelantero().getMarca(),
                            p.getFrenoDelantero().getModelo(),
                            p.getFrenoDelantero().getMaterial()
                    )
                    : null,
                    (p.getFrenoTrasero() != null)
                    ? new FrenoTrasero(
                            p.getFrenoTrasero().getMarca(),
                            p.getFrenoTrasero().getModelo(),
                            p.getFrenoTrasero().getMaterial()
                    )
                    : null,
                    (p.getTransmision() != null)
                    ? new Transmision(
                            p.getTransmision().getTipoTransmision(),
                            p.getTransmision().getVelocidades()
                    )
                    : null
            );

            Moto nuevaMoto = new Moto(
                    (nuevaMarca != null && !nuevaMarca.isBlank()) ? nuevaMarca : base.getMarca(),
                    base.getModelo(),
                    LocalDate.now(),
                    base.getPrecio(),
                    partesClonadas,
                    base.getTipoMoto(),
                    (nuevoColor != null) ? nuevoColor : base.getTipoColorMoto(),
                    base.getCilindraje(),
                    base.isTieneParrilla(),
                    base.isTieneMaletero()
            );

            nuevaMoto.setPlaca(Utilidades.GeneradorDePlaca.generarPlaca());

            boolean guardada = guardarMoto(nuevaMoto);

            if (!guardada) {
                todasGuardadas = false;
                System.err.println("Error al guardar una de las motos clonadas (" + (i + 1) + ").");
            }
        }

        return todasGuardadas;
    }

    public boolean actualizarMotoIndividual(
            int idMoto,
            String nuevaMarca,
            TipoColorMoto nuevoColor
    ) {
        List<Moto> motos = cargarTodas();

        for (Moto moto : motos) {
            if (moto.getIdMoto() == idMoto) {
                if (nuevaMarca != null && !nuevaMarca.trim().isEmpty()) {
                    moto.setMarca(nuevaMarca);
                }
                if (nuevoColor != null) {
                    moto.setTipoColorMoto(nuevoColor);
                }

                guardarTodas(motos);
                return true;
            }
        }

        System.err.println("Moto con ID " + idMoto + " no encontrada.");
        return false;
    }

    private void guardarTodas(List<Moto> motos) {
        try (Writer writer = new FileWriter(RUTA_JSON)) {
            gson.toJson(motos, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void crearDirectoriosSiNoExisten() {
        File archivo = new File(RUTA_JSON);
        archivo.getParentFile().mkdirs();
    }

    public List<Moto> obtenerMotosPorTipoYDisponibles(TipoMoto tipoMoto) {
        List<Moto> motos = cargarTodas();

        List<Moto> filtradas = new ArrayList<>();

        for (Moto moto : motos) {
            if (moto.getTipoMoto() == tipoMoto
                    && moto.getEstado() == EstadoMoto.DISPONIBLE
                    && !moto.getPlaca().startsWith("BSE-")) {
                filtradas.add(moto);
            }
        }

        return filtradas;
    }

    public boolean actualizarMoto(Moto motoActualizada) {
        List<Moto> motos = cargarTodas();

        for (int i = 0; i < motos.size(); i++) {
            if (motos.get(i).getIdMoto() == motoActualizada.getIdMoto()) {
                motos.set(i, motoActualizada);
                guardarTodas(motos);
                return true;
            }
        }
        System.err.println("No se encontró la moto con ID: " + motoActualizada.getIdMoto());
        return false;
    }

    public List<Moto> obtenerMotosDisponibles() {
        List<Moto> motos = cargarTodas();

        List<Moto> filtradas = new ArrayList<>();

        for (Moto moto : motos) {
            if (moto.getEstado() == EstadoMoto.DISPONIBLE
                    && !moto.getPlaca().startsWith("BSE-")) {
                filtradas.add(moto);
            }

        }
        return filtradas;
    }

    public int contarMotosDisponibles() {
        List<Moto> disponibles = obtenerMotosDisponibles();
        return disponibles.size();
    }

    public Moto obtenerMotoBase() {
        List<Moto> motos = cargarTodas();
        for (Moto moto : motos) {
            if (moto.getPlaca().startsWith("BSE-")) {
                return moto;
            }
        }
        return null;
    }

    public Moto obtenerMotoBasePorTipo(TipoMoto tipo) {
        List<Moto> motos = cargarTodas();
        for (Moto moto : motos) {
            if (moto.getPlaca().startsWith("BSE-") && moto.getTipoMoto() == tipo) {
                return moto;
            }
        }
        return null;
    }

}
