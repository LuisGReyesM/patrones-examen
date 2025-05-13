package cl.patrones.examen.productos.utils;

import cl.patrones.examen.productos.domain.Producto;

public class ProductoUtils {
    public static double calcularPrecioFinal(Producto producto, double descuento) {
        if (producto == null || producto.getPrecioLista() == null) {
            throw new IllegalArgumentException("Producto o precio de lista no pueden ser nulos.");
        }

        if (descuento < 0) {
            throw new IllegalArgumentException("El descuento no puede ser negativo.");
        }

        double precioLista = producto.getPrecioLista();
        if (descuento > precioLista) {
            descuento = precioLista;
        }

        return precioLista - descuento;
    }
}