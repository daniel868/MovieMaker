package com.example.amproiect2.datastore;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class FakeMediaDataStore {
    private static List<byte[]> images = new ArrayList<>();
    private static List<byte[]> audio = new ArrayList<>();

    static {
        String filePath1 = "C:\\Users\\danit\\OneDrive\\Pictures\\Video Projects\\12.png";
        String filePath2 = "C:\\Users\\danit\\OneDrive\\Pictures\\Video Projects\\10.png";

        String audioFilePath1 = "C:\\Users\\danit\\Music\\Samples\\eminem-30s.wav";
        String audioFilePath2 = "C:\\Users\\danit\\Music\\Samples\\Queen_Bohemian_Rhapsody.mp3";

        try {
            images.add(parseImageFile(filePath1));
            images.add(parseImageFile(filePath2));

            audio.add(parseAudioFile(audioFilePath1));
            audio.add(parseAudioFile(audioFilePath2));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] parseImageFile(String filePath) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedImage bufferedImage = ImageIO.read(new File(filePath));
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private static byte[] parseAudioFile(String filePath) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(fileInputStream);

        return byteArrayOutputStream.toByteArray();
    }

    public List<byte[]> getImages() {
        return images;
    }

    public List<byte[]>getAudio() {
        return audio;
    }
}
