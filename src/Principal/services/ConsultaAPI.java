package Principal.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ConsultaAPI {
    private static final String API_KEY = System.getenv("EXCHANGE_RATE_APIKEY");
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public String respuestaConversion(String monedaOrigen, String monedaDestino){
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

           return String.format("La tasa de conversión de %s a %s es: %.4f",monedaOrigen,monedaDestino,conversionRate);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error al convertir la moneda.";
        }
    }

    public List<?> respuestaCodigos(){
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + API_KEY + "/codes"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonString = response.body();

            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

            return jsonObject.get("supported_codes").getAsJsonArray().asList();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
