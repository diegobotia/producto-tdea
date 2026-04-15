package com.tdea.producto_tdea.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tdea.producto_tdea.entity.Producto;
import com.tdea.producto_tdea.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

	private final ProductoService productoService;

	public ProductoController(ProductoService productoService) {
		this.productoService = productoService;
	}

	@GetMapping
	public List<Producto> listar() {
		return productoService.encontrarTodos();
	}

	@GetMapping("/{id}")
	public Producto obtenerPorId(@PathVariable Long id) {
		return productoService.encontrarPorId(id);
	}

	@PostMapping
	public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
		Optional.ofNullable(producto)
				.map(Producto::getId)
				.ifPresent(id -> {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No envíes id al crear un producto");
				});

		return ResponseEntity.status(HttpStatus.CREATED).body(productoService.guardar(producto));
	}

	@PutMapping("/{id}")
	public Producto actualizar(@PathVariable Long id, @RequestBody Producto producto) {
		productoService.encontrarPorId(id); // fuerza 404 si no existe
		return Optional.ofNullable(producto)
				.map(p -> {
					p.setId(id);
					return p;
				})
				.map(productoService::guardar)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Body requerido"));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		productoService.eliminarPorId(id);
	}
}

