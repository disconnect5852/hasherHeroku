package com.txsanyix.asd.model;

import java.util.Map;

public class PutRoot {
    private Map<String,String> passwordHashes;

    public PutRoot() {
    }

    public PutRoot(Map<String, String> passwordHashes) {
        this.passwordHashes = passwordHashes;
    }

    @Override
    public String toString() {
        return "PutRoot{" +
                "passwordHashes=" + passwordHashes +
                '}';
    }

    public Map<String, String> getPasswordHashes() {
        return passwordHashes;
    }

    public void setPasswordHashes(Map<String, String> passwordHashes) {
        this.passwordHashes = passwordHashes;
    }
}
