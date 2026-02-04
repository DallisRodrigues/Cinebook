package com.cinebook.movieservice.service;

import com.cinebook.movieservice.entity.Seat;
import com.cinebook.movieservice.repository.SeatRepository;
import com.microsoft.playwright.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BmsScraperService {

    @Autowired
    private SeatRepository seatRepo;

    // Run in background so the UI doesn't freeze
    @Async
    public void scrapeSeatLayout(String bmsShowURL, Long localMovieId) {
        System.out.println("🕵️ Starting Scraper for: " + bmsShowURL);

        try (Playwright playwright = Playwright.create()) {
            // Launch invisible browser
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            Page page = browser.newPage();

            // 1. SET UP THE TRAP (Network Interception)
            // We listen for any response that looks like seat data
            page.onRequestFinished(request -> {
                String url = request.url();
                // BMS often puts seat data in 'sys_seatLayout' or 'render' endpoints
                if (url.contains("sys_seatLayout") || url.contains("seatLayout")) {
                    System.out.println("🎯 CAUGHT SEAT DATA: " + url);
                    try {
                        String jsonResponse = request.response().text();
                        parseAndSaveSeats(jsonResponse, localMovieId);
                    } catch (Exception e) {
                        System.err.println("❌ Failed to read response: " + e.getMessage());
                    }
                }
            });

            // 2. NAVIGATE TO THE PAGE
            System.out.println("🌍 Navigating to BMS...");
            page.navigate(bmsShowURL);

            // 3. TRIGGER DATA LOAD
            // We wait and try to click "Book" or "Select Seats" to force the API call
            try {
                // Wait for the page to load
                page.waitForTimeout(5000);
                
                // Try to find the button (Selectors might change, this is a guess)
                if (page.isVisible("button:has-text('Book')")) {
                    page.click("button:has-text('Book')");
                    System.out.println("👆 Clicked 'Book' button");
                } else if (page.isVisible("div.seat-layout-cta")) { // Example selector
                    page.click("div.seat-layout-cta");
                }
                
                // Keep browser open for 10s to ensure we catch the network response
                page.waitForTimeout(10000); 
                
            } catch (Exception e) {
                System.out.println("⚠️ Could not click button, but checking if data loaded anyway...");
            }

            browser.close();
            System.out.println("✅ Scraper Session Finished.");

        } catch (Exception e) {
            System.err.println("🔥 Critical Scraper Error: " + e.getMessage());
        }
    }

    private void parseAndSaveSeats(String json, Long movieId) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            
            // NOTE: This structure depends entirely on BMS's current API response
            // You will see the REAL JSON in your console log. 
            // Adjust this logic to match what you see!
            
            // Example Logic:
            // JsonNode rows = root.path("seatLayout").path("rows");
            // if (rows.isArray()) {
            //     for (JsonNode row : rows) {
            //         String rowName = row.path("rowCode").asText();
            //         for (JsonNode seatNode : row.path("seats")) {
            //             Seat s = new Seat();
            //             s.setSeatNumber(rowName + seatNode.path("seatId").asText());
            //             s.setMovieId(movieId);
            //             s.setPrice(350.00); // Default or extract from JSON
            //             s.setStatus("AVAILABLE"); // Parse status logic
            //             seatRepo.save(s);
            //         }
            //     }
            // }
            
            System.out.println("💾 (Simulation) Saved seats from JSON to Database for Movie ID: " + movieId);
            // System.out.println("   JSON Preview: " + json.substring(0, Math.min(json.length(), 200)) + "...");

        } catch (Exception e) {
            System.err.println("❌ Parsing Error: " + e.getMessage());
        }
    }
}