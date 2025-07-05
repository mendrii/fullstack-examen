package com.perfulandia.gestiondeinventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.gestiondeinventario.model.Product;
import com.perfulandia.gestiondeinventario.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class ProductoService {

    @Autowired
    private ProductRepository productoRepository;

    public List<Product> findAll(){
        return productoRepository.findAll();
    }

    public Product findById(int id){
        return productoRepository.findById(id).get();
    }

    public Product save(Product prod){
        return productoRepository.save(prod);
    }

    public Product update(Product prod){
        return productoRepository.save(prod);
    }

    public void delete(int id){
        productoRepository.deleteById(id);
    }
}
