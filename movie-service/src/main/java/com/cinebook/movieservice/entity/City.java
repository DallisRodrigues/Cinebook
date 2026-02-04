package com.cinebook.movieservice.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String iconUrl;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<Cinema> cinemas;

    // --- MANUAL GETTERS & SETTERS (To fix your error) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIconUrl() { return iconUrl; }
    public void setIconUrl(String iconUrl) { this.iconUrl = iconUrl; }

    public List<Cinema> getCinemas() { return cinemas; }
    public void setCinemas(List<Cinema> cinemas) { this.cinemas = cinemas; }
}