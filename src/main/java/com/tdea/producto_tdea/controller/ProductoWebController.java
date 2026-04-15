package com.tdea.producto_tdea.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.server.ResponseStatusException;

import com.tdea.producto_tdea.entity.Producto;
import com.tdea.producto_tdea.service.ProductoService;

@Controller
@RequestMapping("/productos")
public class ProductoWebController {

	private final ProductoService productoService;

	public ProductoWebController(ProductoService productoService) {
		this.productoService = productoService;
	}

	@GetMapping({ "", "/", "/lista" })
	public String listar(Model model, @RequestParam(name = "error", required = false) String error) {
		model.addAttribute("productos", productoService.encontrarTodos());
		model.addAttribute("error", error);
		return "productos/lista";
	}

	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		model.addAttribute("producto", new Producto());
		return "productos/formulario";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {
		model.addAttribute("producto", productoService.encontrarPorId(id));
		return "productos/formulario";
	}

	@PostMapping("/guardar")
	public String guardar(@ModelAttribute Producto producto, RedirectAttributes redirectAttributes) {
		try {
			productoService.guardar(producto);
			redirectAttributes.addFlashAttribute("ok", "Producto guardado");
			return "redirect:/productos";
		} catch (ResponseStatusException ex) {
			return "redirect:/productos?error=" + ex.getReason();
		}
	}

	@PostMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		try {
			productoService.eliminarPorId(id);
			redirectAttributes.addFlashAttribute("ok", "Producto eliminado");
			return "redirect:/productos";
		} catch (ResponseStatusException ex) {
			return "redirect:/productos?error=" + Optional.ofNullable(ex.getReason()).orElse("Error");
		}
	}
}

