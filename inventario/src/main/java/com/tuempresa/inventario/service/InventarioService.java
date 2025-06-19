package com.tuempresa.inventario.service;

import com.tuempresa.inventario.model.Producto;
import com.tuempresa.inventario.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InventarioService {
    
    @Autowired
    private InventarioRepository inventarioRepository;
    
    public List<Producto> obtenerTodosLosProductos() {
        return inventarioRepository.findByActivoTrue();
    }
    
    public Producto obtenerProductoPorId(Long id) {
        return inventarioRepository.findById(id).orElse(null);
    }
    
    public List<Producto> buscarPorCategoria(String categoria) {
        return inventarioRepository.findByCategoriaContainingIgnoreCase(categoria);
    }
    
    public List<Producto> buscarPorNombre(String nombre) {
        return inventarioRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    public Producto crearProducto(Producto producto) {
        // Validar si el SKU ya existe
        if (producto.getSku() != null && inventarioRepository.existsBySku(producto.getSku())) {
            throw new RuntimeException("El SKU ya existe");
        }
        
        return inventarioRepository.save(producto);
    }
    
    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        Producto producto = inventarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        // Actualizar campos
        producto.setNombre(productoActualizado.getNombre());
        producto.setDescripcion(productoActualizado.getDescripcion());
        producto.setPrecio(productoActualizado.getPrecio());
        producto.setStock(productoActualizado.getStock());
        producto.setCategoria(productoActualizado.getCategoria());
        producto.setSku(productoActualizado.getSku());
        producto.setImagenUrl(productoActualizado.getImagenUrl());
        producto.setActivo(productoActualizado.getActivo());
        
        return inventarioRepository.save(producto);
    }
    
    public Producto actualizarStock(Long id, Integer cantidad, String operacion) {
        Producto producto = inventarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        if ("add".equalsIgnoreCase(operacion)) {
            producto.setStock(producto.getStock() + cantidad);
        } else if ("subtract".equalsIgnoreCase(operacion)) {
            if (producto.getStock() < cantidad) {
                throw new RuntimeException("Stock insuficiente");
            }
            producto.setStock(producto.getStock() - cantidad);
        } else if ("set".equalsIgnoreCase(operacion)) {
            producto.setStock(cantidad);
        } else {
            throw new RuntimeException("Operación no válida. Use: add, subtract o set");
        }
        
        return inventarioRepository.save(producto);
    }
    
    public void eliminarProducto(Long id) {
        Producto producto = inventarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        // En lugar de eliminar físicamente, marcamos como inactivo
        producto.setActivo(false);
        inventarioRepository.save(producto);
    }
    
    public boolean verificarStock(Long id, Integer cantidadRequerida) {
        Producto producto = inventarioRepository.findById(id).orElse(null);
        return producto != null && producto.getStock() >= cantidadRequerida;
    }
}