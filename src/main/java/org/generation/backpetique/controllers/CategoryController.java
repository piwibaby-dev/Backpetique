package org.generation.backpetique.controllers;


import lombok.AllArgsConstructor;
import org.generation.backpetique.dtos.CategoryRequest;
import org.generation.backpetique.models.Category;
import org.generation.backpetique.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController


@AllArgsConstructor
@RequestMapping (path = "/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    // GET: todas las categorías
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategory();
        return ResponseEntity.ok(categories);
    }

    //GET: Por Id
    @GetMapping(path = "{categoryId}")
    public ResponseEntity<Category> getCategorieById(@PathVariable("categoryId") Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
    // POST: crear nueva categoría
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    // PUT: actualizar categoría
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id,
                                                   @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.updateCategoryById(id, categoryRequest));
    }

    // DELETE: eliminar categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }



    /*
    // POST: asociar producto a categoría de acuerdo a models - Products!!
    @PostMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<Category> addProductToCategory(@PathVariable Long categoryId,
                                                         @PathVariable Long productId) {
        Category category = categoryService.addProductToCategory(categoryId, productId);
        return ResponseEntity.ok(category);

*/


/*

http://localhost:8080/api/categories  para crear una categoria en Bruno o ver todas las categorias una vez la tengas creadas

A las demás peticiones solo agrega el Id (eliminar, actualizar)


{
  "categoryName":"Snacks"

}



 */








}
