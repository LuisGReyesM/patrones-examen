package cl.patrones.examen.productos.descuentos;

import cl.patrones.examen.productos.domain.Producto;

import java.math.BigDecimal;

/**
 * Implementación de la interfaz Descuento que representa un descuento exclusivo para empleados.
 * Este descuento se aplica directamente como un porcentaje del precio de lista del producto.
 */
public class DescuentoEmpleado implements Descuento {

    private final BigDecimal porcentaje; // Porcentaje de descuento aplicado a empleados

    /**
     * Constructor que define el porcentaje de descuento para empleados.
     *
     * @param porcentaje Porcentaje en formato BigDecimal (ej. 0.05 para 5%)
     */
    public DescuentoEmpleado(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    /**
     * Calcula el monto del descuento aplicable a un producto para un empleado.
     *
     * @param producto Producto sobre el cual se calculará el descuento
     * @return Valor del descuento como Double
     */
    @Override
    public Double aplicarDescuento(Producto producto) {
        return producto.getPrecioLista() * porcentaje.doubleValue();
    }
}
