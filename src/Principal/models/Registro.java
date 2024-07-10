package Principal.models;

import java.time.LocalDateTime;

public record Registro(
        String monedaOrigen,
        String monedaDestino,
        LocalDateTime dateTime) {


}
