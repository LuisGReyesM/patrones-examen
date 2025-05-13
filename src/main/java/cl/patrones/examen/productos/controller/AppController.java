package cl.patrones.examen.productos.controller;

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

@Controller
public class AppController {
	private final ProductoService productoService;
	private final CalculadoraDescuentos calculadoraDescuentos;

	public AppController(ProductoService productoService, CalculadoraDescuentos calculadoraDescuentos) {
		this.productoService = productoService;
		this.calculadoraDescuentos = calculadoraDescuentos;
	}

	@GetMapping("/")
	public String inicio(Model model, Authentication authentication) {
		List<? extends Producto> productos = productoService.getProductos();

		List<ProductoDTO> productosDTO = productos.stream().map(p -> {
			Double descuento = calculadoraDescuentos.calcularDescuento(p, authentication);
			double precioFinal = ProductoUtils.calcularPrecioFinal(p, descuento);
			return new ProductoDTO(p, precioFinal);
		}).toList();

		model.addAttribute("productos", productosDTO);
		return "inicio";
	}
}
