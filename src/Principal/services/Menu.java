package Principal.services;

import Principal.models.Registro;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static final Scanner ingreso = new Scanner(System.in);
    private static Integer opcion = -1;
    private static final List<Registro> registrosConversiones = new ArrayList<>();
    private static final ConsultaAPI consultaApi = new ConsultaAPI();

    public void mostrarPrincipal() {

        while (opcion != 0) {
            var menu = """
                    \t\t*************************************************
                    \t\t* MENU CONVERSOR DE MODEDAS (ingresa un número) *
                    \t\t*************************************************
                    (1)\t -\t Dolar ==> Peso Argentino
                    (2)\t -\t Peso Argentino ==> Dolar
                    (3)\t -\t Dolar ==> Real Brasileño
                    (4)\t -\t Real Brasileño ==> Dolar
                    (5)\t -\t Dolar ==> Peso Colombiano
                    (6)\t -\t Peso Colombiano ==> Dolar
                    =====================================================
                    (7)\t -\t Otras funciones
                    
                    (0)\t -\t Salir
                    """;
            System.out.println(menu);
            opcion = ingreso.nextInt();
            ingreso.nextLine();
            switch (opcion) {
                case 1:
                    System.out.println(consultaApi.respuestaConversion("USD", "ARS"));
                    registrosConversiones.add(new Registro("USD", "ARS", LocalDateTime.now()));
                    break;
                case 2:
                    System.out.println(consultaApi.respuestaConversion("ARS", "USD"));
                    registrosConversiones.add(new Registro("ARS", "USD", LocalDateTime.now()));
                    break;
                case 3:
                    System.out.println(consultaApi.respuestaConversion("USD", "BRL"));
                    registrosConversiones.add(new Registro("USD", "BRL", LocalDateTime.now()));
                    break;
                case 4:
                    System.out.println(consultaApi.respuestaConversion("BRL", "USD"));
                    registrosConversiones.add(new Registro("BRL", "USD", LocalDateTime.now()));
                    break;
                case 5:
                    System.out.println(consultaApi.respuestaConversion("USD", "COP"));
                    registrosConversiones.add(new Registro("USD", "COP", LocalDateTime.now()));
                    break;
                case 6:
                    System.out.println(consultaApi.respuestaConversion("COP", "USD"));
                    registrosConversiones.add(new Registro("COP", "USD", LocalDateTime.now()));
                    break;
                case 7:
                    otrasFunciones();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        }
    }

    private static void otrasFunciones() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \t\t************************************************************
                    \t\t* OTRAS FUNCIONES CONVERSOR DE MODEDAS (ingresa un número) *
                    \t\t************************************************************
                    (1)\t -\t Historial de conversiones (Completo)
                    (2)\t -\t Historial de conversiones (entre dos fechas)
                    (3)\t -\t Conversion entre dos monedas ingresadas
                    
                    (0)\t -\t Salir
                    """;
            System.out.println(menu);
            opcion = ingreso.nextInt();
            ingreso.nextLine();

            switch (opcion) {
                case 1:
                    verHistorialConversiones();
                    break;
                case 2:
                    verHistorialEntreFechas();
                    break;
                case 3:
                    System.out.println("""
                    \t\t*********************************
                    \t\t* CODIGOS DE MONEDAS SOPORTADOS *
                    \t\t*********************************
                    """);
                    consultaApi.respuestaCodigos().forEach(System.out::println);
                    System.out.println("\nIngrese código de moneda origen");
                    var monedaOrigen = ingreso.nextLine().toUpperCase();
                    System.out.println("Ingrese código de moneda destino");
                    var monedaDestino = ingreso.nextLine().toUpperCase();
                    System.out.println(consultaApi.respuestaConversion(monedaOrigen, monedaDestino));
                    registrosConversiones.add(new Registro(monedaOrigen, monedaDestino, LocalDateTime.now()));
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }

        }
    }

    private static void verHistorialEntreFechas() {
        System.out.println("Historial por fechas");
    }

    private static void verHistorialConversiones() {
        System.out.println("Historial de conversiones");
        registrosConversiones
                .forEach(r -> System.out.println("CONVERSION: Moneda origen - " +
                        r.monedaOrigen() + " | Moneda destino: " +
                        r.monedaDestino() + " | Fecha de registro: " +
                        r.dateTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT))));
    }

}
