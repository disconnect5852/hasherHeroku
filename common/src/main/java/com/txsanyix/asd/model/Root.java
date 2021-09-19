package com.txsanyix.asd.model;

import java.util.List;

public class Root{
    private Job job;
    private List<String> passwords;

    public Root() {
    }

    public Root(Job job, List<String> passwords) {
        this.job = job;
        this.passwords = passwords;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "Root{" +
                "job=" + job +
                ", passwords=" + passwords +
                '}';
    }
}