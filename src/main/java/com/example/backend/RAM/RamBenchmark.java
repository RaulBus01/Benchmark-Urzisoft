package com.example.backend.RAM;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class RamBenchmark {

    private static final int MIN_BUFFER_SIZE = 1024 * 1; // KB
    private static final int MAX_BUFFER_SIZE = 1024 * 1024 * 32; // MB

    private BufferedImage[] images;
    private BufferedImage img = null;
    private int maxImages = 50;

    private Random rand = new Random();

    private long bytes;

    private boolean canceled = false;

    public void cancel() {
        this.canceled = true;
    }

    public void run(Object... objects) {
        try {

                for (int i = 0; i < maxImages && !canceled; i++) {
                    System.out.println("Image " + i + " of " + maxImages);
                    String path = getPathForResource("/src/main/resources/com/example/frontend_urzisoft/output/" + i + ".js",i);


                    this.bytes = this.bytes + Files.size(Path.of(path));

                    images[i] = ImageIO.read(new File(path));
                }

            if (canceled) {
                for (int i = 0; i < maxImages; i++) {
                    String path = getPathForResource("/src/main/resources/com/example/frontend_urzisoft/output/" + i + ".js",i);
                    File file = Path.of(path).toFile();
                    file.delete();
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void initialize(Object... objects) {
        this.maxImages = (int) objects[0];
        this.images = new BufferedImage[maxImages];
        for (int i = 0; i < maxImages; i++)
            this.images[i] = null;
    }

    public double getResult() {
        return this.bytes/1024/1024;
    }

    public void streamWriteFixedFileSize(String filePrefix, String fileSuffix,
                                         int minIndex, int maxIndex, long fileSize, boolean clean)
            throws IOException, URISyntaxException {

        int currentBufferSize = MIN_BUFFER_SIZE;
        String fileName;
        int fileIndex = 0;

        while (currentBufferSize <= MAX_BUFFER_SIZE
                && fileIndex <= maxIndex - minIndex && !canceled) {

            fileName = getPathForResource(filePrefix + fileIndex + fileSuffix,fileIndex);
           // System.out.println("Buffer size: " + currentBufferSize + " bytes" + fileName);
            writeFile(fileName, currentBufferSize, fileSize, clean);
            // update buffer size
            currentBufferSize *= 4;
            fileIndex++;
        }
    }


    private void writeFile(String fileName, int bufferSize,
                           long fileSize, boolean clean) throws IOException {

        File file = new File(fileName);
        File folder = file.getParentFile();

        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file), bufferSize)) {
            byte[] buffer = new byte[bufferSize];
            int i = 0;
            long toWrite = fileSize / bufferSize;

            while (i < toWrite) {
                // generate random content to write
                rand.nextBytes(buffer);

                outputStream.write(buffer);
                i++;
            }
        }

    }


    private String getPathForResource(String resourcePath, int i) throws IOException, URISyntaxException {
        String jarPath = RamBenchmark.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        File jarFile = new File(jarPath);

        if (jarPath.endsWith(".jar"))
        {
            String jarDir = jarFile.getParentFile().getPath();
            return jarDir +"/" + i + ".js";
        } else {
            String projectPath = jarFile.getParentFile().getParentFile().getPath();
            return projectPath +"/" +resourcePath;
        }
    }
    public void deleteFiles(String filePrefix, String fileSuffix, int minIndex, int maxIndex)
            throws URISyntaxException, IOException {
        for (int i = minIndex; i <= maxIndex; i++) {
            String filePath = getPathForResource(filePrefix + i + fileSuffix,i);
            if (filePath != null) {
                File file = new File(filePath);
                file.delete();
            }
        }
    }


}
