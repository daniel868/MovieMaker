package com.example.amproiect2.servicies;

import com.defano.jsegue.AnimatedSegue;
import com.example.amproiect2.datasource.MediaDatasource;
import com.example.amproiect2.entities.VideoRenderDto;
import com.example.amproiect2.utils.JsonParser;
import com.example.amproiect2.video.render.RenderPreview;
import com.example.amproiect2.video.render.RenderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class VideoServiceTest {

    private VideoService underTest;

    @Autowired
    private MediaDatasource mediaDatasource;

    private VideoRenderDto videoRenderDto;


    @BeforeEach
    void setUp() {

    }

    @Test
    void couldRenderEffect() throws Exception {
        List<AnimatedSegue> tasks = new ArrayList<>();
        JsonParser.readFromJsonFile("render.json", tasks);

//        RenderPreview render = RenderFactory.provideRender(RenderFactory.RENDER_MOVIE, tasks)
//                .setOutputFileName("MediaRender.mp4");
//
//        render.renderMediaContent();

    }
}