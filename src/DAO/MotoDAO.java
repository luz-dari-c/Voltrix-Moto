package DAO;

import Model.Constants.*;
import Model.Entities.Moto;
import Model.Entities.PartesMoto;
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
            System.err.println("⚠️ Ya existe una moto con la placa: " + moto.getPlaca());
            return false;
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

        motos.add(moto);
        guardarTodas(motos);
        return true;

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
            LocalDate nuevaFechaIngreso,
            Double nuevoPrecio,
            Boolean nuevaParrilla,
            Boolean nuevoMaletero,
            TipoMotor nuevoTipoMotor,
            Integer nuevaCilindrada,
            Integer nuevaPotencia,
            MaterialChasis nuevoMaterialChasis,
            TipoChasis nuevoTipoChasis,
            MaterialAsiento nuevoMaterialAsiento,
            CapacidadAsiento nuevaCapacidadAsiento,
            TipoTransmision nuevoTipoTransmision,
            TipoVelocidades nuevasVelocidades,
            EstadoMoto nuevoEstado
    ) {
        List<Moto> motos = cargarTodas();
        boolean encontrado = false;

        for (Moto moto : motos) {
            if (moto.getIdMoto() == idMoto) {
                encontrado = true;

                if (nuevaMarca != null && !nuevaMarca.trim().isEmpty()) {
                    moto.setMarca(nuevaMarca);
                }

                if (nuevoModelo != null && !nuevoModelo.trim().isEmpty()) {
                    moto.setModelo(nuevoModelo);
                }

                if (nuevaFechaIngreso != null) {
                    moto.setFechaIngreso(nuevaFechaIngreso);
                }

                if (nuevoPrecio != null && nuevoPrecio > 0) {
                    moto.setPrecio(nuevoPrecio);
                }

                if (nuevaParrilla != null) {
                    moto.setTieneParrilla(nuevaParrilla);
                }

                if (nuevoMaletero != null) {
                    moto.setTieneMaletero(nuevoMaletero);
                }
                if (nuevoEstado != null) {
                    moto.setEstado(nuevoEstado);
                }

                PartesMoto partes = moto.getPartesMoto();
                if (partes != null) {

                    if (partes.getMotor() != null) {
                        if (nuevoTipoMotor != null) {
                            partes.getMotor().setTipo(nuevoTipoMotor);
                        }

                        if (nuevaCilindrada != null && nuevaCilindrada > 0) {
                            partes.getMotor().setCilindrada(nuevaCilindrada);
                        }

                        if (nuevaPotencia != null && nuevaPotencia > 0) {
                            partes.getMotor().setPotencia(nuevaPotencia);
                        }
                    }

                    if (partes.getChasis() != null) {
                        if (nuevoMaterialChasis != null) {
                            partes.getChasis().setMaterial(nuevoMaterialChasis);
                        }

                        if (nuevoTipoChasis != null) {
                            partes.getChasis().setTipo(nuevoTipoChasis);
                        }
                    }

                    if (partes.getAsiento() != null) {
                        if (nuevoMaterialAsiento != null) {
                            partes.getAsiento().setMaterial(nuevoMaterialAsiento);
                        }

                        if (nuevaCapacidadAsiento != null) {
                            partes.getAsiento().setCapacidad(nuevaCapacidadAsiento);
                        }
                    }

                    if (partes.getTransmision() != null) {
                        if (nuevoTipoTransmision != null) {
                            partes.getTransmision().setTipoTransmision(nuevoTipoTransmision);
                        }

                        if (nuevasVelocidades != null) {
                            partes.getTransmision().setVelocidades(nuevasVelocidades);
                        }
                    }
                }

                break;
            }
        }

        if (!encontrado) {
            System.err.println("Moto con ID " + idMoto + " no encontrada.");
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
