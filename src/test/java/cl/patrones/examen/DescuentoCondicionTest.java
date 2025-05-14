package cl.patrones.examen;

import cl.patrones.examen.productos.descuentos.DescuentoCondicion;
import cl.patrones.examen.productos.domain.Categoria;
import cl.patrones.examen.productos.domain.Producto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.DayOfWeek;

public class DescuentoCondicionTest {

    // Test para verificar que el descuento se aplica correctamente cuando el día y la categoría coinciden
    @Test
    void aplicaDescuento_CoincideDiaYCategoria() {
        // Arrange: Configurar el entorno para la prueba
        Producto producto = mock(Producto.class);  // Crear un mock de producto
        Categoria categoria = mock(Categoria.class);  // Crear un mock de categoría

        // Definir el comportamiento del mock (producto y categoría)
        when(producto.getCategoria()).thenReturn(categoria);  // El producto tendrá la categoría mockeada
        when(producto.getPrecioLista()).thenReturn(1000L);  // El precio del producto será 1000
        when(categoria.getNombre()).thenReturn("Compresor de aire");  // El nombre de la categoría será "Compresor de aire"

        // Obtener el día de la semana actual
        DayOfWeek hoy = java.time.LocalDate.now().getDayOfWeek();

        // Crear una instancia del descuento con un 10% para el día actual y la categoría "compresor"
        DescuentoCondicion descuento = new DescuentoCondicion(hoy, "compresor", new BigDecimal("0.10"));

        // Act: Ejecutar el método que aplica el descuento
        Double aplicado = descuento.aplicarDescuento(producto);

        // Assert: Verificar que el descuento aplicado es el 10% del precio del producto
        assertEquals(100.0, aplicado, 0.0); // 10% de 1000 es 100
    }

    // Test para verificar que el descuento no se aplica si el día no coincide con el configurado
    @Test
    void noAplicaDescuento_DiaIncorrecto() {
        // Arrange: Configurar el entorno para la prueba
        Producto producto = mock(Producto.class);  // Crear un mock de producto
        Categoria categoria = mock(Categoria.class);  // Crear un mock de categoría

        // Definir el comportamiento del mock (producto y categoría)
        when(producto.getCategoria()).thenReturn(categoria);  // El producto tendrá la categoría mockeada
        when(categoria.getNombre()).thenReturn("Compresor");  // El nombre de la categoría será "Compresor"
        when(producto.getPrecioLista()).thenReturn(1000L);  // El precio del producto será 1000

        // Definir un día de la semana que no sea el día actual
        DayOfWeek diaNoHoy = DayOfWeek.FRIDAY;  // Asumimos que hoy no es viernes
        while (diaNoHoy.equals(java.time.LocalDate.now().getDayOfWeek())) {
            // Asegurarnos de que el día no coincida con el día de hoy
            diaNoHoy = diaNoHoy.plus(1);
        }

        // Crear una instancia de descuento con un 10% para el día distinto al actual y la categoría "compresor"
        DescuentoCondicion descuento = new DescuentoCondicion(diaNoHoy, "compresor", new BigDecimal("0.10"));

        // Act: Ejecutar el método que aplica el descuento
        Double aplicado = descuento.aplicarDescuento(producto);

        // Assert: Verificar que no se aplica descuento, ya que el día no coincide
        assertEquals(0.0, aplicado, 0.0); // No debe aplicar descuento
    }

    // Test para verificar que el descuento no se aplica si la categoría no coincide con la categoría del producto
    @Test
    void noAplicaDescuento_CategoriaIncorrecta() {
        // Arrange: Configurar el entorno para la prueba
        Producto producto = mock(Producto.class);  // Crear un mock de producto
        Categoria categoria = mock(Categoria.class);  // Crear un mock de categoría

        // Definir el comportamiento del mock (producto y categoría)
        when(producto.getCategoria()).thenReturn(categoria);  // El producto tendrá la categoría mockeada
        when(categoria.getNombre()).thenReturn("Taladro");  // El nombre de la categoría será "Taladro"
        when(producto.getPrecioLista()).thenReturn(1000L);  // El precio del producto será 1000

        // Obtener el día de la semana actual
        DayOfWeek hoy = java.time.LocalDate.now().getDayOfWeek();

        // Crear una instancia de descuento con un 10% para el día actual y la categoría "compresor"
        DescuentoCondicion descuento = new DescuentoCondicion(hoy, "compresor", new BigDecimal("0.10"));

        // Act: Ejecutar el método que aplica el descuento
        Double aplicado = descuento.aplicarDescuento(producto);

        // Assert: Verificar que no se aplica descuento, ya que la categoría no coincide
        assertEquals(0.0, aplicado, 0.0); // No debe aplicar descuento
    }
}
