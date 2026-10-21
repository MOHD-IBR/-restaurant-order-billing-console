package com.restaurant.controller;

import com.restaurant.service.MenuItemService;
import com.restaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("menuCount", menuItemService.getAllMenuItems().size());
        model.addAttribute("orderCount", orderService.getAllOrders().size());
        return "index";
    }
}
