package com.example.backend.RAM;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RamBenchmark {
    private BufferedImage[] images;
    private BufferedImage img = null;
    private int maxImages = 50;
    private long bytes;


    public void run(Object... objects) {
        try {
            for(int i = 0; i < maxImages; i++) {
                String path = "src/main/resources/com/example/frontend_urzisoft/ram_resources/810_9" + Integer.toString(522 + i) + ".jpg";
                this.bytes = this.bytes + Files.size(Path.of(path));
//                System.out.println(path);
                images[i] = ImageIO.read(new File(path));
            }
//                img = ImageIO.read(new File("C:/Users/Andre/Desktop/photos/810_9522.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(Object... objects) {
        this.images = new BufferedImage[maxImages];
        for(int i = 0; i < maxImages; i++)
            this.images[i] = null;
    }

    public double getResult() {
        return (double)(this.bytes/1024)/1024;
    }
}
