package cl.patrones.examen.productos.descuentos;

import cl.patrones.examen.productos.domain.Producto;
import cl.patrones.examen.productos.domain.Categoria;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Implementación de la interfaz Descuento que aplica un descuento condicionado
 * al día de la semana y a una coincidencia de categoría del producto.
 */
public class DescuentoCondicion implements Descuento {

    private final DayOfWeek dia;       // Día de la semana en que se aplica el descuento
    private final String categoria;    // Nombre (o parte del nombre) de la categoría
    private final BigDecimal porcentaje; // Porcentaje de descuento a aplicar

    /**
     * Constructor que define las condiciones del descuento.
     *
     * @param dia Día específico de la semana en que se activa el descuento
     * @param categoria Nombre o fragmento de la categoría a la que se aplica el descuento
     * @param porcentaje Porcentaje de descuento (ej: 0.10 para 10%)
     */
    public DescuentoCondicion(DayOfWeek dia, String categoria, BigDecimal porcentaje) {
        this.dia = dia;
        this.categoria = categoria.toLowerCase(); // Normaliza para comparación sin distinción de mayúsculas
        this.porcentaje = porcentaje;
    }

    /**
     * Aplica el descuento si se cumplen las condiciones de día y categoría.
     *
     * @param producto Producto al que se evalúa el descuento
     * @return Valor monetario del descuento, o 0.0 si no aplica
     */
    @Override
    public Double aplicarDescuento(Producto producto) {
        DayOfWeek hoy = LocalDate.now().getDayOfWeek();

        // Verifica si hoy es el día en que el descuento debe aplicarse
        if (!hoy.equals(dia)) return 0.0;

        // Verifica si la categoría del producto es válida y coincide con la condición
        Object categoriaObj = producto.getCategoria();
        if (categoriaObj instanceof Categoria) {
            Categoria cat = (Categoria) categoriaObj;
            String nombreCategoria = cat.getNombre();
            if (nombreCategoria != null && nombreCategoria.toLowerCase().contains(this.categoria)) {
                // Aplica el descuento sobre el precio de lista
                return producto.getPrecioLista() * porcentaje.doubleValue();
            }
        } else {
            // Manejo de error si el producto no tiene una categoría reconocida
            System.err.println("Producto no tiene una categoría válida.");
        }

        // No se cumplen las condiciones para aplicar el descuento
        return 0.0;
    }
}
