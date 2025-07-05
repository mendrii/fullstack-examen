package com.perfulandia.gestiondeinventario.controller;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia.gestiondeinventario.model.Product;
import com.perfulandia.gestiondeinventario.service.ProductoService;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductController {

    @Autowired
    private ProductoService productoService;

   @GetMapping
    @Operation(summary = "Listar todos los productos", description = "Obtiene una lista de todos los productos disponibles en el inventario.")
    public ResponseEntity<CollectionModel<EntityModel<Product>>> listar() {
        List<Product> productos = productoService.findAll();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<EntityModel<Product>> productosHateoas = productos.stream()
            .map(producto -> EntityModel.of(producto,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).buscar(producto.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).listar()).withRel("productos")
            ))
            .toList();

        CollectionModel<EntityModel<Product>> collectionModel = CollectionModel.of(productosHateoas,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).listar()).withSelfRel()
        );
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    @Operation(summary = "Guardar un nuevo producto", description = "Crea un nuevo producto en el inventario.")
    public ResponseEntity<EntityModel<Product>> guardar(@RequestBody Product product) {
        Product productonuevo = productoService.save(product);
        EntityModel<Product> productHateoas = EntityModel.of(productonuevo,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).buscar(productonuevo.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).listar()).withRel("productos")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(productHateoas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar un producto por ID", description = "Obtiene los detalles de un producto específico por su ID.")
    public ResponseEntity<EntityModel<Product>> buscar(@PathVariable Integer id) {
        try {
            Product product = productoService.findById(id);
            EntityModel<Product> productHateoas = EntityModel.of(product,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).buscar(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).listar()).withRel("productos")
            );
            return ResponseEntity.ok(productHateoas);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

   @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto", description = "Actualiza los detalles de un producto existente por su ID.")
    public ResponseEntity<EntityModel<Product>> actualizar(@PathVariable Integer id, @RequestBody Product product) {
        try {
            Product prod = productoService.findById(id);
            prod.setId(id);
            prod.setName(product.getName());
            prod.setDescription(product.getDescription());
            prod.setPrice(product.getPrice());
            prod.setStockQuantity(product.getStockQuantity());
            productoService.save(prod);

            EntityModel<Product> productHateoas = EntityModel.of(prod,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).buscar(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).listar()).withRel("productos")
            );
            return ResponseEntity.ok(productHateoas);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
   
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto", description = "Elimina un producto del inventario por su ID.")
   public ResponseEntity<?> eliminar(@PathVariable int id){
    try {
            productoService.delete(id);
            return ResponseEntity.noContent().build();
    } catch (Exception e) {
            return ResponseEntity.notFound().build();
    }
}

}