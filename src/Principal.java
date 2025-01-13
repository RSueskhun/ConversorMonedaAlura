import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        ConsultaTipoDeCambio consulta = new ConsultaTipoDeCambio();
        GeneradorDeArchivo generador = new GeneradorDeArchivo();
        boolean continuar = true;

        //MENU

        System.out.println("=== CONVERSOR DE MONEDA ===");

        while (continuar) {
            System.out.println("**************************************************************");
            System.out.println("**************************************************************");
            System.out.println("\nSELECCIONE UNA OPCION PARA CONTINUAR:\n");
            System.out.println("**************************************************************");
            System.out.println("**************************************************************");
            System.out.println("1. SELECCIONAR MONEDAS PARA REALIZAR LA CONVERSION");
            System.out.println("\n2. SALIR");
            System.out.println("**************************************************************");

            System.out.print("SELECCIONE UNA OPCION PARA CONTINUAR: ");
            String opcionInput = lectura.nextLine();

            try {
                int opcion = Integer.parseInt(opcionInput);

                switch (opcion) {
                    case 1:
                        realizarConversion(lectura, consulta, generador);
                        break;
                    case 2:
                        System.out.println("-----------------------------------------------------------------------------------");
                        System.out.println("LA EJECUCION DE LA CONVERSION FINALIZO -- GRACIAS POR UTILIZAR NUESTROS SERVICIOS--");
                        System.out.println("-----------------------------------------------------------------------------------");
                        continuar = false;
                        break;
                    default:
                        System.out.println("---------------------------------------------");
                        System.out.println("OPCION NO VALIDA POR FAVOR INTENTELO DE NUEVO");
                        System.out.println("---------------------------------------------");
                }
            } catch (NumberFormatException e) {
                System.out.println("SELECCION INVALIDA --ELIJA SOLO UNA DE LAS OPCIONES DISPONIBLES");
            }
        }

        lectura.close();
    }

    private static void realizarConversion(Scanner lectura, ConsultaTipoDeCambio consulta, GeneradorDeArchivo generador) {
        try {
            // ORIGEN
            System.out.println("\nMONEDAS DISPONIBLES:");
            System.out.println("__________________________________________________");
            System.out.println(Moneda.menuMonedas());
            System.out.println("__________________________________________________");
            System.out.print("\nINGRESE LA OPCION CORRESPONDIENTE A SU SELECCION: ");
            int origenInput = Integer.parseInt(lectura.nextLine());
            Moneda origen = Moneda.fromOpcion(origenInput);

            // DESTINO
            System.out.println("\nSELECCIONE LA MONEDA DE DESTINO PARA SU CONVERSION:");
            System.out.println("__________________________________________________");
            System.out.println(Moneda.menuMonedas());
            System.out.println("__________________________________________________");
            System.out.print("\nINGRESE LA OPCION CORRESPONDIENTE A SU SELECCION: ");
            int destinoInput = Integer.parseInt(lectura.nextLine());
            Moneda destino = Moneda.fromOpcion(destinoInput);

            // CANTIDAD
            System.out.println("__________________________________________________");
            System.out.print("\nINGRESE EL MONTO A CONVERTIR (" + origen.getCodigo() + "): ");
            double cantidad = Double.parseDouble(lectura.nextLine());

            // CONSUMO API
            CambioAPI cambio = consulta.buscarTipoDeCambio(origen.getCodigo(), destino.getCodigo());

            // EJECUCION DE LA CONVERSION
            double resultado = cambio.getConversionRate() * cantidad;

            // FORMATEAR RESULTADOS
            DecimalFormat df = new DecimalFormat("#.##");

            // RESULTADO
            System.out.println("__________________________________________________");
            System.out.printf("\n%.2f %s --- EQUIVALE A --- %.2f %s.\n", cantidad, origen.getCodigo(), resultado, destino.getCodigo());

            // ARCHIVO JSON
            generador.guardarJson(cambio, origen.getCodigo(), destino.getCodigo(), cantidad, resultado);
            System.out.println("ARCHIVO - JSON -  GENERADO CON LOS DETALLES DE SU CONVERSION.\n");

        } catch (NumberFormatException e) {
            System.out.println("INGRESO INVALIDO ---EL CONVERSOR SOLO ACEPTA NUMEROS SIN ESPACIOS NI COMAS---");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("EROR AL GENERAR EL ARCHIVO - JSON -" + e.getMessage());
        } catch (Exception e) {
            System.out.println("---ERROR INESPERADO---" + e.getMessage());
        }
    }
}
