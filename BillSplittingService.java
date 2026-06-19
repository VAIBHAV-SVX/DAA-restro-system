package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillSplittingService {

    // Helper data structure to return structured outputs for the manager/cashier
    public static class Transaction {
        public String debtor;
        public String creditor;
        public double amount;

        public Transaction(String debtor, String creditor, double amount) {
            this.debtor = debtor;
            this.creditor = creditor;
            this.amount = amount;
        }
        
        @Override
        public String toString() {
            return debtor + " needs to pay ₹" + String.format("%.2f", amount) + " to " + creditor;
        }
    }

    // Core Unit 4 Greedy Algorithm: Minimize Cash Flow
    public List<Transaction> optimizeSplits(double[] balances, List<String> participants) {
        List<Transaction> optimizedReceipts = new ArrayList<>();
        int numPeople = balances.length;
        
        // Deep copy balances so we don't manipulate state outside the function
        double[] localBalances = balances.clone();

        while (true) {
            int maxCreditIdx = 0;
            int maxDebitIdx = 0;

            // Greedily look for the maximum creditor (owed money) and maximum debtor (owes money)
            for (int i = 1; i < numPeople; i++) {
                if (localBalances[i] > localBalances[maxCreditIdx]) maxCreditIdx = i;
                if (localBalances[i] < localBalances[maxDebitIdx]) maxDebitIdx = i;
            }

            // Base Case: If the highest credit/debit values are zero, everything balances out completely
            if (Math.abs(localBalances[maxCreditIdx]) < 0.01 && Math.abs(localBalances[maxDebitIdx]) < 0.01) {
                break;
            }

            // Determine the minimum absolute value that can change hands greedily
            double amountToSettle = Math.min(localBalances[maxCreditIdx], -localBalances[maxDebitIdx]);

            // Shift internal cash flow balances
            localBalances[maxCreditIdx] -= amountToSettle;
            localBalances[maxDebitIdx] += amountToSettle;

            // Record this transaction for the counter manager
            optimizedReceipts.add(new Transaction(
                participants.get(maxDebitIdx), 
                participants.get(maxCreditIdx), 
                amountToSettle
            ));
        }
        return optimizedReceipts;
    }
}