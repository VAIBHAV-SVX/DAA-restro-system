package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/session")
@CrossOrigin(origins = "*") 
public class OrderSessionController {

    private final Map<String, SessionData> activeSessions = new ConcurrentHashMap<>();

    @GetMapping("/start")
    public String startSession(
            @RequestParam String tableNo,
            @RequestParam String items,
            @RequestParam double total) {
        
        SessionData session = activeSessions.getOrDefault(tableNo, new SessionData());
        session.setTableNo(tableNo);
        session.setHasActiveOrder(true);
        session.setOrderedItems(items);
        session.setOrderStatus("RECEIVED");
        
        String logMsg = "> [ORDER RECEIVED]: Table " + tableNo + " placed " + items + " (Total: ₹" + total + ")";
        session.setLatestManagerLog(logMsg);
        
        activeSessions.put(tableNo, session);
        return "Order logged for Table " + tableNo;
    }

    // 🗲 Reads the current log text, wipes it clean from memory, then returns it!
    @GetMapping("/status")
    public SessionData getStatus(@RequestParam String tableNo) {
        SessionData session = activeSessions.getOrDefault(tableNo, new SessionData());
        
        // 1. Create a shallow clone copy to send back to the frontend
        SessionData responseData = new SessionData();
        responseData.setTableNo(session.getTableNo());
        responseData.setHasActiveOrder(session.isHasActiveOrder());
        responseData.setOrderedItems(session.getOrderedItems());
        responseData.setOrderStatus(session.getOrderStatus());
        responseData.setLatestManagerLog(session.getLatestManagerLog()); // Contains the log
        
        // 2. WIPE the master log string in memory so it doesn't loop next time
        session.setLatestManagerLog(""); 
        
        return responseData;
    }

    @GetMapping("/post-log")
    public String postLog(@RequestParam String tableNo, @RequestParam String msg) {
        SessionData session = activeSessions.getOrDefault(tableNo, new SessionData());
        session.setLatestManagerLog(msg);
        activeSessions.put(tableNo, session);
        return "Log updated.";
    }

    @GetMapping("/update-status")
    public String updateStatus(@RequestParam String tableNo, @RequestParam String status) {
        SessionData session = activeSessions.get(tableNo);
        if (session != null) {
            session.setOrderStatus(status);
            session.setLatestManagerLog("> [KITCHEN STATUS UPDATE]: Table " + tableNo + " changed to " + status);
        }
        return "Status updated to " + status;
    }

    public static class SessionData {
        private String tableNo = "";
        private boolean hasActiveOrder = false;
        private String orderedItems = "";
        private String orderStatus = "";
        private String latestManagerLog = "";

        public String getTableNo() { return tableNo; }
        public void setTableNo(String tableNo) { this.tableNo = tableNo; }
        public boolean isHasActiveOrder() { return hasActiveOrder; }
        public void setHasActiveOrder(boolean hasActiveOrder) { this.hasActiveOrder = hasActiveOrder; }
        public String getOrderedItems() { return orderedItems; }
        public void setOrderedItems(String orderedItems) { this.orderedItems = orderedItems; }
        public String getOrderStatus() { return orderStatus; }
        public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
        public String getLatestManagerLog() { return latestManagerLog; }
        public void setLatestManagerLog(String latestManagerLog) { this.latestManagerLog = latestManagerLog; }
    }
}