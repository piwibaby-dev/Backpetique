package org.generation.backpetique.services;


import lombok.AllArgsConstructor;
import org.generation.backpetique.dtos.CategoryRequest;
import org.generation.backpetique.exceptions.ResourceNotFoundException;
import org.generation.backpetique.models.Category;
import org.generation.backpetique.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

//logica de negocio categoriasss

public class CategoryService {


    private CategoryRepository categoryRepository;
    //private ProductsRepository productsRepository


    //ver categorias ver todas las categorias?
    //buscar una categoria especifica con su id
    //crear categoria y unir a producto?

//Ver todas la categorias

//Obtener todas las categorias
    public List<Category> getAllCategory (){
        return categoryRepository.findAll();
    }

    //buscar categoria con su id?

    public Category getCategoryById(Long id){
        return categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Categoria con el Id " + id + " no encontrado" ));
    }

    //Crear Categoria Nueva!

    public Category createCategory (Category category){

        return categoryRepository.save(category);
    }


    //Update Category

    public Category updateCategoryById(Long id , CategoryRequest categoryRequest){


        Category categoryStored = getCategoryById(id);

        // Actualizar el nombre con lo que venga en el DTO
        categoryStored.setCategoryName(categoryRequest.categoryName());

        // Guardar cambios en la BD
        return categoryRepository.save(categoryStored);
    }


    //Eliminar Categoria

public Category  deleteCategoryById(Long id){

        Category category = getCategoryById(id);
        categoryRepository.delete(category);
        return category;

}




    /*
 Asociar productos a categoria en el service
 Similar a Un artista a muchas canciones
 Una categoria a muchos productos?

 @Autowired
 private ProductsRepository productsRepository;

 public Category addProductToCategory (Long CategoryId, Long ProductId) {

 //Busca la categoria
 Category category = getCategoryById(categoryId);

 //BuscaProducto

 Products products = productsRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException ("Producto con el Id " + productId + " no encontrado"));

 // Asociar producto a la categoría
    product.setCategory(category);
    productsRepository.save(product);

    return category;



 }

*/




}
