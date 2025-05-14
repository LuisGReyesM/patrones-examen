package cl.patrones.examen;

import cl.patrones.examen.productos.descuentos.*;
import cl.patrones.examen.productos.domain.Categoria;
import cl.patrones.examen.productos.domain.Producto;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CalculadoraDescuentosTest {

    // Método para crear un producto mockeado con nombre, precio y categoría
    private Producto mockProducto(String nombre, long precio, String categoriaNombre) {
        // Crear un mock de la categoría
        Categoria categoria = mock(Categoria.class);
        when(categoria.getNombre()).thenReturn(categoriaNombre); // Asignar el nombre de la categoría

        // Crear un producto con la categoría mockeada
        Producto producto = mock(Producto.class);
        when(producto.getNombre()).thenReturn(nombre); // Asignar el nombre
        when(producto.getPrecioLista()).thenReturn(precio); // Asignar el precio
        when(producto.getCategoria()).thenReturn(categoria); // Asignar la categoría mockeada
        return producto;
    }

    // Método para crear una autenticación mockeada con un rol específico
    private Authentication mockAuth(String rol) {
        Authentication auth = mock(Authentication.class);
        UserDetails user = mock(UserDetails.class);
        when(auth.isAuthenticated()).thenReturn(true); // Simular que la autenticación es exitosa
        when(auth.getPrincipal()).thenReturn(user); // Asignar un usuario mockeado
        when(user.getAuthorities()).thenReturn(
                (Collection) List.of(new SimpleGrantedAuthority(rol)) // Asignar el rol que se pasa como parámetro
        );

        return auth;
    }

    // Test para verificar que el descuento se aplique correctamente para un empleado
    @Test
    void descuentoEmpleado() {
        // Crear un producto mockeado con nombre "compresor" y precio 1000
        Producto producto = mockProducto("compresor", 1000L, "compresor");

        // Crear autenticación con rol "ROLE_EMPLEADO"
        Authentication auth = mockAuth("ROLE_EMPLEADO");

        // Configurar lista de descuentos para empleados
        List<Descuento> descuentos = List.of(
                new DescuentoCondicion(null, "compresor", new BigDecimal("0.05"))  // 5% descuento para 'compresor' sin importar el día
        );

        // Inicializar la calculadora con la lista de descuentos
        CalculadoraDescuentos calc = new CalculadoraDescuentos(descuentos);

        // Calcular el descuento para el producto
        double descuento = calc.calcularDescuento(producto, auth);

        // Verificar que el descuento aplicado sea el 5% de 1000 (50)
        assertEquals(50.0, descuento, 0.01); // 5% de 1000 es 50
    }

    // Test para verificar que si el precio del producto es 0, el descuento sea 0
    @Test
    void analisisDeLimite_ProductoConPrecioCero() {
        // Crear un producto mockeado con nombre "taladro" y precio 0
        Producto producto = mockProducto("taladro", 0L, "taladro");

        // Crear autenticación con rol "ROLE_EMPLEADO"
        Authentication auth = mockAuth("ROLE_EMPLEADO");

        // Configurar lista de descuentos (por ejemplo, 10% de descuento los miércoles)
        List<Descuento> descuentos = List.of(
                new DescuentoCondicion(DayOfWeek.WEDNESDAY, "taladro", new BigDecimal("0.10"))  // 10% descuento los miércoles para 'taladro'
        );

        // Inicializar la calculadora con la lista de descuentos
        CalculadoraDescuentos calc = new CalculadoraDescuentos(descuentos);

        // Calcular el descuento para el producto
        double descuento = calc.calcularDescuento(producto, auth);

        // Verificar que el descuento aplicado sea 0, ya que el precio es 0
        assertEquals(0.0, descuento, 0.01); // El descuento debe ser 0 ya que el precio es 0
    }

    // Test para verificar que se seleccione el descuento mayor según el día
    @Test
    public void descuentoMayorEmpleadoVsDia() {
        // Define una fecha fija (martes 13 de mayo de 2025)
        LocalDate fixedDate = LocalDate.of(2025, 5, 13); // Martes

        // Usamos MockedStatic para simular los métodos estáticos de LocalDate
        try (MockedStatic<LocalDate> mockedDate = Mockito.mockStatic(LocalDate.class)) {
            // Cuando LocalDate.now() sea llamado, devolver la fecha fija
            mockedDate.when(LocalDate::now).thenReturn(fixedDate);

            // Usamos el mockProducto para crear un producto con la categoría 'esmeril'
            Producto producto = mockProducto("esmeril", 1000L, "esmeril");
            Authentication auth = mockAuth("ROLE_EMPLEADO");

            // Lista de descuentos
            List<Descuento> descuentos = List.of(
                    new DescuentoCondicion(DayOfWeek.TUESDAY, "esmeril", new BigDecimal("0.08")), // 8% descuento en martes para 'esmeril'
                    new DescuentoCondicion(DayOfWeek.MONDAY, "compresor", new BigDecimal("0.06")),  // 6% descuento en lunes para 'esmeril'
                    new DescuentoCondicion(DayOfWeek.WEDNESDAY, "taladro", new BigDecimal("0.10"))  // 6% descuento en lunes para 'esmeril'
            );

            // Calculadora de descuentos
            CalculadoraDescuentos calc = new CalculadoraDescuentos(descuentos);
            double descuento = calc.calcularDescuento(producto, auth);

            // Verificar que el descuento aplicado sea el mayor (8% en martes)
            assertEquals(80.0, descuento, 0.01); // 8% de 1000 es 80
        }
    }

    // Test para verificar que el descuento se aplique correctamente según el día y la categoría
    @Test
    void descuentoPorDiaYCategoria_LunesCompresor() {
        // Fecha fija (lunes 12 de mayo de 2025)
        LocalDate fechaFija = LocalDate.of(2025, 5, 12); // ✅ Mueve esto fuera del when

        try (MockedStatic<LocalDate> mocked = mockStatic(LocalDate.class)) {
            mocked.when(LocalDate::now).thenReturn(fechaFija); // ✅ Esto ya está bien

            Producto producto = mockProducto("compresor", 1000L, "compresor");
            Authentication auth = mockAuth("ROLE_CLIENTE");

            List<Descuento> descuentos = List.of(
                    new DescuentoCondicion(DayOfWeek.MONDAY, "compresor", new BigDecimal("0.06"))
            );

            CalculadoraDescuentos calc = new CalculadoraDescuentos(descuentos);
            double descuento = calc.calcularDescuento(producto, auth);

            assertEquals(60.0, descuento, 0.01); // 6% de 1000
        }
    }

    // Test para verificar que no se aplique ningún descuento cuando no hay coincidencia
    @Test
    void validaDescuentoNegativo() {
        LocalDate fechaFija = LocalDate.of(2025, 5, 15); // Jueves

        try (MockedStatic<LocalDate> mockedDate = mockStatic(LocalDate.class)) {
            mockedDate.when(LocalDate::now).thenReturn(fechaFija);

            Producto producto = mockProducto("otroProducto", 1000L, "sinCategoria");

            // Autenticación con un rol que no tiene descuentos
            Authentication auth = mockAuth("ROLE_INVITADO");

            List<Descuento> descuentos = List.of(
                    new DescuentoCondicion(DayOfWeek.MONDAY, "compresor", new BigDecimal("0.06")),
                    new DescuentoCondicion(DayOfWeek.TUESDAY, "esmeril", new BigDecimal("0.08"))
            );

            CalculadoraDescuentos calc = new CalculadoraDescuentos(descuentos);
            double descuento = calc.calcularDescuento(producto, auth);

            // No debe aplicar ningún descuento
            assertEquals(0.0, descuento, 0.01); // No debe aplicar ningún descuento
        }
    }
}
