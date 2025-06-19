package com.tuempresa.inventario.repository;

import com.tuempresa.inventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Producto, Long> {
    
    List<Producto> findByActivoTrue();
    
    List<Producto> findByCategoriaContainingIgnoreCase(String categoria);
    
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    boolean existsBySku(String sku);
    
    List<Producto> findByStockLessThan(Integer stock);
    
    List<Producto> findByCategoriaAndActivoTrue(String categoria);
}