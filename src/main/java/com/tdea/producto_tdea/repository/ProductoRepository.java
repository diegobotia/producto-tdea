package com.tdea.producto_tdea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tdea.producto_tdea.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
