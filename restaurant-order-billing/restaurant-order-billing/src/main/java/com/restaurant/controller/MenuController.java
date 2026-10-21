package com.restaurant.controller;

import com.restaurant.model.MenuItem;
import com.restaurant.service.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuItemService menuItemService;

    @GetMapping
    public String listMenu(Model model) {
        model.addAttribute("menuItems", menuItemService.getAllMenuItems());
        return "menu/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("menuItem", new MenuItem());
        return "menu/form";
    }

    @PostMapping("/add")
    public String addMenuItem(@Valid @ModelAttribute("menuItem") MenuItem menuItem,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "menu/form";
        }
        menuItemService.saveMenuItem(menuItem);
        return "redirect:/menu";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        MenuItem menuItem = menuItemService.getMenuItemById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid menu item Id:" + id));
        model.addAttribute("menuItem", menuItem);
        return "menu/form";
    }

    @PostMapping("/edit/{id}")
    public String updateMenuItem(@PathVariable Long id,
                                 @Valid @ModelAttribute("menuItem") MenuItem menuItem,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "menu/form";
        }
        menuItem.setId(id);
        menuItemService.saveMenuItem(menuItem);
        return "redirect:/menu";
    }

    @GetMapping("/delete/{id}")
    public String deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
        return "redirect:/menu";
    }
}
