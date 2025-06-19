package com.tuempresa.inventario.controller;

import com.tuempresa.inventario.model.Producto;
import com.tuempresa.inventario.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/Api/Inventario")
@CrossOrigin(origins = "*")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    // GET /Api/Inventario - Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodosLosProductos(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String nombre) {
        
        List<Producto> productos;
        
        if (categoria != null && !categoria.isEmpty()) {
            productos = inventarioService.buscarPorCategoria(categoria);
        } else if (nombre != null && !nombre.isEmpty()) {
            productos = inventarioService.buscarPorNombre(nombre);
        } else {
            productos = inventarioService.obtenerTodosLosProductos();
        }
        
        return ResponseEntity.ok(productos);
    }

    // GET /Api/Inventario/{id} - Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Producto producto = inventarioService.obtenerProductoPorId(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /Api/Inventario - Crear nuevo producto
    @PostMapping
    public ResponseEntity<Map<String, Object>> crearProducto(@RequestBody Producto producto) {
        try {
            Producto nuevoProducto = inventarioService.crearProducto(producto);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Producto creado exitosamente");
            response.put("producto", nuevoProducto);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // PUT /Api/Inventario/{id} - Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarProducto(
            @PathVariable Long id, 
            @RequestBody Producto producto) {
        try {
            Producto productoActualizado = inventarioService.actualizarProducto(id, producto);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Producto actualizado exitosamente");
            response.put("producto", productoActualizado);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.notFound().body(errorResponse);
        }
    }

    // PATCH /Api/Inventario/{id}/stock - Actualizar stock
    @PatchMapping("/{id}/stock")
    public ResponseEntity<Map<String, Object>> actualizarStock(
            @PathVariable Long id,
            @RequestParam Integer cantidad,
            @RequestParam(defaultValue = "add") String operacion) {
        try {
            Producto producto = inventarioService.actualizarStock(id, cantidad, operacion);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Stock actualizado exitosamente");
            response.put("producto", producto);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // DELETE /Api/Inventario/{id} - Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarProducto(@PathVariable Long id) {
        try {
            inventarioService.eliminarProducto(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Producto eliminado exitosamente");
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.notFound().body(errorResponse);
        }
    }
}