package com.matchingengine.pattern.strategy;

import com.matchingengine.core.OrderBook;
import com.matchingengine.model.Order;
import com.matchingengine.model.Trade;

import java.util.List;

public interface MatchingStrategy {
    List<Trade> match(Order incomingOrder, OrderBook orderBook);
}
