package org.generation.backpetique.config;

import lombok.AllArgsConstructor;
import org.generation.backpetique.models.*;
import org.generation.backpetique.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final PetsTypeRepository petsTypeRepository;
    private final UsersRepository usersRepository;
    private final PetsRepository petsRepository;
    private final OrdersRepository ordersRepository;
    private final PaymentRepository paymentRepository;
    private final ProductDetailRepository productDetailRepository;
    private final PaymentMethodsRepository paymentMethodsRepository;
    private final JsonMapper jsonMapper;

    record SeedProduct(String sku, String title, String description, String species, String category,
                       String subcategory, String brand, String lifeStage, String breedSize, BigDecimal price,
                       Integer stock, BigDecimal weight, String unit, Boolean prescription, String urlImg) {
    }

    record SeedPet(String petName, String petType, String petAge, String petWeight, String petSize) {
    }

    record SeedUser(String userName, String surNames, String email, String phoneNumber, String birthDate,
                    String passwordSite, List<SeedPet> pets) {
    }

    record SeedItem(String sku, Integer quantity) {
    }

    record SeedPaymentMethod(String email, String type, String alias, String holderName, String brand, String last4,
                             String expiry, String paypalEmail, Boolean isDefault) {
    }

    record SeedOrder(String email, String status, String paymentMethod, Integer daysAgo, List<SeedItem> items) {
    }

    @Override
    public void run(String... args) throws Exception {
        List.of("Perro", "Gato", "Perro y Gato").forEach(this::getPetType);
        loadProducts();
        loadUsers();
        if (ordersRepository.count() == 0) loadOrders();
        if (paymentMethodsRepository.count() == 0) loadPaymentMethods();
    }

    private <T> List<T> read(String path, TypeReference<List<T>> type) throws IOException {
        try (InputStream input = new ClassPathResource(path).getInputStream()) {
            return jsonMapper.readValue(input, type);
        }
    }

    private PetsType getPetType(String name) {
        return petsTypeRepository.findByPetType(name)
                .orElseGet(() -> petsTypeRepository.save(new PetsType(null, name)));
    }

    private void loadProducts() throws IOException {
        for (SeedProduct seed : read("data/products.json", new TypeReference<List<SeedProduct>>() {})) {
            if (productRepository.findBySku(seed.sku()).isPresent()) continue;

            Category category = categoryRepository.findByCategoryName(seed.category())
                    .orElseGet(() -> categoryRepository.save(new Category(null, seed.category())));
            Brand brand = brandRepository.findByBrandName(seed.brand()).orElseGet(() -> {
                Brand newBrand = new Brand();
                newBrand.setBrandName(seed.brand());
                return brandRepository.save(newBrand);
            });

            Products product = new Products();
            product.setSku(seed.sku());
            product.setTitle(seed.title());
            product.setDescription(seed.description());
            product.setSpecies(seed.species());
            product.setSubcategory(seed.subcategory());
            product.setLifeStage(seed.lifeStage());
            product.setBreedSize(seed.breedSize());
            product.setPrice(seed.price());
            product.setStock(seed.stock());
            product.setWeight(seed.weight());
            product.setUnit(seed.unit());
            product.setPrescription(seed.prescription());
            product.setUrlImg(seed.urlImg());
            product.setCategoryId(category);
            product.setBrandId(brand);
            productRepository.save(product);
        }
    }

    private void loadUsers() throws IOException {
        for (SeedUser seed : read("data/users.json", new TypeReference<List<SeedUser>>() {})) {
            if (usersRepository.existsByEmail(seed.email())) continue;

            Users user = new Users();
            user.setUserName(seed.userName());
            user.setSurNames(seed.surNames());
            user.setEmail(seed.email());
            user.setPhoneNumber(seed.phoneNumber());
            user.setBirthDate(Date.valueOf(LocalDate.parse(seed.birthDate())));
            user.setPasswordSite(seed.passwordSite());
            user.setPets(new ArrayList<>());
            usersRepository.save(user);

            for (SeedPet seedPet : seed.pets()) {
                Pets pet = new Pets();
                pet.setPetName(seedPet.petName());
                pet.setPetAge(seedPet.petAge());
                pet.setPetWeight(seedPet.petWeight());
                pet.setPetSize(seedPet.petSize());
                pet.setPetType(getPetType(seedPet.petType()));
                pet.setUserId(user);
                petsRepository.save(pet);
            }
        }
    }

    private void loadPaymentMethods() throws IOException {
        for (SeedPaymentMethod seed : read("data/payment-methods.json", new TypeReference<List<SeedPaymentMethod>>() {})) {
            usersRepository.findByEmail(seed.email()).ifPresent(user -> {
                PaymentMethods method = new PaymentMethods();
                method.setType(seed.type());
                method.setAlias(seed.alias());
                method.setHolderName(seed.holderName());
                method.setBrand(seed.brand());
                method.setLast4(seed.last4());
                method.setExpiry(seed.expiry());
                method.setPaypalEmail(seed.paypalEmail());
                method.setIsDefault(seed.isDefault());
                method.setUserId(user);
                paymentMethodsRepository.save(method);
            });
        }
    }

    private void loadOrders() throws IOException {
        for (SeedOrder seed : read("data/orders.json", new TypeReference<List<SeedOrder>>() {})) {
            Users user = usersRepository.findByEmail(seed.email()).orElseThrow();
            List<Products> products = seed.items().stream()
                    .map(item -> productRepository.findBySku(item.sku()).orElseThrow())
                    .toList();

            BigDecimal total = BigDecimal.ZERO;
            for (int i = 0; i < products.size(); i++) {
                total = total.add(products.get(i).getPrice().multiply(BigDecimal.valueOf(seed.items().get(i).quantity())));
            }

            Orders order = new Orders();
            order.setTotalAmount(total);
            order.setStatusO(seed.status());
            order.setOrderDate(LocalDateTime.now().minusDays(seed.daysAgo()));
            order.setUserId(user);
            ordersRepository.save(order);

            String state = seed.status().charAt(0) + seed.status().substring(1).toLowerCase();
            for (int i = 0; i < products.size(); i++) {
                ProductDetail detail = new ProductDetail();
                detail.setProductDetailCompose(new ProductDetailCompose(order.getId(), products.get(i).getProductId()));
                detail.setOrders(order);
                detail.setProducts(products.get(i));
                detail.setQuantity(seed.items().get(i).quantity());
                detail.setUnitPriceHistorical(products.get(i).getPrice());
                detail.setState(state);
                productDetailRepository.save(detail);
            }

            if (!seed.status().equals("PENDIENTE")) {
                Payment payment = new Payment();
                payment.setOrderId(order.getId());
                payment.setPaymentMethod(seed.paymentMethod());
                payment.setAmount(total);
                paymentRepository.save(payment);
                order.setPaymentId(payment);
                ordersRepository.save(order);
            }
        }
    }
}
