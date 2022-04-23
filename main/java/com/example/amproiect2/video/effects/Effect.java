package com.example.amproiect2.video.effects;

import java.awt.image.BufferedImage;

public class Effect {
    private final static int DEFAULT_EFFECT_DURATION = 2000;

    private BufferedImage source, destination;
    private int effectDuration = DEFAULT_EFFECT_DURATION;
    private String effectType;

    public Effect(BufferedImage source, BufferedImage destination, String effectType) {
        this.source = source;
        this.destination = destination;
        this.effectType = effectType;
    }

    public BufferedImage getSource() {
        return source;
    }

    public void setSource(BufferedImage source) {
        this.source = source;
    }

    public BufferedImage getDestination() {
        return destination;
    }

    public void setDestination(BufferedImage destination) {
        this.destination = destination;
    }

    public int getEffectDuration() {
        return effectDuration;
    }

    public void setEffectDuration(int effectDuration) {
        this.effectDuration = effectDuration;
    }

    public String getEffectType() {
        return effectType;
    }

    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }
}
