package DAO;

import Model.Constants.CapacidadAsiento;
import Model.Constants.MaterialChasis;
import Model.Constants.TipoChasis;
import Model.Constants.TipoMotor;
import Model.Constants.TipoTransmision;
import Model.Constants.TipoVelocidades;
import Model.Entities.Freno;
import Model.Entities.Moto;
import Model.Entities.Llanta;
import Utilidades.LocalDateAdapter;
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

    public MotoDAO() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        crearDirectoriosSiNoExisten();

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
            Type tipoListaVisita = new TypeToken<ArrayList<Moto>>() {
            }.getType();
            List<Moto> visitas = gson.fromJson(reader, tipoListaVisita);
            return visitas != null ? visitas : new ArrayList<>();
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

        if (moto.getIdMoto() == 0) {
            int nuevoId = obtenerProximoIdMoto(motos);
            moto.setIdMoto(nuevoId);
        }

        if (moto.getMotor() != null && moto.getMotor().getIdMotor() == 0) {
            int nuevoIdMotor = obtenerProximoIdMotor(motos);
            moto.getMotor().setIdMotor(nuevoIdMotor);
        }

        if (moto.getFrenoDelantero() != null && moto.getFrenoDelantero().getIdFreno() == 0) {
            int nuevoIdFreno = obtenerProximoIdFreno(motos);
            moto.getFrenoDelantero().setIdFreno(nuevoIdFreno);
        }
        if (moto.getFrenoTrasero() != null && moto.getFrenoTrasero().getIdFreno() == 0) {
            int nuevoIdFreno = obtenerProximoIdFreno(motos);
            moto.getFrenoTrasero().setIdFreno(nuevoIdFreno);
        }

        if (moto.getLlantaDelantera() != null && moto.getLlantaDelantera().getIdLlanta() == 0) {
            int nuevoIdLlanta = obtenerProximoIdLlanta(motos);
            moto.getLlantaDelantera().setIdLlanta(nuevoIdLlanta);
        }
        if (moto.getLlantaTrasera() != null && moto.getLlantaTrasera().getIdLlanta() == 0) {
            int nuevoIdLlanta = obtenerProximoIdLlanta(motos);
            moto.getLlantaTrasera().setIdLlanta(nuevoIdLlanta);
        }

        if (moto.getChasis() != null && moto.getChasis().getIdChasis() == 0) {
            int nuevoIdChasis = obtenerProximoIdChasis(motos);
            moto.getChasis().setIdChasis(nuevoIdChasis);
        }

        if (moto.getAsiento() != null && moto.getAsiento().getIdAsiento() == 0) {
            int nuevoIdAsiento = obtenerProximoIdAsiento(motos);
            moto.getAsiento().setIdAsiento(nuevoIdAsiento);
        }

        if (moto.getTransmision() != null && moto.getTransmision().getIdTransmision() == 0) {
            int nuevoIdTransmision = obtenerProximoIdTransmision(motos);
            moto.getTransmision().setIdTransmision(nuevoIdTransmision);
        }

        motos.add(moto);
        guardarTodas(motos);
        return true;
    }

    private int obtenerProximoIdMoto(List<Moto> motos) {
        return motos.stream()
                .mapToInt(Moto::getIdMoto)
                .max()
                .orElse(0) + 1;
    }

    private int obtenerProximoIdMotor(List<Moto> motos) {
        return motos.stream()
                .filter(m -> m.getMotor() != null)
                .map(Moto::getMotor)
                .mapToInt(m -> m.getIdMotor())
                .max()
                .orElse(0) + 1;
    }

    private int obtenerProximoIdFreno(List<Moto> motos) {
        return motos.stream()
                .flatMap(m -> java.util.stream.Stream.of(m.getFrenoDelantero(), m.getFrenoTrasero()))
                .filter(f -> f != null)
                .mapToInt(Freno::getIdFreno)
                .max()
                .orElse(0) + 1;
    }

    private int obtenerProximoIdLlanta(List<Moto> motos) {
        return motos.stream()
                .flatMap(m -> java.util.stream.Stream.of(m.getLlantaDelantera(), m.getLlantaTrasera()))
                .filter(l -> l != null)
                .mapToInt(Llanta::getIdLlanta)
                .max()
                .orElse(0) + 1;
    }

    private int obtenerProximoIdChasis(List<Moto> motos) {
        return motos.stream()
                .filter(m -> m.getChasis() != null)
                .map(Moto::getChasis)
                .mapToInt(c -> c.getIdChasis())
                .max()
                .orElse(0) + 1;
    }

    private int obtenerProximoIdAsiento(List<Moto> motos) {
        return motos.stream()
                .filter(m -> m.getAsiento() != null)
                .map(Moto::getAsiento)
                .mapToInt(a -> a.getIdAsiento())
                .max()
                .orElse(0) + 1;
    }

    private int obtenerProximoIdTransmision(List<Moto> motos) {
        return motos.stream()
                .filter(m -> m.getTransmision() != null)
                .map(Moto::getTransmision)
                .mapToInt(t -> t.getIdTransmision())
                .max()
                .orElse(0) + 1;
    }

    public boolean eliminarMoto(int idMoto) {
        List<Moto> motos = cargarTodas();

        boolean eliminado = motos.removeIf(m -> m.getIdMoto() == idMoto);

        if (eliminado) {
            guardarTodas(motos);
            return true;
        }

        return false;
    }

    public boolean actualizarMoto(
            int idMoto,
            String nuevaMarca,
            String nuevoModelo,
            LocalDate nuevaFechaLanzamiento,
            Double nuevoPrecio,
            Boolean nuevaParrilla,
            Boolean nuevoMaletero,
            TipoMotor nuevoTipoMotor,
            Integer nuevaCilindrada,
            Integer nuevaPotencia,
            MaterialChasis nuevoMaterialChasis,
            TipoChasis nuevoTipoChasis,
            String nuevoMaterialAsiento,
            CapacidadAsiento nuevaCapacidadAsiento,
            TipoTransmision nuevoTipoTransmision,
            TipoVelocidades nuevasVelocidades
    ) {
        List<Moto> motos = cargarTodas();
        boolean encontrado = false;

        for (Moto moto : motos) {
            if (moto.getIdMoto() == idMoto) {

                encontrado = true;

                if (nuevaMarca != null) {
                    moto.setMarca(nuevaMarca);
                }
                if (nuevoModelo != null) {
                    moto.setModelo(nuevoModelo);
                }
                if (nuevaFechaLanzamiento != null) {
                    moto.setFechaLanzamiento(nuevaFechaLanzamiento);
                }
                if (nuevoPrecio != null) {
                    moto.setPrecio(nuevoPrecio);
                }
                if (nuevaParrilla != null) {
                    moto.setTieneParrilla(nuevaParrilla);
                }
                if (nuevoMaletero != null) {
                    moto.setTieneMaletero(nuevoMaletero);
                }

                if (moto.getMotor() != null) {
                    if (nuevoTipoMotor != null) {
                        moto.getMotor().setTipo(nuevoTipoMotor);
                    }
                    if (nuevaCilindrada != null) {
                        moto.getMotor().setCilindrada(nuevaCilindrada);
                    }
                    if (nuevaPotencia != null) {
                        moto.getMotor().setPotencia(nuevaPotencia);
                    }
                }

                if (moto.getChasis() != null) {
                    if (nuevoMaterialChasis != null) {
                        moto.getChasis().setMaterial(nuevoMaterialChasis);
                    }
                    if (nuevoTipoChasis != null) {
                        moto.getChasis().setTipo(nuevoTipoChasis);
                    }
                }

                if (moto.getAsiento() != null) {
                    if (nuevoMaterialAsiento != null) {
                        moto.getAsiento().setMaterial(nuevoMaterialAsiento);
                    }
                    if (nuevaCapacidadAsiento != null) {
                        moto.getAsiento().setCapacidad(nuevaCapacidadAsiento);
                    }
                }

                if (moto.getTransmision() != null) {
                    if (nuevoTipoTransmision != null) {
                        moto.getTransmision().setTipoTransmision(nuevoTipoTransmision);
                    }
                    if (nuevasVelocidades != null) {
                        moto.getTransmision().setVelocidades(nuevasVelocidades);
                    }
                }

                break;
            }
        }

        if (!encontrado) {
            System.err.println("Moto con id " + idMoto + " no encontrada.");
            return false;
        }

        guardarTodas(motos);
        return true;
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
}
