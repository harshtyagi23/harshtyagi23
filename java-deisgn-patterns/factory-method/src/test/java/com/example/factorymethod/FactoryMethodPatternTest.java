package com.example.factorymethod;

import com.example.factorymethod.application.ProductService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FactoryMethodPatternTest {

    @Test
    void shouldCreateFinancialProductsUsingFactory() {
        ProductService service = ProductService.createDefault();

        Optional<String> equity = service.describeProduct("equity");
        Optional<String> bond = service.describeProduct("bond");
        Optional<String> unsupported = service.describeProduct("fund");

        assertTrue(equity.isPresent());
        assertTrue(bond.isPresent());
        assertTrue(unsupported.isEmpty());
        assertEquals("Equity product: AAPL", equity.get());
        assertEquals("Bond product: Contoso Bank maturing in 2035", bond.get());
    }
}
