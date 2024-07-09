package Principal;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


public class ConversorDeMonedas {

    private static final String API_KEY = "26fdb4f19a7394dd68207769"; // Reemplaza con tu API Key
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";
    private static final Scanner ingreso = new Scanner(System.in);

    public static void main(String[] args) {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \t\t********************************************
                    \t\t* MENU DE CONVERSIONES (ingresa un número) *
                    \t\t********************************************
                    (1)\t -\t Dolar ==> Peso Argentino
                    (2)\t -\t Peso Argentino ==> Dolar
                    (3)\t -\t Dolar ==> Real Brasileño
                    (4)\t -\t Real Brasileño ==> Dolar
                    (5)\t -\t Dolar ==> Peso Colombiano
                    (6)\t -\t Peso Colombiano ==> Dolar
                    \n
                    (0)\t -\t Salir
                    """;
            System.out.println(menu);
            opcion = ingreso.nextInt();
            ingreso.nextLine();

            switch (opcion) {
                case 1:
                    Conversor("USD", "ARS");
                    break;
                case 2:
                    Conversor("ARS", "USD");
                    break;
                case 3:
                    Conversor("USD", "BRL");
                    break;
                case 4:
                    Conversor("BRL", "USD");
                    break;
                case 5:
                    Conversor("USD", "COP");
                    break;
                case 6:
                    Conversor("COP", "USD");
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }



    }

    private static void Conversor(String monedaOrigen, String monedaDestino) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + API_KEY + "/pair/" + monedaOrigen + "/" + monedaDestino))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonString = response.body();

            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
            double conversionRate = jsonObject.get("conversion_rate").getAsDouble();

            System.out.printf("La tasa de conversión de %s a %s es: %.2f%n", monedaOrigen, monedaDestino, conversionRate);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al convertir la moneda.");
        }
    }


}
