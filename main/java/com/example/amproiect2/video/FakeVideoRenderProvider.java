package com.example.amproiect2.video;

import com.defano.jsegue.AnimatedSegue;
import com.example.amproiect2.video.effects.Effect;
import com.example.amproiect2.video.effects.EffectFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FakeVideoRenderProvider {
    private static List<BufferedImage> effectsImage = new ArrayList<>();
    private static List<AnimatedSegue> animations = new ArrayList<>();

    static {
        try {
            effectsImage.add(ImageIO.read(new File("C:\\Users\\danit\\OneDrive\\Pictures\\poze Renault\\5.png")));
            effectsImage.add(ImageIO.read(new File("C:\\Users\\danit\\OneDrive\\Pictures\\poze Renault\\6.png")));
            effectsImage.add(ImageIO.read(new File("C:\\Users\\danit\\OneDrive\\Pictures\\poze Renault\\5.png")));
            effectsImage.add(ImageIO.read(new File("C:\\Users\\danit\\OneDrive\\Pictures\\poze Renault\\6.png")));

            Effect effect = new Effect(
                    effectsImage.get(0),
                    effectsImage.get(1),
                    EffectFactory.ScrollRightEffect
            );
            Effect effect1 = new Effect(
                    effectsImage.get(1),
                    effectsImage.get(2),
                    EffectFactory.ScrollLeftEffect
            );

            Effect effect2 = new Effect(
                    effectsImage.get(2),
                    effectsImage.get(3),
                    EffectFactory.AlphaDissolveEffect
            );

            animations.add(EffectFactory.provideEffect(effect));
            animations.add(EffectFactory.provideEffect(effect1));
            animations.add(EffectFactory.provideEffect(effect2));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static List<AnimatedSegue> getAnimations() {
        return animations;
    }
}
