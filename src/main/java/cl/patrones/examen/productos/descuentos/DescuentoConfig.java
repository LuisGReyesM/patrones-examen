package cl.patrones.examen.productos.descuentos;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.List;

/**
 * Clase de configuración de Spring que define los beans relacionados con los descuentos.
 */
@Configuration // Indica que esta clase contiene definiciones de beans para el contenedor de Spring
public class DescuentoConfig {

    /**
     * Define un bean de tipo CalculadoraDescuentos que contiene una lista de
     * descuentos condicionales aplicables según el día y la categoría del producto.
     *
     * @return una instancia de CalculadoraDescuentos con descuentos preconfigurados
     */
    @Bean
    public CalculadoraDescuentos calculadoraDescuentos() {
        return new CalculadoraDescuentos(List.of(
                // Lunes: 6% de descuento en productos de categoría "compresor"
                new DescuentoCondicion(DayOfWeek.MONDAY, "compresor", new BigDecimal("0.06")),
                // Martes: 8% de descuento en productos de categoría "esmeril"
                new DescuentoCondicion(DayOfWeek.TUESDAY, "esmeril", new BigDecimal("0.08")),
                // Miércoles: 10% de descuento en productos de categoría "taladro"
                new DescuentoCondicion(DayOfWeek.WEDNESDAY, "taladro", new BigDecimal("0.10"))
        ));
    }
}
