package cl.patrones.examen.productos.descuentos;

import cl.patrones.examen.productos.domain.Producto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CalculadoraDescuentos {

    // Lista base de descuentos que se aplican a todos los usuarios
    private final List<Descuento> descuentosBase;

    // Constructor que recibe una lista de descuentos base
    public CalculadoraDescuentos(List<Descuento> descuentosBase) {
        this.descuentosBase = descuentosBase;
    }

    /**
     * Calcula el descuento aplicable a un producto, considerando
     * si el usuario autenticado tiene el rol de empleado.
     *
     * @param producto Producto al que se le aplicará el descuento
     * @param authentication Información de autenticación del usuario actual
     * @return El mayor descuento aplicable como un valor Double
     */
    public Double calcularDescuento(Producto producto, Authentication authentication) {
        // Clona la lista base de descuentos para agregar adicionales si corresponde
        List<Descuento> descuentos = new ArrayList<>(descuentosBase);

        // Verifica si el usuario autenticado es un empleado
        boolean esEmpleado = authentication != null &&
                authentication.isAuthenticated() &&
                authentication.getPrincipal() instanceof UserDetails userDetails &&
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .anyMatch(rol -> rol.equals("ROLE_EMPLEADO"));

        // Si es empleado, se añade un descuento adicional del 5%
        if (esEmpleado) {
            descuentos.add(new DescuentoEmpleado(new BigDecimal("0.05")));
        }

        // Aplica todos los descuentos y retorna el valor máximo obtenido
        return descuentos.stream()
                .map(d -> d.aplicarDescuento(producto)) // Aplica cada descuento
                .max(Double::compareTo) // Selecciona el mayor descuento
                .orElse(0.0); // Si no hay descuentos, retorna 0.0
    }
}
