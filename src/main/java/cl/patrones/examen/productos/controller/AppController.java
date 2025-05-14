package cl.patrones.examen.productos.controller;

// Importaciones necesarias para el controlador y su lógica
import cl.patrones.examen.productos.descuentos.CalculadoraDescuentos;
import cl.patrones.examen.productos.domain.Producto;
import cl.patrones.examen.productos.dto.ProductoDTO;
import cl.patrones.examen.productos.service.ProductoService;
import cl.patrones.examen.productos.utils.ProductoUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller // Indica que esta clase es un controlador de Spring MVC
public class AppController {

	private final ProductoService productoService; // Servicio para obtener productos
	private final CalculadoraDescuentos calculadoraDescuentos; // Componente para calcular descuentos

	// Constructor que inyecta dependencias necesarias
	public AppController(ProductoService productoService, CalculadoraDescuentos calculadoraDescuentos) {
		this.productoService = productoService;
		this.calculadoraDescuentos = calculadoraDescuentos;
	}

	@GetMapping("/") // Maneja solicitudes GET a la raíz del sitio
	public String inicio(Model model, Authentication authentication) {
		// Obtiene la lista de productos disponibles desde el servicio
		List<? extends Producto> productos = productoService.getProductos();

		// Convierte los productos a DTOs aplicando descuento y precio final
		List<ProductoDTO> productosDTO = productos.stream().map(p -> {
			// Calcula el descuento para el producto, basado en el usuario autenticado
			Double descuento = calculadoraDescuentos.calcularDescuento(p, authentication);

			// Calcula el precio final con el descuento aplicado
			double precioFinal = ProductoUtils.calcularPrecioFinal(p, descuento);

			// Crea un DTO con los datos del producto y su precio final
			return new ProductoDTO(p, precioFinal);
		}).toList();

		// Agrega la lista de productos (DTOs) al modelo para ser mostrado en la vista
		model.addAttribute("productos", productosDTO);

		// Devuelve el nombre de la vista que se va a renderizar (inicio.html)
		return "inicio";
	}
}
