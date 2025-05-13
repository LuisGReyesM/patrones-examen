package cl.patrones.examen.productos.descuentos;

import cl.patrones.examen.productos.domain.Producto;

public interface Descuento {
    Double aplicarDescuento(Producto producto);
}