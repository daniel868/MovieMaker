package com.example.amproiect2.entities;

public class VideoRenderDto {
    private final String effectType;
    private final int sourceIndex;
    private final int destinationIndex;

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


    @Override
    public String toString() {
        return "VideoRenderDto{" +
                "effectType='" + effectType + '\'' +
                ", sourceIndex=" + sourceIndex +
                ", destinationIndex=" + destinationIndex +
                '}';
    }
}
