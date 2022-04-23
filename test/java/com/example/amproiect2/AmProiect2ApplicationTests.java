package com.example.amproiect2;

import com.amazonaws.services.s3.AmazonS3;
import com.example.amproiect2.datasource.local.MediaFileDao;
import com.example.amproiect2.entities.MediaFile;
import com.example.amproiect2.config.BucketName;
import com.example.amproiect2.datasource.storage.AmazonFileStore;
import com.example.amproiect2.datasource.local.MediaFileRepository;
import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Rational;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AmProiect2ApplicationTests {

    public static String IMAGE1_FiLE = "C:\\Users\\danit\\OneDrive\\Pictures\\Video Projects\\10.png";
    public static String IMAGE2_FiLE = "C:\\Users\\danit\\OneDrive\\Pictures\\Video Projects\\12.png";


    @Autowired
    private AmazonS3 s3;

    @Autowired
    private AmazonFileStore fileStore;

    @Autowired
    private MediaFileDao mediaFileDao;

    @Autowired
    MediaFileRepository mediaFileRepository;

    @Test
    void contextLoads() {
        assertThat(s3).isNotNull();
    }

    @Test
    void couldDownloadFileFromS3() {
        //given
        String imageKey = "user1/10.png";
        //when
        byte[] imageDownloaded = fileStore.download(BucketName.PROFILE_IMAGE.getBucketName(), imageKey);
        //then
        assertThat(imageDownloaded).isNotNull();
    }

    @Test
    void couldDownloadListFilesFromS3() {
        //given
        List<String> filesKey = List.of(
                "user1/10.png",
                "user1/12.png",
                "user1/cat.jpg",
                "user1/ClaseAbtracte_Interfete.jpg",
                "user1/java_datastructures.jpg",
                "user1/WhatsApp Image 2021-10-16 at 16.09.48.jpeg"
        );

        List<byte[]> expected = new ArrayList<>();

        for (String key : filesKey) {
            byte[] imageDownloaded = fileStore.download(BucketName.PROFILE_IMAGE.getBucketName(), key);
            expected.add(imageDownloaded);
        }

        assertThat(expected.size()).isEqualTo(filesKey.size());
    }

    @Test
    void couldMapForInsertIntoLocalDatabase() {
        //given
        List<String> filesKey = List.of(
                "user1/10.png",
                "user1/12.png",
                "user1/cat.jpg",
                "user1/ClaseAbtracte_Interfete.jpg",
                "user1/java_datastructures.jpg",
                "user1/WhatsApp Image 2021-10-16 at 16.09.48.jpeg",
                "user1/Ubi.png"
        );
        //when
        List<MediaFile> mappedList = filesKey.stream()
                .map((key) -> new MediaFile(key, key.split("/")[1], "user1"))
                .collect(Collectors.toList());

        //then
        assertThat(mappedList.size()).isEqualTo(filesKey.size());
    }

    @Test
    void certainQueryIsWorking() {
        //given
        int entitiesCount = mediaFileRepository.getEntitiesCount();

        //then
        assertThat(entitiesCount).isGreaterThan(0);
    }

    @Test
    void couldFetchAllKeysDownloadAllImagesFromAws() {
        //given
        List<MediaFile> imagesKeys = mediaFileDao.fetchImagesAwsKeys();

        List<byte[]> byteArr = fileStore.downloadAllFiles(BucketName.PROFILE_IMAGE.getBucketName(), imagesKeys);

        //then
        assertThat(byteArr.size()).isEqualTo(3);
    }

    @Test
    void couldLoadVideoFile() throws Exception {
        File file = new File("C:\\Users\\danit\\OneDrive\\Documents\\Bandicam\\bd_proiect.mp4");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FileInputStream fileInputStream = new FileInputStream(file);
        byteArrayOutputStream.write(fileInputStream.readAllBytes());
        byte[] underTest = byteArrayOutputStream.toByteArray();

        assertThat(underTest).isNotNull();
    }

    @Test
    void couldRenderWithMoreThreads() throws Exception {
        SeekableByteChannel out = NIOUtils.writableFileChannel("output.mp4");
        AWTSequenceEncoder encoder = new AWTSequenceEncoder(out, Rational.R(25, 1));
        CyclicBarrier barrier = new CyclicBarrier(2);

        List<BufferedImage> bufferedImages = new ArrayList<>();
        bufferedImages.add(ImageIO.read(new File(IMAGE1_FiLE)));
        bufferedImages.add(ImageIO.read(new File(IMAGE2_FiLE)));

        bufferedImages.add(ImageIO.read(new File(IMAGE2_FiLE)));
        bufferedImages.add(ImageIO.read(new File(IMAGE1_FiLE)));

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 2; i++) {
                    try {
                        encoder.encodeImage(bufferedImages.get(i));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 2; i < 4; i++) {
                    try {
                        encoder.encodeImage(bufferedImages.get(i));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        encoder.finish();
    }
}
