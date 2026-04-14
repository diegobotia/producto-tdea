package com.tdea.producto_tdea.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tdea.producto_tdea.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
