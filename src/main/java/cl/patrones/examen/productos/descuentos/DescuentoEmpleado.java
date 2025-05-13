package cl.patrones.examen.productos.descuentos;

import cl.patrones.examen.productos.domain.Producto;

import java.math.BigDecimal;

public class DescuentoEmpleado implements Descuento {

    private final BigDecimal porcentaje;

    public DescuentoEmpleado(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    @Override
    public Double aplicarDescuento(Producto producto) {
        return producto.getPrecioLista() * porcentaje.doubleValue();
    }
}