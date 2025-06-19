package com.tuempresa.inventario.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.tuempresa.inventario.repository.InventarioRepository;

@Configuration
public class DatabaseInitializer {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);
    
    @Autowired
    private InventarioRepository inventarioRepository;
    
    @Bean
    CommandLineRunner init() {
        return args -> {
            // Verificar si la base de datos se pobló correctamente
            long count = inventarioRepository.count();
            logger.info("===========================================");
            logger.info("Base de datos inicializada correctamente");
            logger.info("Total de productos en inventario: " + count);
            logger.info("===========================================");
            
            // Mostrar algunos productos como ejemplo
            if (count > 0) {
                logger.info("Primeros productos disponibles:");
                inventarioRepository.findAll().stream()
                    .limit(5)
                    .forEach(producto -> 
                        logger.info("- " + producto.getNombre() + " (SKU: " + producto.getSku() + ") - Stock: " + producto.getStock())
                    );
            }
        };
    }
}