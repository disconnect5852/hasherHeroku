package com.txsanyix.asd.model;

import java.util.List;
import java.util.Map;

public class Job{
    private long id;
    private String applicant;
    private List<String> passwords;
    private long startTimeMillis;
    private long finishTimeMillis;
    private long runtimeMillis;
    private Map<String,String> result;

    public Job() {
    }

    public Job(long id, String applicant, List<String> passwords, long startTimeMillis, long finishTimeMillis, long runtimeMillis, Map<String, String> result) {
        this.id = id;
        this.applicant = applicant;
        this.passwords = passwords;
        this.startTimeMillis = startTimeMillis;
        this.finishTimeMillis = finishTimeMillis;
        this.runtimeMillis = runtimeMillis;
        this.result = result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public List<String> getPasswords() {
        return passwords;
    }

    public void setPasswords(List<String> passwords) {
        this.passwords = passwords;
    }

    public long getStartTimeMillis() {
        return startTimeMillis;
    }

    public void setStartTimeMillis(long startTimeMillis) {
        this.startTimeMillis = startTimeMillis;
    }

    public long getFinishTimeMillis() {
        return finishTimeMillis;
    }

    public void setFinishTimeMillis(long finishTimeMillis) {
        this.finishTimeMillis = finishTimeMillis;
    }

    public long getRuntimeMillis() {
        return runtimeMillis;
    }

    public void setRuntimeMillis(long runtimeMillis) {
        this.runtimeMillis = runtimeMillis;
    }

    public Map<String, String> getResult() {
        return result;
    }

    public void setResult(Map<String, String> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", applicant='" + applicant + '\'' +
                ", passwords=" + passwords +
                ", startTimeMillis=" + startTimeMillis +
                ", finishTimeMillis=" + finishTimeMillis +
                ", runtimeMillis=" + runtimeMillis +
                ", result=" + result +
                '}';
    }
}
