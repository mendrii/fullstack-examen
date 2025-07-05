package com.perfulandia.gestiondeinventario.controllerTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import com.perfulandia.gestiondeinventario.controller.ProductController;
import com.perfulandia.gestiondeinventario.model.Product;
import com.perfulandia.gestiondeinventario.service.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {



    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product;


    @BeforeEach
    public void setUp(){
        product = new Product();
        product.setId(1);
        product.setName("Producto Test");
        product.setDescription("Descripción del producto de prueba");
        product.setPrice(100.0);
        product.setStockQuantity(50);
        product.setCategory("Electrónica");
        
    }
    

    @Test
    public void testGetAllProductos() throws Exception {
        when(productoService.findAll()).thenReturn(List.of(product));


        mockMvc.perform(get("/api/v1/productos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Producto Test"))
            .andExpect(jsonPath("$[0].description").value("Descripción del producto de prueba"))    
            .andExpect(jsonPath("$[0].price").value(100.0))
            .andExpect(jsonPath("$[0].stockQuantity").value(50))
            .andExpect(jsonPath("$[0].category").value("Electrónica"));
    }

    @Test
    public void testGetEnviosById() throws Exception {
        when(productoService.findById(1)).thenReturn(product);


        mockMvc.perform(get("/api/v1/productos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Producto Test"))
            .andExpect(jsonPath("$.description").value("Descripción del producto de prueba"))
            .andExpect(jsonPath("$.price").value(100.0))
            .andExpect(jsonPath("$.stockQuantity").value(50))
            .andExpect(jsonPath("$.category").value("Electrónica"));
    }


    @Test
    public void testSaveEnvio() throws Exception {
        when(productoService.save(any(Product.class))).thenReturn(product);


        mockMvc.perform(post("/api/v1/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(product)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Producto Test"))
            .andExpect(jsonPath("$.description").value("Descripción del producto de prueba"))
            .andExpect(jsonPath("$.price").value(100.0))
            .andExpect(jsonPath("$.stockQuantity").value(50))
            .andExpect(jsonPath("$.category").value("Electrónica"));
            
    }

   
   
    @Test
    public void testUpdateEnvio() throws Exception {
        when(productoService.findById(1)).thenReturn(product); 
        when(productoService.save(any(Product.class))).thenReturn(product);


        mockMvc.perform(put("/api/v1/productos/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(product)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Producto Test"))
            .andExpect(jsonPath("$.description").value("Descripción del producto de prueba"))
            .andExpect(jsonPath("$.price").value(100.0))
            .andExpect(jsonPath("$.stockQuantity").value(50))
            .andExpect(jsonPath("$.category").value("Electrónica"));
           

    }

    @Test
    public void testDelete() throws Exception {
    // Usa Integer.valueOf(1) para mayor claridad con el tipo
    doNothing().when(productoService).delete(1);

    mockMvc.perform(delete("/api/v1/productos/1"))
        .andExpect(status().isNoContent());

    verify(productoService, times(1)).delete(1);
   


}

}


