package cl.patrones.examen.productos.descuentos;

import cl.patrones.examen.productos.domain.Producto;
import cl.patrones.examen.productos.domain.Categoria;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class DescuentoCondicion implements Descuento {

    private final DayOfWeek dia;
    private final String categoria;
    private final BigDecimal porcentaje;

    public DescuentoCondicion(DayOfWeek dia, String categoria, BigDecimal porcentaje) {
        this.dia = dia;
        this.categoria = categoria.toLowerCase();
        this.porcentaje = porcentaje;
    }

    @Override
    public Double aplicarDescuento(Producto producto) {
        DayOfWeek hoy = LocalDate.now().getDayOfWeek();
        if (!hoy.equals(dia)) return 0.0;

        Object categoriaObj = producto.getCategoria();
        if (categoriaObj instanceof Categoria) {
            Categoria cat = (Categoria) categoriaObj;
            String nombreCategoria = cat.getNombre();
            if (nombreCategoria != null && nombreCategoria.toLowerCase().contains(this.categoria)) {
                return producto.getPrecioLista() * porcentaje.doubleValue();
            }
        } else {
            System.err.println("Producto no tiene una categoría válida.");
        }

        return 0.0;
    }
}
