package cl.patrones.examen.productos.utils;

import cl.patrones.examen.productos.domain.Producto;

/**
 * Clase utilitaria que contiene métodos relacionados con operaciones sobre productos.
 */
public class ProductoUtils {

    /**
     * Calcula el precio final de un producto aplicando un descuento dado.
     *
     * @param producto El producto al que se le aplicará el descuento
     * @param descuento El valor monetario del descuento a aplicar
     * @return El precio final después de aplicar el descuento
     * @throws IllegalArgumentException si el producto o su precio de lista es nulo, o si el descuento es negativo
     */
    public static double calcularPrecioFinal(Producto producto, double descuento) {
        // Validaciones básicas de entrada
        if (producto == null || producto.getPrecioLista() == null) {
            throw new IllegalArgumentException("Producto o precio de lista no pueden ser nulos.");
        }

        if (descuento < 0) {
            throw new IllegalArgumentException("El descuento no puede ser negativo.");
        }

        double precioLista = producto.getPrecioLista();

        // Asegura que el descuento no exceda el precio original
        if (descuento > precioLista) {
            descuento = precioLista;
        }

        // Retorna el precio final después del descuento
        return precioLista - descuento;
    }
}
