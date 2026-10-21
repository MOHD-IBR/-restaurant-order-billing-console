package com.restaurant.controller;

import com.restaurant.model.MenuItem;
import com.restaurant.model.Order;
import com.restaurant.model.OrderItem;
import com.restaurant.service.MenuItemService;
import com.restaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MenuItemService menuItemService;

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders/list";
    }

    @GetMapping("/new")
    public String showNewOrderForm(Model model) {
        model.addAttribute("menuItems", menuItemService.getAvailableMenuItems());
        model.addAttribute("order", new Order());
        return "orders/new";
    }

    @PostMapping("/new")
    public String createOrder(@RequestParam String customerName,
                              @RequestParam String tableNumber,
                              @RequestParam Map<String, String> allParams) {

        Order order = new Order();
        order.setCustomerName(customerName);
        order.setTableNumber(tableNumber);

        List<OrderItem> items = new ArrayList<>();

        // Process quantities from form (format: qty_1, qty_2 etc.)
        for (String key : allParams.keySet()) {
            if (key.startsWith("qty_")) {
                String idStr = key.substring(4);
                try {
                    Long menuId = Long.parseLong(idStr);
                    int qty = Integer.parseInt(allParams.get(key));

                    if (qty > 0) {
                        MenuItem menuItem = menuItemService.getMenuItemById(menuId).orElse(null);
                        if (menuItem != null) {
                            OrderItem orderItem = new OrderItem(
                                    menuItem.getName(),
                                    menuItem.getPrice(),
                                    qty
                            );
                            order.addOrderItem(orderItem);
                        }
                    }
                } catch (NumberFormatException e) {
                    // ignore invalid
                }
            }
        }

        if (order.getOrderItems().isEmpty()) {
            return "redirect:/orders/new?error=empty";
        }

        Order savedOrder = orderService.saveOrder(order);
        return "redirect:/orders/bill/" + savedOrder.getId();
    }

    @GetMapping("/bill/{id}")
    public String showBill(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + id));
        model.addAttribute("order", order);
        return "orders/bill";
    }

    @GetMapping("/view/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + id));
        model.addAttribute("order", order);
        return "orders/view";
    }
}
