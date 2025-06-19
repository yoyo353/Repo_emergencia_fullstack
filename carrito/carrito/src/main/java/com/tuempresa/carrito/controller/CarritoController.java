package com.tuempresa.carrito.controller;

import com.tuempresa.carrito.model.Carrito;
import com.tuempresa.carrito.model.ItemCarrito;
import com.tuempresa.carrito.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Api/Carrito")
@CrossOrigin(origins = "*")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    // GET /Api/Carrito - Obtener el carrito completo
    @GetMapping
    public ResponseEntity<Carrito> obtenerCarrito() {
        Carrito carrito = carritoService.obtenerCarrito();
        return ResponseEntity.ok(carrito);
    }

    // POST /Api/Carrito/agregar/{productoId}?cantidad={cantidad}
    @PostMapping("/agregar/{productoId}")
    public ResponseEntity<Map<String, Object>> agregarProducto(
            @PathVariable Long productoId,
            @RequestParam Integer cantidad) {
        
        try {
            ItemCarrito item = carritoService.agregarProducto(productoId, cantidad);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Producto agregado al carrito exitosamente");
            response.put("item", item);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // DELETE /Api/Carrito/eliminar/{productoId}
    @DeleteMapping("/eliminar/{productoId}")
    public ResponseEntity<Map<String, Object>> eliminarProducto(@PathVariable Long productoId) {
        try {
            carritoService.eliminarProducto(productoId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Producto eliminado del carrito exitosamente");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // DELETE /Api/Carrito/vaciar
    @DeleteMapping("/vaciar")
    public ResponseEntity<Map<String, Object>> vaciarCarrito() {
        carritoService.vaciarCarrito();
        
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Carrito vaciado exitosamente");
        
        return ResponseEntity.ok(response);
    }
}