package com.revature.RecommendationAPI.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recs")
@CrossOrigin
public class RecController {

    @GetMapping
    public ResponseEntity<String[]> getRecs() {
        String[] books = {
                "The Alchemist",
                "Sapiens: A Brief History of Humankind",
                "Atomic Habits",
                "The Subtle Art of Not Giving a F*ck",
                "The Hobbit",
                "A Song of Ice and Fire",
                "Thinking, Fast and Slow",
                "The Art of War",
                "Rich Dad Poor Dad",
                "Meditations"
        };
        return ResponseEntity.ok(books);
    }
}
