package com.matchingengine.core;

import com.matchingengine.model.Order;
import com.matchingengine.model.Trade;
import com.matchingengine.pattern.strategy.MatchingStrategy;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MatchingEngine {

    private final OrderBook orderBook;
    private final MatchingStrategy matchingStrategy;
    private final Lock lock = new ReentrantLock();

    public MatchingEngine(MatchingStrategy matchingStrategy) {
        this.orderBook = new OrderBook();
        this.matchingStrategy = matchingStrategy;
    }

    public OrderBook getOrderBook() {
        return orderBook;
    }

    public MatchingStrategy getMatchingStrategy() {
        return matchingStrategy;
    }

    public List<Trade> processOrder(Order order) {
        lock.lock();
        try {
            List<Trade> trades = matchingStrategy.match(order, orderBook);

            if (!order.isFilled()) {
                orderBook.add(order);
            }

            return trades;
        } finally {
            lock.unlock();
        }
    }
}
