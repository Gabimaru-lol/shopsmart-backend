package com.arnav.ecommerce.dsa;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trie {

    private final TrieNode root = new TrieNode();

    public void insert(String word, Long productId) {
        TrieNode current = root;
        String normalized = word.toLowerCase();

        for (char ch : normalized.toCharArray()) {
            current.children.putIfAbsent(ch, new TrieNode());
            current = current.children.get(ch);
        }
        current.isEndOfWord = true;
        current.productId = productId;
    }

    public List<Long> searchByPrefix(String prefix) {
        List<Long> results = new ArrayList<>();
        TrieNode current = root;
        String normalized = prefix.toLowerCase();

        for (char ch : normalized.toCharArray()) {
            TrieNode next = current.children.get(ch);
            if (next == null) {
                return results;
            }
            current = next;
        }

        collectAllWords(current, results);
        return results;
    }

    private void collectAllWords(TrieNode node, List<Long> results) {
        if (node.isEndOfWord) {
            results.add(node.productId);
        }
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            collectAllWords(entry.getValue(), results);
        }
    }
}