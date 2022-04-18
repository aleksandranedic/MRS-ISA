package com.project.team9.model.buissness;

import javax.persistence.*;
import java.util.Date;

@Entity
public class SiteFee {
    @Id
    @SequenceGenerator(
            name = "pricelist_sequence",
            sequenceName = "pricelist_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "pricelist_sequence"
    )
    private Long id;
    private int price;
    private Date startTime;
    private Date endTime;

    public SiteFee() {
    }

    public SiteFee(int price, Date startTime) {
        this.price = price;
        this.startTime = startTime;
    }

    public SiteFee(int price, Date startTime, Date endTime) {
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
