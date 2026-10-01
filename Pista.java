public class Pista {
    private final String codigo;
    private final String descripcion;
    private final String tipoEvidencia;
    private final int nivelImportancia;
    private final int nivelConfiabilidad;

    public Pista(String codigo, String descripcion, String tipoEvidencia,
                 int nivelImportancia, int nivelConfiabilidad) {
        if (codigo == null || codigo.trim().isEmpty()
                || descripcion == null || descripcion.trim().isEmpty()
                || tipoEvidencia == null || tipoEvidencia.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El codigo, descripcion y tipo de evidencia son obligatorios.");
        }

        if (nivelImportancia < 1 || nivelImportancia > 10) {
            throw new IllegalArgumentException(
                    "La importancia debe estar entre 1 y 10.");
        }

        if (nivelConfiabilidad < 0 || nivelConfiabilidad > 100) {
            throw new IllegalArgumentException(
                    "La confiabilidad debe estar entre 0 y 100.");
        }

        this.codigo = codigo.trim();
        this.descripcion = descripcion.trim();
        this.tipoEvidencia = tipoEvidencia.trim();
        this.nivelImportancia = nivelImportancia;
        this.nivelConfiabilidad = nivelConfiabilidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getNivelImportancia() {
        return nivelImportancia;
    }

    public int getNivelConfiabilidad() {
        return nivelConfiabilidad;
    }

    @Override
    public String toString() {
        return "Codigo: " + codigo
                + " | Descripcion: " + descripcion
                + " | Tipo: " + tipoEvidencia
                + " | Importancia: " + nivelImportancia
                + " | Confiabilidad: " + nivelConfiabilidad;
    }
}