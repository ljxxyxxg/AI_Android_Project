package com.example.myapplication.model;

public class TrafficEvent {
    private String type;
    private String eventType;
    private String eventDetailType;
    private String startDate;
    private String coordX;
    private String coordY;
    private String linkId;
    private String roadName;
    private String roadNo;
    private String roadDrcType;
    private String lanesBlockType;
    private String lanesBlocked;
    private String message;
    private String endDate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventDetailType() {
        return eventDetailType;
    }

    public void setEventDetailType(String eventDetailType) {
        this.eventDetailType = eventDetailType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCoordX() {
        return coordX;
    }

    public void setCoordX(String coordX) {
        this.coordX = coordX;
    }

    public String getCoordY() {
        return coordY;
    }

    public void setCoordY(String coordY) {
        this.coordY = coordY;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getRoadNo() {
        return roadNo;
    }

    public void setRoadNo(String roadNo) {
        this.roadNo = roadNo;
    }

    public String getRoadDrcType() {
        return roadDrcType;
    }

    public void setRoadDrcType(String roadDrcType) {
        this.roadDrcType = roadDrcType;
    }

    public String getLanesBlockType() {
        return lanesBlockType;
    }

    public void setLanesBlockType(String lanesBlockType) {
        this.lanesBlockType = lanesBlockType;
    }

    public String getLanesBlocked() {
        return lanesBlocked;
    }

    public void setLanesBlocked(String lanesBlocked) {
        this.lanesBlocked = lanesBlocked;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
