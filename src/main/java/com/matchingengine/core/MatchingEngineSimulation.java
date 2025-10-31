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
        Reader reader = new CsvOrderReader();
        List<Order> orders = reader.loadOrders(filepath);

        for (Order order : orders) {
            System.out.println("Submitting order" + order.toString());

            List<Trade> trades = engine.processOrder(order);

            if (!trades.isEmpty()) {
                System.out.println("Trades not empty");
                printTrades(trades);
            } else {
                System.out.println("No trades found");
            }

            System.out.println(engine.getOrderBook().toString());
            System.out.println("-----------------------------------------");
        }
    }

    private static void printTrades(List<Trade> trades) {
        for (Trade trade : trades) {
            System.out.println(trade.toString());
        }
    }
}
