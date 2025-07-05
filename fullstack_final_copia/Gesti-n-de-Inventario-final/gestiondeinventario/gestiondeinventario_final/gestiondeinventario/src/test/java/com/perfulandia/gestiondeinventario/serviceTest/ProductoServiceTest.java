package com.perfulandia.gestiondeinventario.serviceTest;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

import com.perfulandia.gestiondeinventario.model.Product;
import com.perfulandia.gestiondeinventario.repository.ProductRepository;
import com.perfulandia.gestiondeinventario.service.ProductoService;

@SpringBootTest

public class ProductoServiceTest {

@Autowired
    private ProductoService productoService;


    @MockBean
    private ProductRepository productRepository;


    @Test
    public void testFindAll(){
        Product producto = new Product();
        producto.setId(1);
        producto.setName("Producto Test");
        producto.setDescription("Descripción del producto de prueba");
        producto.setPrice(100.0);
        producto.setStockQuantity(0);
        producto.setCategory(null);

        when(productRepository.findAll()).thenReturn(List.of(producto));
        List<Product> product = productoService.findAll();
        assertNotNull(product);
        assertEquals(1, product.size());
    }



    @Test 
    public void testFyndById(){
        int id = 1;
        Product producto = new Product();
        producto.setId(id);
        producto.setName("Producto Test");
        producto.setDescription("Descripción del producto de prueba");
        producto.setPrice(100.0);
        producto.setStockQuantity(0);
        producto.setCategory(null);
        when(productRepository.findById(id)).thenReturn(Optional.of(producto));
        
        Product found = productoService.findById(id);
        assertNotNull(found);
        assertEquals( id, found.getId());
    
    
    }

    @Test
    public void save(){
        Product producto = new Product(3, "Producto Test", "Descripción del producto de prueba", 100.0, 10, null);
        when(productRepository.save(producto)).thenReturn(producto);
    
        Product saved = productoService.save(producto);
        assertNotNull(saved);
        assertEquals(3, saved.getId());
    }

  
    @Test
    public void delete() {
        int id = 1;
        when(productRepository.existsById(id)).thenReturn(true);

        productoService.delete(id);

        verify(productRepository, times(1)).deleteById(id);
    }

}
