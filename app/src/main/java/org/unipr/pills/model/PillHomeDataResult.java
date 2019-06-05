package org.unipr.pills.model;

import java.util.Date;

public class PillHomeDataResult {

    private Integer id;

    private Date start;

    private String duration;

    private String frequency;

    private Integer photoId;

    private String pillName;

    private String time;

    public PillHomeDataResult(){}

    public PillHomeDataResult(Integer id, Date start, String duration, String frequency,
                              Integer photoId, String pillName, String time) {
        this.id = id;
        this.start = start;
        this.duration = duration;
        this.frequency = frequency;
        this.photoId = photoId;
        this.pillName = pillName;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    public String getPillName() {
        return pillName;
    }

    public void setPillName(String pillName) {
        this.pillName = pillName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
