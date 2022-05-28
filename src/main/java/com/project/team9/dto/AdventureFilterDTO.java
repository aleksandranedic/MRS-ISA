package com.project.team9.dto;

import java.util.ArrayList;

public class AdventureFilterDTO {
    private boolean adventuresChecked;
    private String numberOfClients;
    private String fishingInstructorName;
    private ArrayList<Integer> priceRange;

    public AdventureFilterDTO() {
    }

    public AdventureFilterDTO(boolean adventuresChecked, String numberOfClients, String fishingInstructorName, ArrayList<Integer> priceRange) {
        this.adventuresChecked = adventuresChecked;
        this.numberOfClients = numberOfClients;
        this.fishingInstructorName = fishingInstructorName;
        this.priceRange = priceRange;
    }

    public boolean isAdventuresChecked() {
        return adventuresChecked;
    }

    public void setAdventuresChecked(boolean adventuresChecked) {
        this.adventuresChecked = adventuresChecked;
    }

    public String getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(String numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public String getFishingInstructorName() {
        return fishingInstructorName;
    }

    public void setFishingInstructorName(String fishingInstructorName) {
        this.fishingInstructorName = fishingInstructorName;
    }

    public ArrayList<Integer> getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(ArrayList<Integer> priceRange) {
        this.priceRange = priceRange;
    }
}
