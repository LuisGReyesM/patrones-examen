package cl.patrones.examen.productos.dto;

import cl.patrones.examen.productos.domain.Producto;

public class ProductoDTO {
    private Producto producto;
    private Double precioFinal;

    public ProductoDTO(Producto producto, Double precioFinal) {
        this.producto = producto;
        this.precioFinal = precioFinal;
    }

    public Producto getProducto() {
        return producto;
    }

    public Double getPrecioFinal() {
        return precioFinal;
    }

    public String getNombre() {
        return producto.getNombre();
    }

    public Double getPrecioLista() {
        return producto.getPrecioLista() != null ? producto.getPrecioLista().doubleValue() : null;
    }

    public String getSku() {
        return producto.getSku();
    }

    public String getImagen() {
        return producto.getImagen();
    }
}
