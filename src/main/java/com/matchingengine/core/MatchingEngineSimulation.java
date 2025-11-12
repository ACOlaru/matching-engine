package com.matchingengine.core;

import com.matchingengine.io.CsvOrderReader;
import com.matchingengine.io.Reader;
import com.matchingengine.model.Order;
import com.matchingengine.model.Side;
import com.matchingengine.model.Trade;
import com.matchingengine.pattern.strategy.FifoMatchingStrategy;
import com.matchingengine.pattern.strategy.MatchingStrategy;

import java.util.ArrayList;
import java.util.List;

public class MatchingEngineSimulation {

    public static void main( String[] args )
    {
        String filepath = "src/main/resources/orders.csv";

        MatchingStrategy strategy = new FifoMatchingStrategy();
        MatchingEngine engine = new MatchingEngine(strategy);
        OrderDispatcher dispatcher = new OrderDispatcher(engine);
        dispatcher.start();

        Reader reader = new CsvOrderReader();
        List<Order> orders = reader.loadOrders(filepath);

        for (Order order : orders) {
            dispatcher.submitOrder(order);

        }
    }

    private static void printTrades(List<Trade> trades) {
        for (Trade trade : trades) {
            System.out.println(trade.toString());
        }
    }
}
