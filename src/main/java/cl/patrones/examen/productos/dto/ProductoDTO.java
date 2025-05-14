package cl.patrones.examen.productos.dto;

import cl.patrones.examen.productos.domain.Producto;

/**
 * DTO (Data Transfer Object) que representa un producto con su precio final calculado,
 * utilizado para exponer los datos necesarios a la vista sin modificar el modelo original.
 */
public class ProductoDTO {

    private Producto producto;        // Referencia al objeto Producto original
    private Double precioFinal;       // Precio final del producto con descuentos aplicados

    /**
     * Constructor que inicializa el DTO con el producto original y su precio final.
     *
     * @param producto El producto original
     * @param precioFinal Precio con descuentos aplicados
     */
    public ProductoDTO(Producto producto, Double precioFinal) {
        this.producto = producto;
        this.precioFinal = precioFinal;
    }

    // Getters

    public Producto getProducto() {
        return producto;
    }

    public Double getPrecioFinal() {
        return precioFinal;
    }

    /**
     * @return El nombre del producto (extraído del objeto Producto)
     */
    public String getNombre() {
        return producto.getNombre();
    }

    /**
     * @return El precio de lista del producto, convertido a Double si no es nulo
     */
    public Double getPrecioLista() {
        return producto.getPrecioLista() != null ? producto.getPrecioLista().doubleValue() : null;
    }

    /**
     * @return El SKU del producto
     */
    public String getSku() {
        return producto.getSku();
    }

    /**
     * @return La URL o ruta de la imagen del producto
     */
    public String getImagen() {
        return producto.getImagen();
    }
}
