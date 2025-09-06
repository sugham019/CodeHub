package com.example.backend.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;


// A simple POJO for Problem
class Problem {
    private String slug;
    private String title;
    private String description;
    private String inputFormat;
    private String sampleInput;
    private String outputFormat;
    private String sampleOutput;
    private String constraints;

    // constructor
    public Problem(String slug, String title, String description,
                   String inputFormat, String sampleInput,
                   String outputFormat, String sampleOutput,
                   String constraints) {
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.inputFormat = inputFormat;
        this.sampleInput = sampleInput;
        this.outputFormat = outputFormat;
        this.sampleOutput = sampleOutput;
        this.constraints = constraints;
    }

    // getters
    public String getSlug() { return slug; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getInputFormat() { return inputFormat; }
    public String getSampleInput() { return sampleInput; }
    public String getOutputFormat() { return outputFormat; }
    public String getSampleOutput() { return sampleOutput; }
    public String getConstraints() { return constraints; }
}

class TestCaseResult {
    private String status;
    private double time;
    private int memory;

    public TestCaseResult(String status, double time, int memory) {
        this.status = status;
        this.time = time;
        this.memory = memory;
    }

    public String getStatus() { return status; }
    public double getTime() { return time; }
    public int getMemory() { return memory; }
}



@Controller
public class ProblemViewController {


    @GetMapping("/problem/{slug}")
    public String editor(@PathVariable String slug, Model model) {
        // Dummy problem info for "Reverse a String"
        Problem problem = new Problem(
                slug,
                "Reverse a String",
                "Given a string, return the reversed string.",
                "A single line containing the string to reverse.",
                "hello world",
                "The reversed string",
                "dlrow olleh",
                "1 <= length of string <= 1000"
        );

        // Dummy test case results (normally populated after submission)
        List<TestCaseResult> testCaseResults = Arrays.asList(
                new TestCaseResult("Accepted", 0.010, 512),
                new TestCaseResult("Accepted", 0.012, 520)
        );

        // Add attributes for Thymeleaf
        model.addAttribute("title", problem.getTitle());
        model.addAttribute("problem", problem);
        model.addAttribute("testCaseResults", testCaseResults); // updated camelCase for template

        // Example error message (could be null if no error)
        model.addAttribute("error", null);

        return "submit_solution"; // Thymeleaf template name
    }

    @GetMapping("/problems")
    public String problems(Model model) {
        // Dummy list of problems
        class User {
            private String username;
            private int points;
            public User(String username, int points) { this.username = username; this.points = points; }
            public String getUsername() { return username; }
            public int getPoints() { return points; }
        }

        class ProblemSummary {
            private String slug;
            private String title;
            private String difficulty;
            private int points;
            private double successRate;

            public ProblemSummary(String slug, String title, String difficulty, int points, double successRate) {
                this.slug = slug;
                this.title = title;
                this.difficulty = difficulty;
                this.points = points;
                this.successRate = successRate;
            }

            public String getSlug() { return slug; }
            public String getTitle() { return title; }
            public String getDifficulty() { return difficulty; }
            public int getPoints() { return points; }
            public double getSuccessRate() { return successRate; }
        }

        List<ProblemSummary> problems = Arrays.asList(
                new ProblemSummary("reverse-string", "Reverse a String", "Easy", 10, 96.5),
                new ProblemSummary("palindrome-string", "Given a string, determine if it is a palindrome.", "Easy", 10, 99.6),
                new ProblemSummary("palindrome-int", "Given an integer, determine if it is a palindrome.", "Easy", 10, 99.6),
                new ProblemSummary("rev-linkedlist", "Reverse a Linked List", "Medium", 20, 82.6),
                new ProblemSummary("det-linkedlist", "Determine if a linked list has a cycle.", "Medium", 20, 82.6),
                new ProblemSummary("buble-sort", "Sort an array using Merge Sort", "Medium", 20, 82.6),
                new ProblemSummary("det-sort", "Remove duplicates from a sorted array in-place and return the new length", "Medium", 20, 82.6)
        );

        // Dummy top users
        List<User> topUsers = Arrays.asList(
                new User("Soham", 130),
                new User("Sugham", 130),
                new User("Sambhav", 130),
                new User("Hari", 0)
        );

        // Add attributes for Thymeleaf
        model.addAttribute("problems", problems);
        model.addAttribute("topUsers", topUsers);
        model.addAttribute("title", "All Problems");
        String currentUser = "Soham";

        model.addAttribute("currentUser", currentUser); //
        return "question"; // Thymeleaf template name
    }
}
