package com.project.team9.model.buissness;

import javax.persistence.*;

@Entity
public class UserCategory {
    @Id
    @SequenceGenerator(
            name = "user_category_sequence",
            sequenceName = "user_category_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_category_sequence"
    )
    private Long id;
    private String name;
    private int minimumPoints;
    private int maximumPoints;
    private int priceAlteration;

    public UserCategory() {
    }

    public UserCategory(String name, int minimumPoints, int maximumPoints, int priceAlteration) {
        this.name = name;
        this.minimumPoints = minimumPoints;
        this.maximumPoints = maximumPoints;
        this.priceAlteration = priceAlteration;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinimumPoints() {
        return minimumPoints;
    }

    public void setMinimumPoints(int minimumPoints) {
        this.minimumPoints = minimumPoints;
    }

    public int getMaximumPoints() {
        return maximumPoints;
    }

    public void setMaximumPoints(int maximumPoints) {
        this.maximumPoints = maximumPoints;
    }

    public int getPriceAlteration() {
        return priceAlteration;
    }

    public void setPriceAlteration(int priceAlteration) {
        this.priceAlteration = priceAlteration;
    }
}
