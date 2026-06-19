package com.example.demo.controller;

import com.example.demo.model.MenuItem;
import com.example.demo.service.MenuSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "*") // Allows any frontend client to talk to this endpoint safely
public class MenuController {

    @Autowired
    private MenuSearchService menuSearchService;

    // Route to search items via our Unit 5 KMP String Matching Algorithm
    // Example access URL: http://localhost:8080/api/menu/search?query=paneer
    @GetMapping("/search")
    public List<MenuItem> search(@RequestParam String query) {
        return menuSearchService.searchMenu(query);
    }
}