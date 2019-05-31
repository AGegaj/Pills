package org.unipr.pills.model;

public class PillBoxDataResult {
    private Integer photoId;

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

    private String pillName;

    public PillBoxDataResult(){}

    public PillBoxDataResult(Integer photoId, String pillName) {
        this.photoId = photoId;
        this.pillName = pillName;
    }
}
