package com.example.lesson32flagquiz.Models;

public class FlagInfo {
    private int flagImage;
    private String flagName;

    public FlagInfo(int flagImage, String flagName) {
        this.flagImage = flagImage;
        this.flagName = flagName;
    }

    public int getFlagImage() {
        return flagImage;
    }

    public void setFlagImage(int flagImage) {
        this.flagImage = flagImage;
    }

    public String getFlagName() {
        return flagName;
    }

    public void setFlagName(String flagName) {
        this.flagName = flagName;
    }
}
