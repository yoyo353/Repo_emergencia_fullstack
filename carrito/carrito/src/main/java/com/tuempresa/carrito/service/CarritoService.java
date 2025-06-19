package com.tuempresa.carrito.service;

import com.tuempresa.carrito.model.Carrito;
import com.tuempresa.carrito.model.ItemCarrito;
import com.tuempresa.carrito.model.ProductoInfo;
import com.tuempresa.carrito.repository.CarritoRepository;
import com.tuempresa.carrito.repository.ItemCarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class CarritoService {
    
    @Autowired
    private CarritoRepository carritoRepository;
    
    @Autowired
    private ItemCarritoRepository itemCarritoRepository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${inventario.service.url:http://localhost:8085}")
    private String inventarioServiceUrl;
    
    // Por simplicidad, usamos un carrito por defecto (usuario 1)
    private static final Long DEFAULT_USER_ID = 1L;
    
    public Carrito obtenerCarrito() {
        return carritoRepository.findByUsuarioId(DEFAULT_USER_ID)
            .orElseGet(() -> crearNuevoCarrito());
    }
    
    private Carrito crearNuevoCarrito() {
        Carrito carrito = new Carrito();
        carrito.setUsuarioId(DEFAULT_USER_ID);
        carrito.setTotal(BigDecimal.ZERO);
        return carritoRepository.save(carrito);
    }
    
    public ItemCarrito agregarProducto(Long productoId, Integer cantidad) {
        // Obtener información del producto desde el microservicio de inventario
        String url = inventarioServiceUrl + "/Api/Inventario/" + productoId;
        ProductoInfo producto = restTemplate.getForObject(url, ProductoInfo.class);
        
        if (producto == null) {
            throw new RuntimeException("Producto no encontrado");
        }
        
        if (producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente");
        }
        
        Carrito carrito = obtenerCarrito();
        
        // Verificar si el producto ya está en el carrito
        Optional<ItemCarrito> itemExistente = carrito.getItems().stream()
            .filter(item -> item.getProductoId().equals(productoId))
            .findFirst();
        
        ItemCarrito item;
        if (itemExistente.isPresent()) {
            // Actualizar cantidad
            item = itemExistente.get();
            item.setCantidad(item.getCantidad() + cantidad);
        } else {
            // Crear nuevo item
            item = new ItemCarrito(productoId, producto.getNombre(), 
                                 producto.getPrecio(), cantidad);
            item.setCarrito(carrito);
            carrito.getItems().add(item);
        }
        
        itemCarritoRepository.save(item);
        carrito.calcularTotal();
        carritoRepository.save(carrito);
        
        return item;
    }
    
    public void eliminarProducto(Long productoId) {
        Carrito carrito = obtenerCarrito();
        
        ItemCarrito item = carrito.getItems().stream()
            .filter(i -> i.getProductoId().equals(productoId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Producto no encontrado en el carrito"));
        
        carrito.getItems().remove(item);
        itemCarritoRepository.delete(item);
        
        carrito.calcularTotal();
        carritoRepository.save(carrito);
    }
    
    public void vaciarCarrito() {
        Carrito carrito = obtenerCarrito();
        carrito.getItems().clear();
        carrito.setTotal(BigDecimal.ZERO);
        carritoRepository.save(carrito);
    }
}