package cl.patrones.examen.productos.descuentos;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.List;

@Configuration
public class DescuentoConfig {

    @Bean
    public CalculadoraDescuentos calculadoraDescuentos() {
        return new CalculadoraDescuentos(List.of(
                new DescuentoCondicion(DayOfWeek.MONDAY, "compresor", new BigDecimal("0.06")),
                new DescuentoCondicion(DayOfWeek.TUESDAY, "esmeril", new BigDecimal("0.08")),
                new DescuentoCondicion(DayOfWeek.WEDNESDAY, "taladro", new BigDecimal("0.10"))
        ));
    }
}