package com.cinebook.movieservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TmdbService {

    private final WebClient webClient;

    @Value("${tmdb.api.key}")
    private String apiKey;

    public TmdbService(@Value("${tmdb.api.base-url}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    // 1. Get "Now Showing" (With Fallback)
    @Cacheable(value = "nowPlaying", key = "'india'") 
    public String getNowPlayingMovies() {
        try {
            System.out.println("🌍 Connecting to TMDB API...");
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/movie/now_playing")
                            .queryParam("api_key", apiKey)
                            .queryParam("region", "IN")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            System.err.println("⚠️ TMDB Unreachable (" + e.getMessage() + "). Using Mock Data.");
            return MOCK_NOW_PLAYING; // Return fake JSON so frontend loads!
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

    // --- MOCK DATA (Saved my life during demos!) ---
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