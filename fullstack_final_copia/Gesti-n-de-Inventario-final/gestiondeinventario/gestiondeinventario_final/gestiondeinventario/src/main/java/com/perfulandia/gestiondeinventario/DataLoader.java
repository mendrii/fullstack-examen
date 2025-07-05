package com.perfulandia.gestiondeinventario;

import com.perfulandia.gestiondeinventario.model.Product;
import com.perfulandia.gestiondeinventario.repository.ProductRepository;
import net.datafaker.Faker;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Profile("dev")
@Component

public class DataLoader implements CommandLineRunner{

    @Autowired
    private ProductRepository productRepository;

     @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        for (int i = 0; i < 30; i++) {
            try {
                Product producto = new Product();
                producto.setName(faker.commerce().productName());
                producto.setDescription(faker.lorem().sentence());
                producto.setPrice(Double.parseDouble(faker.commerce().price(10.0, 1000.0)));
                producto.setStockQuantity(faker.number().numberBetween(1, 100));
                producto.setCategory(faker.commerce().department());
                productRepository.save(producto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("¡30 productos de prueba generados con Faker!");
    }
}



