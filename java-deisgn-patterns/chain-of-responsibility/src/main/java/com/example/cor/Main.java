package com.example.cor;

import com.example.cor.api.TradeRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        TradeApprovalService service = new TradeApprovalService();
        List<TradeRequest> requests = List.of(
                new TradeRequest("Equity Desk", "private", "ACME", BigDecimal.valueOf(50_000), BigDecimal.valueOf(2_500_000), Optional.of("Quarter-end rebalance")),
                new TradeRequest("Credit Desk", "institutional", "SANCTIONED-BOND", BigDecimal.valueOf(10_000), BigDecimal.valueOf(1_500_000), Optional.empty())
        );

        requests.stream()
                .map(service::review)
                .forEach(System.out::println);
    }
}
