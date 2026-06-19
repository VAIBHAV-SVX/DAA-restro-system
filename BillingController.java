package com.example.demo.controller;

import com.example.demo.service.BillSplittingService;
import com.example.demo.service.BillSplittingService.Transaction;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/billing")
@CrossOrigin(origins = "*")
public class BillingController {

    private final BillSplittingService billSplittingService;

    public BillingController(BillSplittingService billSplittingService) {
        this.billSplittingService = billSplittingService;
    }

    // Direct route to test your Unit 4 Greedy Split Engine
    // Example URL: http://localhost:8080/api/billing/test-split
    @GetMapping("/test-split")
    public List<Transaction> testGreedySplit() {
        // Let's assume 3 friends at Table 5: Rahul, Amit, and Sumit
        List<String> friends = Arrays.asList("Rahul", "Amit", "Sumit");

        // Net balance array map: 
        // Rahul paid more than his share, so he is owed +₹300.
        // Amit owes money to the table group, his net balance is -₹100.
        // Sumit owes money to the table group, his net balance is -₹200.
        double[] netTableBalances = {300.0, -100.0, -200.0};

        // Execute our Greedy Algorithm logic
        return billSplittingService.optimizeSplits(netTableBalances, friends);
    }
}