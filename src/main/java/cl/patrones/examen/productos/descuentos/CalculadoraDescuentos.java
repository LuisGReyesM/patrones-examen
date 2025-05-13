package cl.patrones.examen.productos.descuentos;

import cl.patrones.examen.productos.domain.Producto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CalculadoraDescuentos {

    private final List<Descuento> descuentosBase;

    public CalculadoraDescuentos(List<Descuento> descuentosBase) {
        this.descuentosBase = descuentosBase;
    }

    public Double calcularDescuento(Producto producto, Authentication authentication) {
        List<Descuento> descuentos = new ArrayList<>(descuentosBase);

        boolean esEmpleado = authentication != null &&
                authentication.isAuthenticated() &&
                authentication.getPrincipal() instanceof UserDetails userDetails &&
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .anyMatch(rol -> rol.equals("ROLE_EMPLEADO"));

        if (esEmpleado) {
            descuentos.add(new DescuentoEmpleado(new BigDecimal("0.05")));
        }

        return descuentos.stream()
                .map(d -> d.aplicarDescuento(producto))
                .max(Double::compareTo)
                .orElse(0.0);
    }
}