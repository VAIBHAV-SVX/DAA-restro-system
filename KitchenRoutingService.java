package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class KitchenRoutingService {

    // Representation of a graph node neighbor (Station, Distance)
    public static class Node implements Comparable<Node> {
        public int id;
        public int weight;

        public Node(int id, int weight) {
            this.id = id;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    // Core Unit 3 Dijkstra Implementation from Syllabus
    public int[] calculateShortestPaths(int totalStations, List<List<Node>> adjList, int sourceStation) {
        int[] distances = new int[totalStations];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[sourceStation] = 0;

        PriorityQueue<Node> minHeap = new PriorityQueue<>();
        minHeap.add(new Node(sourceStation, 0));

        boolean[] visited = new boolean[totalStations];

        while (!minHeap.isEmpty()) {
            int u = minHeap.poll().id;

            if (visited[u]) continue;
            visited[u] = true;

            for (Node neighbor : adjList.get(u)) {
                int v = neighbor.id;
                int weight = neighbor.weight;

                // Relaxation Step
                if (!visited[v] && distances[u] != Integer.MAX_VALUE && distances[u] + weight < distances[v]) {
                    distances[v] = distances[u] + weight;
                    minHeap.add(new Node(v, distances[v]));
                }
            }
        }
        return distances;
    }
}