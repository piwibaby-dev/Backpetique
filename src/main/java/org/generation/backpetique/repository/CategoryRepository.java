package org.generation.backpetique.repository;

import org.generation.backpetique.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


///Despues de Models Category sigue interface Category Repository

public interface CategoryRepository extends JpaRepository<Category, Long> {


//ejemplo de una lista de categorias con el id de un producto?

    // List<Category> findByProductsId(Long productsId);

    //Una lista de categorias con el nombre de categoria??

    // List<Category> findByCategoryName(String CategoryName);
}
