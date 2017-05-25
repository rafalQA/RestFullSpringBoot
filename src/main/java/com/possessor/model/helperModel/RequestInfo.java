package com.possessor.model.helperModel;

/**
 * Created by rpiotrowicz on 2017-05-24.
 */
public class RequestLimit {
    private int requestNumber = 0;
    private long startTime;
    private long endTime;

    public int getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(int requestNumber) {
        this.requestNumber = requestNumber;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

}
