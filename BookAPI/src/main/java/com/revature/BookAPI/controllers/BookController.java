package com.revature.BookAPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/books")
@CrossOrigin
public class BookController {
        @Autowired
        private CircuitBreakerFactory<?,?> circuitBreakerFactory;

        //Method that doesn't interact with any other aPI
        @GetMapping
        public ResponseEntity<?> getAllBooks() {
                String[] books = {
                        "To Kill a Mockingbird",
                        "1984",
                        "The Great Gatsby",
                        "Pride and Prejudice",
                        "The Catcher in the Rye",
                        "Moby Dick",
                        "The Lord of the Rings",
                        "Harry Potter and the Sorcerer's Stone",
                        "The Chronicles of Narnia",
                        "Brave New World"
                };
                return ResponseEntity.ok(books);
        }

        // This method will utilize a circuit breaker
        //Method that uses RestTemplate to consumer another API
        @GetMapping("/recs")
        public ResponseEntity<?> getBookRecs() {
                return circuitBreakerFactory.create("recsAPI").run(
                        () -> {
                        RestTemplate restTemplate = new RestTemplate();
                        String[] recs = restTemplate.getForObject("http://localhost:8081/recs", String[].class);
                        return ResponseEntity.ok(recs);
                        }, throwable -> getRecsFallback(throwable)
                );
        }
        //Method that will be used if the recs API is down
        public ResponseEntity<?> getRecsFallback(Throwable throwable) {
                System.out.println("Fallback method called due to exception: " + throwable.getMessage());
                return ResponseEntity.ok("Sorry couldn't get Recommendations, please try again later");
        }
}
