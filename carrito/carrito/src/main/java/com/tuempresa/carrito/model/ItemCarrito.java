package com.tuempresa.carrito.model;

import javax.persistence.*;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "item_carrito")
public class ItemCarrito {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "carrito_id")
    @JsonIgnore
    private Carrito carrito;
    
    @Column(name = "producto_id")
    private Long productoId;
    
    @Column(name = "nombre_producto")
    private String nombreProducto;
    
    @Column(name = "precio")
    private BigDecimal precio;
    
    @Column(name = "cantidad")
    private Integer cantidad;
    
    @Column(name = "subtotal")
    private BigDecimal subtotal;
    
    // Constructor
    public ItemCarrito() {}
    
    public ItemCarrito(Long productoId, String nombreProducto, BigDecimal precio, Integer cantidad) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.cantidad = cantidad;
        this.subtotal = precio.multiply(BigDecimal.valueOf(cantidad));
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Carrito getCarrito() {
        return carrito;
    }
    
    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }
    
    public Long getProductoId() {
        return productoId;
    }
    
    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }
    
    public String getNombreProducto() {
        return nombreProducto;
    }
    
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
    
    public BigDecimal getPrecio() {
        return precio;
    }
    
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }
    
    public BigDecimal getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
    
    public void calcularSubtotal() {
        this.subtotal = this.precio.multiply(BigDecimal.valueOf(this.cantidad));
    }
}