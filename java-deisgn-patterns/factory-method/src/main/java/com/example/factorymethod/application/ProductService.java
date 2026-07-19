package com.example.factorymethod.application;

import com.example.factorymethod.api.FinancialProduct;
import com.example.factorymethod.api.ProductFactory;

import java.util.Optional;
import java.util.function.Supplier;

public class ProductService {
    private final ProductFactory factory;

    public ProductService(ProductFactory factory) {
        this.factory = factory;
    }

    public Optional<String> describeProduct(String type) {
        return Optional.ofNullable(type)
                .map(factory::create)
                .map(FinancialProduct::describe);
    }

    public static ProductService createDefault() {
        Supplier<ProductFactory> supplier = ProductFactory::defaultFactory;
        return new ProductService(supplier.get());
    }
}
