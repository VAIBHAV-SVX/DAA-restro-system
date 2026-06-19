package com.example.demo.controller;

import com.example.demo.service.KitchenRoutingService;
import com.example.demo.service.KitchenRoutingService.Node;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/kitchen")
@CrossOrigin(origins = "*")
public class KitchenRoutingController {

    private final KitchenRoutingService routingService;

    public KitchenRoutingController(KitchenRoutingService routingService) {
        this.routingService = routingService;
    }

    // Test Path configuration across restaurant substations
    // Example access: http://localhost:8080/api/kitchen/route-delivery
    @GetMapping("/route-delivery")
    public Map<String, Integer> getDeliveryRoute() {
        int totalStations = 5; 
        // Station ID Mapping: 
        // 0: Central Pickup/Start, 1: Drink Bar, 2: Tandoor/Starter, 3: Main Line, 4: Desserts
        
        List<List<Node>> adjList = new ArrayList<>();
        for (int i = 0; i < totalStations; i++) {
            adjList.add(new ArrayList<>());
        }

        // Add Graph Edges (Source, Destination, Transit Cost/Weight)
        adjList.get(0).add(new Node(1, 4)); // Start -> Drink Bar (Weight 4)
        adjList.get(0).add(new Node(2, 2)); // Start -> Tandoor (Weight 2)
        adjList.get(1).add(new Node(3, 3)); // Drink Bar -> Main Line (Weight 3)
        adjList.get(2).add(new Node(1, 1)); // Tandoor -> Drink Bar (Weight 1)
        adjList.get(2).add(new Node(3, 5)); // Tandoor -> Main Line (Weight 5)
        adjList.get(3).add(new Node(4, 2)); // Main Line -> Desserts (Weight 2)

        // Calculate single-source shortest path starting from Central Pickup (0)
        int[] shortestDistances = routingService.calculateShortestPaths(totalStations, adjList, 0);

        Map<String, Integer> results = new LinkedHashMap<>();
        results.put("Central Pickup to Drink Bar", shortestDistances[1]);
        results.put("Central Pickup to Tandoor Station", shortestDistances[2]);
        results.put("Central Pickup to Main Course Line", shortestDistances[3]);
        results.put("Central Pickup to Dessert Station", shortestDistances[4]);

        return results;
    }
}