package granjaautomatizada.utilitario;

import java.time.LocalDateTime;
import java.util.Random;

// Clase utilitaria con métodos de apoyo
public class Util {

    private static Random random = new Random();

    // Genera un ID con prefijo y número
    // Ejemplo: PARCELA_1, ASPERSOR_3, SENSOR_5
    public static String generarId(String prefijo, int numero) {
        return prefijo + "_" + numero;
    }

    // Genera una lectura de humedad progresiva
    // No salta bruscamente de 10 a 90
    public static int generarHumedadProgresiva(int humedadActual) {
        int variacion = random.nextInt(11) - 5; // -5 a +5
        int nuevaHumedad = humedadActual + variacion;

        if (nuevaHumedad < 0) nuevaHumedad = 0;
        if (nuevaHumedad > 100) nuevaHumedad = 100;

        return nuevaHumedad;
    }

    // Obtiene fecha y hora actual
    public static LocalDateTime obtenerFechaHoraActual() {
        return LocalDateTime.now();
    }
}
