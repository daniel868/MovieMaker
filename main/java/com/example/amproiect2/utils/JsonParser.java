package com.example.amproiect2.utils;

import com.defano.jsegue.AnimatedSegue;
import com.example.amproiect2.video.effects.Effect;
import com.example.amproiect2.video.effects.EffectFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class JsonParser {
    public static void readFromJsonFile(String filePath, List<AnimatedSegue> renderList) {
        JSONParser parser = new JSONParser();

        try {
            FileReader fileReader = new FileReader(filePath);

            Object object = parser.parse(fileReader);

            JSONArray jsonArray = (JSONArray) object;

            System.out.println(jsonArray);

            jsonArray.forEach(render -> {
                try {
                    renderList.add(parseRenderObject((JSONObject) render));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.getMessage();
        }
    }

    private static AnimatedSegue parseRenderObject(JSONObject render) throws Exception {
        BufferedImage sourceImage, destinationImage;

        JSONObject renderObject = (JSONObject) render.get("videoRenderDto");

        String effectType = renderObject.get("effectType").toString();
        String sourceImagePath = renderObject.get("sourceImagePath").toString();
        String destinationImagePath = renderObject.get("destinationImagePath").toString();

        sourceImage = ImageIO.read(new File(sourceImagePath));
        destinationImage = ImageIO.read(new File(destinationImagePath));

        Effect dataEffect = new Effect(sourceImage, destinationImage, effectType);

        return EffectFactory.provideEffect(dataEffect);
    }
}
