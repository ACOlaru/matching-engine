package com.matchingengine.core;

import com.matchingengine.model.Order;
import com.matchingengine.model.Trade;
import com.matchingengine.pattern.strategy.MatchingStrategy;

import java.util.List;

public class MatchingEngine {
    private OrderBook orderBook;
    private MatchingStrategy matchingStrategy;

    public MatchingEngine(MatchingStrategy matchingStrategy) {
        orderBook = new OrderBook();
        this.matchingStrategy = matchingStrategy;
    }

    public OrderBook getOrderBook() {
        return orderBook;
    }

    public MatchingStrategy getMatchingStrategy() {
        return matchingStrategy;
    }

    public List<Trade> processOrder(Order order) {
        List<Trade> trades = matchingStrategy.match(order, orderBook);

        if (!order.isFilled()) {
            orderBook.add(order);
        }

        return trades;
    }
}
