package com.example.amproiect2.video.render;

import com.defano.jsegue.AnimatedSegue;
import com.example.amproiect2.video.FakeVideoRenderProvider;
import com.example.amproiect2.video.effects.EffectFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RenderFactoryTest {

    private Render underTest;

    private List<AnimatedSegue> effects;

    private AnimatedSegue effect;

    @BeforeEach
    void setUp() {
        effects = FakeVideoRenderProvider.getAnimations();
        effect = FakeVideoRenderProvider.getAnimations().get(0);
    }

    @Test
    void couldRenderAnimation() {
        underTest = RenderFactory
                .provideRender(RenderFactory.RENDER_PREVIEW, effect);

        underTest.setOutputFileName("test.mp4");

        try {
            underTest.renderMediaContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        FileInputStream fileInputStream;

        try {
            fileInputStream = new FileInputStream("test.mp4");
            assertThat(fileInputStream.readAllBytes().length).isGreaterThan(0);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void couldRenderMovie() {
        underTest = RenderFactory
                .provideRender(RenderFactory.RENDER_MOVIE, effects);

        underTest.setOutputFileName("test_movie.mp4");

        try {
            underTest.renderMediaContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        FileInputStream fileInputStream;

        try {
            fileInputStream = new FileInputStream("test_movie.mp4");
            assertThat(fileInputStream.readAllBytes().length).isGreaterThan(0);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void couldLoadFile() {
        FileInputStream fileInputStream;
        ByteArrayInputStream byteArrayInputStream;
        try {
            fileInputStream = new FileInputStream("test_movie.mp4");
            byte[] bytes = fileInputStream.readAllBytes();

            assertThat(bytes.length).isGreaterThan(0);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}