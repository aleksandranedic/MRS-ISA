package com.project.team9.model.buissness;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Pointlist {
    @Id
    @SequenceGenerator(
            name = "pointlist_sequence",
            sequenceName = "pointlist_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "pointlist_sequence"
    )
    private Long id;
    private int numOfPoints;
    private Date startTime;
    private Date endTime;

    public Pointlist() {
    }

    public Pointlist(int numOfPoints, Date startTime, Date endTime) {
        this.numOfPoints = numOfPoints;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Pointlist(int numOfPoints, Date startTime) {
        this.numOfPoints = numOfPoints;
        this.startTime = startTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumOfPoints() {
        return numOfPoints;
    }

    public void setNumOfPoints(int numOfPoints) {
        this.numOfPoints = numOfPoints;
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
