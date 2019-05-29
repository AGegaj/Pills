package model;

import java.util.Date;

public class PillDataResult {
    private Integer id;

    private Integer photoId;

    private String pillName;

    private Date start;

    private String frequency;

    private String reminderTimes;

    public PillDataResult(Integer id, Integer photoId, String pillName, Date start, String frequency, String reminderTimes) {
        this.id = id;
        this.photoId = photoId;
        this.pillName = pillName;
        this.start = start;
        this.frequency = frequency;
        this.reminderTimes = reminderTimes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PillDataResult(){}

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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getReminderTimes() {
        return reminderTimes;
    }

    public void setReminderTimes(String reminderTimes) {
        this.reminderTimes = reminderTimes;
    }
}
