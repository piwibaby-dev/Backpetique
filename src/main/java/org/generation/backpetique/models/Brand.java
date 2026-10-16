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

    public Brand(){
    }

    public  Brand (Long brandId, String brandName, Long productsId, Long categoryId){
        this.brandId = brandId;
        this.brandName = brandName;
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
}
