package org.generation.backpetique.models;

import jakarta.persistence.*;

@Entity
@Table(name ="brands")
public class Brand {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "brand_name", nullable = false, length = 50)
    private String brandName;

    @Column(name = "products_id")
    private Long productsId;

    @Column(name = "category_id")
    private Long categoryId;

    public Brand(){
    }

    public  Brand (Long brandId, String brandName, Long productsId, Long categoryId){
        this.brandId = brandId;
        this.brandName = brandName;
        this.productsId = productsId;
        this.categoryId = categoryId;
    }

    // Getter y Setters
    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getProductsId() {
        return productsId;
    }

    public void setProductsId(Long productsId) {
        this.productsId = productsId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
