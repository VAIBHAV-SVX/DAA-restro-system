package com.example.demo.service;

import com.example.demo.model.MenuItem;
import com.example.demo.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuSearchService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    // Pulls all items from DB and filters them using custom KMP logic
    public List<MenuItem> searchMenu(String userPattern) {
        List<MenuItem> allItems = menuItemRepository.findAll();
        List<MenuItem> matchedItems = new ArrayList<>();

        for (MenuItem item : allItems) {
            if (kmpMatch(item.getName(), userPattern) || kmpMatch(item.getDescription(), userPattern)) {
                matchedItems.add(item);
            }
        }
        return matchedItems;
    }

    // Core KMP Algorithm from your DAA Syllabus
    private boolean kmpMatch(String text, String pattern) {
        if (pattern == null || pattern.isEmpty()) return true;
        if (text == null || text.isEmpty()) return false;

        String txt = text.toLowerCase();
        String pat = pattern.toLowerCase();
        
        int M = pat.length();
        int N = txt.length();
        
        int[] lps = computeLPSArray(pat, M);
        int i = 0; // index for txt
        int j = 0; // index for pat

        while (i < N) {
            if (pat.charAt(j) == txt.charAt(i)) {
                j++;
                i++;
            }
            if (j == M) {
                return true; // Found a pattern match match
            } else if (i < N && pat.charAt(j) != txt.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i = i + 1;
                }
            }
        }
        return false;
    }

    private int[] computeLPSArray(String pat, int M) {
        int[] lps = new int[M];
        int len = 0;
        int i = 1;
        lps[0] = 0;

        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }
}