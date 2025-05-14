package cl.patrones.examen.productos.descuentos;

import cl.patrones.examen.productos.domain.Producto;

/**
 * Interfaz que representa una estrategia de descuento.
 * Las clases que implementan esta interfaz deben definir cómo aplicar un descuento a un producto.
 */
public interface Descuento {

    /**
     * Aplica un descuento al producto proporcionado y devuelve el valor del descuento.
     *
     * @param producto El producto al que se le aplicará el descuento
     * @return El valor del descuento como Double
     */
    Double aplicarDescuento(Producto producto);
}
