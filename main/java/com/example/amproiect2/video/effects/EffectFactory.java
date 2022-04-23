package com.example.amproiect2.video.effects;

import com.defano.jsegue.AnimatedSegue;
import com.defano.jsegue.SegueBuilder;
import com.defano.jsegue.renderers.*;

import java.util.concurrent.TimeUnit;

public class EffectFactory {
    public final static String AlphaDissolveEffect = "AlphaDissolveEffect";
    public final static String PixelDissolveEffect = "PixelDissolveEffect";
    public final static String ScrollLeftEffect = "ScrollLeftEffect";
    public final static String ScrollRightEffect = "ScrollRightEffect";

    private static SegueBuilder createEffect(Effect requestedEffect, Class<? extends AnimatedSegue> clazz) {

        return SegueBuilder.of(clazz)
                .withSource(requestedEffect.getSource())
                .withDestination(requestedEffect.getDestination())
                .withDuration(requestedEffect.getEffectDuration(),
                        TimeUnit.MILLISECONDS)
                .withMaxFramesPerSecond(30);
    }

    public static AnimatedSegue provideEffect(Effect requestedEffect) {
        switch (requestedEffect.getEffectType()) {
            case AlphaDissolveEffect:
                return createEffect(
                        requestedEffect,
                        AlphaDissolveEffect.class)
                        .build();

            case PixelDissolveEffect:
                return createEffect(
                        requestedEffect,
                        PixelDissolveEffect.class)
                        .build();

            case ScrollLeftEffect:
                return createEffect(
                        requestedEffect,
                        ScrollLeftEffect.class)
                        .build();

            case ScrollRightEffect:
                return createEffect(
                        requestedEffect,
                        ScrollRightEffect.class)
                        .build();
            default:
                return SegueBuilder.of(PlainEffect.class)
                        .withSource(requestedEffect.getSource())
                        .withDestination(requestedEffect.getDestination())
                        .withDuration(requestedEffect.getEffectDuration(),
                                TimeUnit.MILLISECONDS)
                        .withMaxFramesPerSecond(30)
                        .build();
        }
    }
}
