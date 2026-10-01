import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
    private final Scanner entrada;
    private Caso caso;

    private Principal() {
        entrada = new Scanner(System.in);
        caso = null;
    }

    public static void main(String[] args) {
        Principal programa = new Principal();
        programa.ejecutar();
    }

    private void ejecutar() {
        try {
            System.out.println("AGENCIA DE DETECTIVES");
            caso = crearCaso();

            boolean salir = false;

            while (!salir) {
                mostrarMenu();

                try {
                    int opcion = leerEntero("Seleccione una opcion: ");

                    switch (opcion) {
                        case 1:
                            caso = crearCaso();
                            System.out.println(
                                    "Nuevo caso creado sin ubicaciones ni pistas.");
                            break;

                        case 2: {
                            int posicion = leerEntero("Posicion (0-4): ");
                            Ubicacion ubicacion = leerUbicacion();

                            caso.registrarUbicacion(posicion, ubicacion);
                            System.out.println("Ubicacion registrada.");
                            break;
                        }

                        case 3:
                            System.out.println(caso.consultarUbicaciones());
                            break;

                        case 4:
                            System.out.println(
                                    caso.consultarUbicacion(
                                            leerEntero("Posicion (0-4): ")));
                            break;

                        case 5: {
                            int posicion = leerEntero("Posicion (0-4): ");

                            caso.consultarUbicacion(posicion);

                            int riesgo = leerEntero("Nuevo riesgo (1-10): ");
                            String estado = leerTexto("Nuevo estado: ");

                            caso.modificarUbicacion(posicion, riesgo, estado);
                            System.out.println("Ubicacion modificada.");
                            break;
                        }

                        case 6:
                            caso.descartarUbicacion(
                                    leerEntero("Posicion (0-4): "));
                            System.out.println("Ubicacion descartada.");
                            break;

                        case 7:
                            caso.registrarPista(leerPista());
                            System.out.println("Pista registrada.");
                            break;

                        case 8:
                            System.out.println(caso.consultarPistas());
                            break;

                        case 9:
                            System.out.println(
                                    caso.buscarPista(leerTexto("Codigo: ")));
                            break;

                        case 10: {
                            String codigoActual = leerTexto("Codigo actual: ");

                            caso.buscarPista(codigoActual);

                            System.out.println(
                                    "Ingrese los nuevos datos de la pista.");

                            caso.modificarPista(codigoActual, leerPista());
                            System.out.println("Pista modificada.");
                            break;
                        }

                        case 11:
                            caso.eliminarPista(leerTexto("Codigo: "));
                            System.out.println("Pista eliminada.");
                            break;

                        case 12:
                            System.out.println(caso.generarReporte());
                            break;

                        case 13:
                            salir = true;
                            System.out.println("Programa finalizado.");
                            break;

                        default:
                            System.out.println(
                                    "Seleccione una opcion entre 1 y 13.");
                    }

                } catch (IllegalArgumentException | IllegalStateException
                         | IndexOutOfBoundsException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }

        } finally {
            entrada.close();
        }
    }

    private void mostrarMenu() {
        System.out.println("\n" + caso);
        System.out.println("1. Nuevo caso");
        System.out.println("2. Registrar ubicacion");
        System.out.println("3. Consultar ubicaciones");
        System.out.println("4. Consultar una ubicacion");
        System.out.println("5. Modificar ubicacion");
        System.out.println("6. Descartar ubicacion");
        System.out.println("7. Registrar pista");
        System.out.println("8. Consultar pistas");
        System.out.println("9. Buscar pista");
        System.out.println("10. Modificar pista");
        System.out.println("11. Eliminar pista");
        System.out.println("12. Mostrar reporte de investigacion");
        System.out.println("13. Salir");
    }

    private Caso crearCaso() {
        String nombre = leerTexto("Nombre del caso: ");
        String codigo = leerTexto("Codigo del caso: ");
        String detective = leerTexto("Detective responsable: ");

        return new Caso(nombre, codigo, detective);
    }

    private Ubicacion leerUbicacion() {
        String codigo = leerTexto("Codigo: ");
        String nombre = leerTexto("Nombre: ");
        String direccion = leerTexto("Direccion o descripcion del lugar: ");
        int riesgo = leerEntero("Nivel de riesgo (1-10): ");
        String estado = leerTexto("Estado: ");

        return new Ubicacion(codigo, nombre, direccion, riesgo, estado);
    }

    private Pista leerPista() {
        String codigo = leerTexto("Codigo: ");
        String descripcion = leerTexto("Descripcion: ");
        String tipo = leerTexto("Tipo de evidencia: ");
        int importancia = leerEntero("Importancia (1-10): ");
        int confiabilidad = leerEntero("Confiabilidad (0-100): ");

        return new Pista(
                codigo, descripcion, tipo, importancia, confiabilidad);
    }

    private String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = entrada.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("Este dato no puede quedar vacio.");
        }
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);

            try {
                return entrada.nextInt();

            } catch (InputMismatchException e) {
                System.out.println(
                        "Entrada incorrecta. Escriba un numero entero.");

            } finally {
                entrada.nextLine();
            }
        }
    }
}