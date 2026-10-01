import java.util.ArrayList;

public class Caso {
    private final String nombre;
    private final String codigo;
    private final String detective;
    private final Ubicacion[] ubicaciones;
    private final ArrayList<Pista> pistas;

    public Caso(String nombre, String codigo, String detective) {
        if (nombre == null || nombre.trim().isEmpty()
                || codigo == null || codigo.trim().isEmpty()
                || detective == null || detective.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Todos los datos del caso son obligatorios.");
        }

        this.nombre = nombre.trim();
        this.codigo = codigo.trim();
        this.detective = detective.trim();

        ubicaciones = new Ubicacion[5];
        pistas = new ArrayList<Pista>();
    }

    private void validarPosicion(int posicion) {
        if (posicion < 0 || posicion >= ubicaciones.length) {
            throw new IndexOutOfBoundsException(
                    "La posicion debe estar entre 0 y 4.");
        }
    }

    public void registrarUbicacion(int posicion, Ubicacion ubicacion) {
        validarPosicion(posicion);

        if (ubicaciones[posicion] != null) {
            throw new IllegalStateException(
                    "La posicion ya esta ocupada.");
        }

        if (ubicacion == null) {
            throw new IllegalArgumentException(
                    "La ubicacion no puede ser null.");
        }

        ubicaciones[posicion] = ubicacion;
    }

    public Ubicacion consultarUbicacion(int posicion) {
        validarPosicion(posicion);

        if (ubicaciones[posicion] == null) {
            throw new IllegalStateException(
                    "No hay una ubicacion en esa posicion.");
        }

        return ubicaciones[posicion];
    }

    public String consultarUbicaciones() {
        String resultado = "";

        for (int i = 0; i < ubicaciones.length; i++) {
            if (ubicaciones[i] != null) {
                resultado += "Posicion " + i + ": "
                        + ubicaciones[i] + "\n";
            }
        }

        return resultado.isEmpty()
                ? "No hay ubicaciones registradas."
                : resultado;
    }

    public void modificarUbicacion(int posicion, int nivelRiesgo,
                                   String estado) {
        consultarUbicacion(posicion).actualizar(nivelRiesgo, estado);
    }

    public void descartarUbicacion(int posicion) {
        consultarUbicacion(posicion);
        ubicaciones[posicion] = null;
    }

    private int buscarIndicePista(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El codigo de la pista es obligatorio.");
        }

        for (int i = 0; i < pistas.size(); i++) {
            if (pistas.get(i).getCodigo()
                    .equalsIgnoreCase(codigo.trim())) {
                return i;
            }
        }

        return -1;
    }

    public void registrarPista(Pista pista) {
        if (pista == null) {
            throw new IllegalArgumentException(
                    "La pista no puede ser null.");
        }

        if (buscarIndicePista(pista.getCodigo()) != -1) {
            throw new IllegalArgumentException(
                    "Ya existe una pista con ese codigo.");
        }

        pistas.add(pista);
    }

    public Pista buscarPista(String codigo) {
        int indice = buscarIndicePista(codigo);

        if (indice == -1) {
            throw new IllegalArgumentException(
                    "No se encontro la pista.");
        }

        return pistas.get(indice);
    }

    public String consultarPistas() {
        if (pistas.isEmpty()) {
            return "No hay pistas registradas.";
        }

        String resultado = "";

        for (Pista pista : pistas) {
            resultado += pista + "\n";
        }

        return resultado;
    }

    public void modificarPista(String codigoActual, Pista nuevaPista) {
        int indice = buscarIndicePista(codigoActual);

        if (indice == -1) {
            throw new IllegalArgumentException(
                    "No se encontro la pista.");
        }

        if (nuevaPista == null) {
            throw new IllegalArgumentException(
                    "La nueva pista no puede ser null.");
        }

        int repetido = buscarIndicePista(nuevaPista.getCodigo());

        if (repetido != -1 && repetido != indice) {
            throw new IllegalArgumentException(
                    "Otra pista ya utiliza ese codigo.");
        }

        pistas.set(indice, nuevaPista);
    }

    public void eliminarPista(String codigo) {
        int indice = buscarIndicePista(codigo);

        if (indice == -1) {
            throw new IllegalArgumentException(
                    "No se encontro la pista.");
        }

        pistas.remove(indice);
    }

    public String generarReporte() {
        int cantidadUbicaciones = 0;
        Ubicacion mayorRiesgo = null;

        for (Ubicacion ubicacion : ubicaciones) {
            if (ubicacion != null) {
                cantidadUbicaciones++;

                if (mayorRiesgo == null
                        || ubicacion.getNivelRiesgo()
                        > mayorRiesgo.getNivelRiesgo()) {
                    mayorRiesgo = ubicacion;
                }
            }
        }

        String reporte = toString()
                + "\nUbicaciones registradas: " + cantidadUbicaciones
                + "\nEspacios disponibles: "
                + (ubicaciones.length - cantidadUbicaciones)
                + "\nUbicacion con mayor riesgo: "
                + (mayorRiesgo == null
                    ? "No hay ubicaciones."
                    : mayorRiesgo.toString())
                + "\nPistas registradas: " + pistas.size();

        if (pistas.isEmpty()) {
            return reporte
                    + "\nPista con mayor importancia: No hay pistas."
                    + "\nPista con mayor confiabilidad: No hay pistas."
                    + "\nPromedio de importancia: No disponible.";
        }

        Pista mayorImportancia = pistas.get(0);
        Pista mayorConfiabilidad = pistas.get(0);
        double sumaImportancia = 0;

        for (Pista pista : pistas) {
            sumaImportancia += pista.getNivelImportancia();

            if (pista.getNivelImportancia()
                    > mayorImportancia.getNivelImportancia()) {
                mayorImportancia = pista;
            }

            if (pista.getNivelConfiabilidad()
                    > mayorConfiabilidad.getNivelConfiabilidad()) {
                mayorConfiabilidad = pista;
            }
        }

        return reporte
                + "\nPista con mayor importancia: " + mayorImportancia
                + "\nPista con mayor confiabilidad: " + mayorConfiabilidad
                + "\nPromedio de importancia: "
                + String.format("%.2f", sumaImportancia / pistas.size());
    }

    @Override
    public String toString() {
        return "Caso: " + nombre
                + " | Codigo: " + codigo
                + " | Detective: " + detective;
    }
}