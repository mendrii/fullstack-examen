package com.perfulandia.gestiondeinventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perfulandia.gestiondeinventario.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer > {   
}
