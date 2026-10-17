package org.generation.backpetique.services;

import lombok.AllArgsConstructor;
import org.generation.backpetique.dtos.ProductDetailRequest;
import org.generation.backpetique.exceptions.ResourceNotFoundException;
import org.generation.backpetique.models.Orders;
import org.generation.backpetique.models.ProductDetail;
import org.generation.backpetique.models.ProductDetailCompose;
import org.generation.backpetique.models.Products;
import org.generation.backpetique.repository.OrdersRepository;
import org.generation.backpetique.repository.ProductDetailRepository;
import org.generation.backpetique.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductDetailService {
    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;

    //Obtener todas los detalles de los productos
    public List<ProductDetail> getAllProductDetails(){
        return  productDetailRepository.findAll();
    }

    //Obtener uno solo por id
    public ProductDetail getProuctDetailById(Long orderId, Long productId) {
        ProductDetailCompose llaveCompuesta = new ProductDetailCompose(orderId, productId);

        return productDetailRepository.findById(llaveCompuesta).orElseThrow(
                () -> new ResourceNotFoundException("Detalle no encontrado para la orden " + orderId + " y producto " + productId)
        );
    }

    public ProductDetail createProductDetail(Long orderId, ProductDetailRequest productDetailRequest) {
        Products producto = productRepository.findById(productDetailRequest.product_id())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con el id: " + productDetailRequest.product_id())
        );

        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrado con el id: " + orderId)
        );

        ProductDetailCompose llaveCompuesta = new ProductDetailCompose(orderId, productDetailRequest.product_id());

        ProductDetail productDetail = new ProductDetail();

        productDetail.setState("Pendiente");
        productDetail.setOrders(order);
        productDetail.setProducts(producto);
        productDetail.setQuantity(productDetailRequest.quantity());
        productDetail.setUnitPriceHistorical(producto.getPrice());
        productDetail.setProductDetailCompose(llaveCompuesta);

        return productDetailRepository.save(productDetail);
    }

    public ProductDetail updateState(Long orderId, Long productId, String newState){
        ProductDetail updateDetail = getProuctDetailById(orderId, productId);
        updateDetail.setState(newState);
        return productDetailRepository.save(updateDetail);
    }

}
