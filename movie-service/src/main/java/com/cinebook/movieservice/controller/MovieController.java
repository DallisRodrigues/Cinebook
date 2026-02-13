package com.cinebook.movieservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; // <--- Import for better HTTP responses
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cinebook.movieservice.entity.Seat;
import com.cinebook.movieservice.repository.SeatRepository;
import com.cinebook.movieservice.service.BmsScraperService;
import com.cinebook.movieservice.service.MovieService;
import com.cinebook.movieservice.service.StripeService;
import com.cinebook.movieservice.service.TmdbService;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private TmdbService tmdbService;
    
    @Autowired
    private MovieService movieService;

    @Autowired
    private BmsScraperService scraperService;

    @Autowired
    private SeatRepository seatRepo;
    
    @Autowired
    private StripeService stripeService;

    // 1. Get Live "Now Playing" Data (Proxy to TMDB)
    @GetMapping("/now-playing")
    public String getNowPlaying() {
        return tmdbService.getNowPlayingMovies();
    }

    // 2. Get Movie Details (Proxy to TMDB)
    @GetMapping("/{movieId}/details")
    public String getDetails(@PathVariable String movieId) {
        return tmdbService.getMovieDetails(movieId);
    }

    // 3. Get Seats (Internal DB)
    @GetMapping("/{movieId}/seats")
    public List<Seat> getSeats(@PathVariable Long movieId, @RequestParam(defaultValue = "Unknown Movie") String title) {
        // Lazy Init: Create seats if they don't exist
        movieService.ensureMovieExists(movieId, title);
        return movieService.getSeats(movieId);
    }

    // 4. Book a Seat (Single)
    @PutMapping("/seats/{seatId}/book")
    public boolean bookSeat(@PathVariable Long seatId) {
        return movieService.bookSeat(seatId);
    }
    
    // 5. Book Multiple Seats
    @PostMapping("/seats/book-multiple")
    public boolean bookMultipleSeats(@RequestBody List<Long> seatIds, @RequestParam Long userId) {
        return movieService.bookSeats(seatIds, userId);
    }

    // 6. Trigger Scraper
    @PostMapping("/scrape")
    public String triggerScraper(@RequestParam String url, @RequestParam Long movieId) {
        scraperService.scrapeSeatLayout(url, movieId);
        return "🕵️ Scraper started! Watch the CONSOLE for the hidden JSON data.";
    }

    // --- 🆕 7. GET MOVIE TRAILER (The Missing Link) ---
    @GetMapping("/{id}/trailer")
    public ResponseEntity<String> getMovieTrailer(@PathVariable String id) {
        // We call tmdbService directly now!
        String trailerKey = tmdbService.getMovieTrailer(id);
        
        if (trailerKey != null) {
            return ResponseEntity.ok(trailerKey);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 8. Payment Session
    @PostMapping("/payment/create-session")
    public String createPaymentSession(@RequestParam double amount) {
        return stripeService.createCheckoutSession(amount);
    }

    // 9. Load JSON Data
    @PostMapping("/load-seats")
    public String loadSeatData(@RequestBody Map<String, Object> seatData) {
        try {
            Map<String, Object> layout = (Map<String, Object>) seatData.get("seatLayout");
            List<Map<String, Object>> rows = (List<Map<String, Object>>) layout.get("rows");
            int seatsAdded = 0;
            
            for (Map<String, Object> row : rows) {
                List<Map<String, Object>> seats = (List<Map<String, Object>>) row.get("seats");
                for (Map<String, Object> seatObj : seats) {
                    Seat seat = new Seat();
                    seat.setSeatNumber((String) seatObj.get("id"));
                    String status = (String) seatObj.get("status");
                    seat.setStatus(status != null && status.equalsIgnoreCase("AVAILABLE") ? "AVAILABLE" : "BOOKED");
                    seat.setMovieId(1L); 
                    seatRepo.save(seat);
                    seatsAdded++;
                }
            }
            return "✅ SUCCESS: Loaded " + seatsAdded + " seats into the database!";
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ ERROR: Could not process JSON. Check console.";
        }
    }
}