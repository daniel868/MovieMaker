package com.example.amproiect2.entities;

public class VideoRenderDto {
    private String effectType;
    private int sourceIndex;
    private int destinationIndex;

    public VideoRenderDto(String effectType, int sourceIndex, int destinationIndex) {
        this.effectType = effectType;
        this.sourceIndex = sourceIndex;
        this.destinationIndex = destinationIndex;
    }

    public String getEffectType() {
        return effectType;
    }

    public Integer getSourceIndex() {
        return sourceIndex;
    }

    public Integer getDestinationIndex() {
        return destinationIndex;
    }

    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }

    public void setSourceIndex(int sourceIndex) {
        this.sourceIndex = sourceIndex;
    }

    public void setDestinationIndex(int destinationIndex) {
        this.destinationIndex = destinationIndex;
    }

    @Override
    public String toString() {
        return "VideoRenderDto{" +
                "effectType='" + effectType + '\'' +
                ", sourceIndex=" + sourceIndex +
                ", destinationIndex=" + destinationIndex +
                '}';
    }
}
