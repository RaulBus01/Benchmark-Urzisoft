package com.example.backend.RAM;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class RamBenchmark{

    private static final int MIN_BUFFER_SIZE = 1024 * 1; // KB
    private static final int MAX_BUFFER_SIZE = 1024 * 1024 * 32; // MB
    private static final long MIN_FILE_SIZE = 1024 * 1024 * 1; // MB
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 1024; // MB
    private BufferedImage[] images;
    private BufferedImage img = null;
    private int maxImages = 50;

    private Random rand = new Random();

    private long bytes;

    public void run(Object... objects) {
        try {
            for(int i = 0; i < maxImages; i++) {
                String path = "C:/Users/Andre/Desktop/Irian/hello" + Integer.toString(i) + ".js";
                this.bytes = this.bytes + Files.size(Path.of(path));
                System.out.println(path);
                images[i] = ImageIO.read(new File(path));
            }
//                img = ImageIO.read(new File("C:/Users/Andre/Desktop/photos/810_9522.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(Object... objects) {
        this.maxImages = (int)objects[0];
        this.images = new BufferedImage[maxImages];
        for(int i = 0; i < maxImages; i++)
            this.images[i] = null;
    }

    public double getResult() {
        return (double)(this.bytes/1024)/1024;
    }

    public void streamWriteFixedFileSize(String filePrefix, String fileSuffix,
                                         int minIndex, int maxIndex, long fileSize, boolean clean)
            throws IOException {

        int currentBufferSize = MIN_BUFFER_SIZE;
        String fileName;
        int fileIndex = 0;

        while (currentBufferSize <= MAX_BUFFER_SIZE
                && fileIndex <= maxIndex - minIndex) {
            fileName = filePrefix + fileIndex + fileSuffix;
            writeFile(fileName, currentBufferSize, fileSize, clean);
            // update buffer size
            currentBufferSize*=4;
            fileIndex++;
        }

        String partition = filePrefix.substring(0, filePrefix.indexOf(":\\"));
    }

    private void writeFile(String fileName, int bufferSize,
                           long fileSize, boolean clean) throws IOException {

        File folderPath = new File(fileName.substring(0, fileName.lastIndexOf(File.separator)));

        // create folder path to benchmark output
        if (!folderPath.isDirectory())
            folderPath.mkdirs();

        final File file = new File(fileName);
        // create stream writer with given buffer size
        final BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file), bufferSize);

        byte[] buffer = new byte[bufferSize];
        int i = 0;
        long toWrite = fileSize / bufferSize;

        while (i < toWrite) {
            // generate random content to write
            rand.nextBytes(buffer);

            outputStream.write(buffer);
            i++;
        }

        outputStream.close();
        if(clean)
            file.delete();
//			delete file on exit ?
    }
}
