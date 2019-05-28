package model;

import java.util.Date;

public class PillRegister {

    private String pillName;

    private Integer photoId;

    private Date start;

    private String duration;

    private String frequency;

    private String reminder;

    private String status;

    public PillRegister(){}

    public PillRegister(String pillName, Integer photoId, Date start, String duration, String frequency, String reminder, String status) {
        this.pillName = pillName;
        this.photoId = photoId;
        this.start = start;
        this.duration = duration;
        this.frequency = frequency;
        this.reminder = reminder;
        this.status = status;
    }

    public String getPillName() {
        return pillName;
    }

    public void setPillName(String pillName) {
        this.pillName = pillName;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
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

    public String getStatus() {
        return status;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
