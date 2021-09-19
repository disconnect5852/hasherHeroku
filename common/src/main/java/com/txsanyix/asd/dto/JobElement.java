package com.txsanyix.asd.dto;

import java.io.Serializable;

public class JobElement implements Serializable {

    private long jobId;
    private String password;
    private String hash;

    public JobElement() {
    }

    public JobElement(long jobId, String password) {
        this.jobId = jobId;
        this.password = password;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "JobElement{" +
                "jobId=" + jobId +
                ", password='" + password + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}
