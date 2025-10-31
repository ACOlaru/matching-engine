package com.matchingengine.io;

import com.matchingengine.model.Order;

import java.util.List;

public interface Reader {
    public List<Order> loadOrders(String filename);
}
