package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	DemoApplication() {
		// Constructor
		System.out.println("DemoApplication instance created.");


		List<String> products = new ArrayList<>();
		products.add("Laptop");
		products.add("Phone");
		products.get(0); // O(1) - fast random access
		products.set(1, "Tablet"); // O(1) - fast updates


		LinkedList<String> playlist = new LinkedList<>();
		playlist.addFirst("Song 1"); // O(1) - efficient head insertion
		playlist.addLast("Song 2");  // O(1) - efficient tail insertion
		playlist.removeFirst();      // O(1) - efficient head removal


		Set<Integer> likes = new HashSet<>();
		likes.add(101);
		likes.add(205);
		likes.add(101); // Duplicate ignored
		// Iteration order: unpredictable


		Map<String, User> sessions = new HashMap<>();
		sessions.put("session123", new User("Alice"));
		sessions.put("session456", new User("Bob"));
		// Fast lookups, no ordering


		// LRU Cache implementation
		LinkedHashMap<String, Integer> lru = new LinkedHashMap<>(16, 0.75f, true) {
			protected boolean removeEldestEntry(Map.Entry<String, Integer> e) {
				return size() > 100; // Max 100 entries
			}
		};
		lru.put("key1", 1);
		lru.get("key1"); // Moves to end (most recently used)


		Map<String, Integer> scores = new TreeMap<>();
		scores.put("Charlie", 85);
		scores.put("Alice", 92);
		scores.put("Bob", 78);
		// Iteration order: Alice, Bob, Charlie (alphabetical)



		ConcurrentMap<String, Integer> cache = new ConcurrentHashMap<>();
		// Safe for concurrent access from multiple threads
		cache.put("counter", 1);
		cache.compute("counter", (k, v) -> v == null ? 1 : v + 1);



		Queue<String> q = new LinkedList<>();
		q.offer("Task 1"); // Add to rear
		q.offer("Task 2");
		String next = q.poll(); // Remove from front - "Task 1"



		Deque<String> undo = new ArrayDeque<>();
		undo.push("Action 1");    // Add to front (stack-like)
		undo.addLast("Action 2"); // Add to rear (queue-like)
		String last = undo.pop(); // Remove from front - "Action 1"

	}

}


class User {
	private String name;

	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "User{name='" + name + "'}";
	}
}