package com.tdea.producto_tdea.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tdea.producto_tdea.entity.Producto;
import com.tdea.producto_tdea.repository.ProductoRepository;

@Service
public class ProductoService {

	private final ProductoRepository productoRepository;

	@Autowired
	public ProductoService(ProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}

	public List<Producto> encontrarTodos() {
		return productoRepository.findAll().stream().toList();
	}

	public Producto encontrarPorId(Long id) {
		return Optional.ofNullable(id)
				.flatMap(productoRepository::findById)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
	}

	public Producto guardar(Producto producto) {
		return Optional.ofNullable(producto)
				.filter(p -> p.getNombre() != null && !p.getNombre().trim().isEmpty())
				.filter(p -> p.getPrecio() != null && p.getPrecio() >= 0)
				.map(productoRepository::save)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Datos inválidos: nombre requerido y precio debe ser >= 0"));
	}

	public void eliminarPorId(Long id) {
		Optional.ofNullable(id)
				.flatMap(productoRepository::findById)
				.map(p -> {
					productoRepository.deleteById(p.getId());
					return true;
				})
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
	}
}

