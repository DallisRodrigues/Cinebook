package com.cinebook.movieservice.service;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class TmdbService {

    private final WebClient webClient;

    @Value("${tmdb.api.key}")
    private String apiKey;

    // --- 🧠 SMART CACHE MEMORY ---
    // This variable holds the Real JSON even if the internet disconnects later
    private String lastRealData = null; 

    public TmdbService(@Value("${tmdb.api.base-url}") String baseUrl) {
        // --- ⏱️ TIMEOUT CONFIGURATION (5 Seconds) ---
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofSeconds(5))
                .doOnConnected(conn -> 
                        conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                            .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    // --- 🚀 AUTO-STARTUP FETCH ---
    @PostConstruct
    public void seedDataOnStartup() {
        System.out.println("🌱 Startup: Attempting to fetch Real Movie Data...");
        try {
            getNowPlayingMovies(); 
        } catch (Exception e) {
            System.out.println("⚠️ Startup fetch failed (Internet might be down). Will try again on user request.");
        }
    }

    // 1. Get "Now Showing" (With Smart Fallback)
    @Cacheable(value = "nowPlaying", key = "'india'") 
    public String getNowPlayingMovies() {
        try {
            System.out.println("🌍 Connecting to TMDB API...");
            String response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/movie/now_playing")
                            .queryParam("api_key", apiKey)
                            .queryParam("region", "IN")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            
            // ✅ SUCCESS! Save this real data to our memory variable
            if (response != null && !response.isEmpty()) {
                this.lastRealData = response;
                System.out.println("✅ TMDB Success! Data cached in memory.");
            }
            return response;

        } catch (Exception e) {
            System.err.println("⚠️ TMDB Unreachable (" + e.getMessage() + ")");
            
            // 🛡️ FALLBACK STRATEGY
            if (this.lastRealData != null) {
                System.out.println("♻️ Network failed, but using SAVED REAL DATA from memory.");
                return this.lastRealData; // <--- Return the old real movies!
            }
            
            System.err.println("❌ No real data found. Falling back to MOCK DATA.");
            return MOCK_NOW_PLAYING; // Last resort
        }
    }

    // 2. Get Details (With Fallback)
    @Cacheable(value = "movieDetails", key = "#movieId")
    public String getMovieDetails(String movieId) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/movie/" + movieId)
                            .queryParam("api_key", apiKey)
                            .queryParam("append_to_response", "credits,videos,release_dates,images")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            System.err.println("⚠️ TMDB Details Failed. Using Mock.");
            return MOCK_DETAILS;
        }
    }

    // 3. Get Trailer (SMARTER VERSION)
    // Prioritizes "Trailer", but accepts "Teaser" if no trailer is found.
    public String getMovieTrailer(String movieId) {
        try {
            // Fetch videos for the movie
            String response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/movie/" + movieId + "/videos")
                            .queryParam("api_key", apiKey)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Parse JSON 
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            JsonNode results = root.path("results");

            String bestVideoKey = null;

            if (results.isArray()) {
                for (JsonNode node : results) {
                    String type = node.path("type").asText();
                    String site = node.path("site").asText();

                    // Ensure it is a YouTube video
                    if ("YouTube".equalsIgnoreCase(site)) {
                        
                        // Priority 1: Official Trailer (Return immediately if found)
                        if ("Trailer".equalsIgnoreCase(type)) {
                            return node.path("key").asText(); 
                        }
                        
                        // Priority 2: Teaser (Save it, only use if no trailer is found later)
                        if ("Teaser".equalsIgnoreCase(type) && bestVideoKey == null) {
                            bestVideoKey = node.path("key").asText();
                        }
                    }
                }
            }
            // Return whatever we found (Trailer was already returned, so this is likely a Teaser or null)
            return bestVideoKey;

        } catch (Exception e) {
            System.err.println("⚠️ Failed to fetch trailer: " + e.getMessage());
        }
        return null; // Return null if nothing found
    }

    // --- MOCK DATA ---
    private static final String MOCK_NOW_PLAYING = """
    {
        "results": [
            { "id": 550, "title": "Fight Club", "poster_path": "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg", "vote_average": 8.4, "release_date": "1999-10-15" },
            { "id": 157336, "title": "Interstellar", "poster_path": "/gEU2QniL6E8ahDgTSamDbFicAuD.jpg", "vote_average": 8.6, "release_date": "2014-11-05" },
            { "id": 299534, "title": "Avengers: Endgame", "poster_path": "/or06FN3Dka5tukK1e9sl16pB3iy.jpg", "vote_average": 8.3, "release_date": "2019-04-24" }
        ]
    }
    """;

    private static final String MOCK_DETAILS = """
    {
        "id": 550, "title": "Fight Club (Mock Mode)", "overview": "An insomniac office worker and a devil-may-care soapmaker form an underground fight club...",
        "poster_path": "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg", "backdrop_path": "/hZkgoQYus5vegHoetLkCJzb17zJ.jpg",
        "runtime": 139, "vote_average": 8.4,
        "release_dates": { "results": [{ "iso_3166_1": "IN", "release_dates": [{ "certification": "A" }] }] },
        "credits": { "cast": [{ "name": "Edward Norton" }, { "name": "Brad Pitt" }] },
        "videos": { "results": [{ "key": "6JnN1DmbqoU", "type": "Trailer" }] }
    }
    """;
}