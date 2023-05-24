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
    private static final long MAX_FILE_SIZE = 4L*1024 * 1024 * 1024; // MB
    private BufferedImage[] images;
    private BufferedImage img = null;
    private int maxImages = 50;

    private Random rand = new Random();

    private long bytes;

    private boolean canceled = false;
    public void cancel()
    {
        this.canceled = true;
    }
    public void run(Object... objects) {
        try {
            for(int i = 0; i < maxImages && !canceled; i++)
            {
                System.out.println("Image " + i + " of " + maxImages);
                String path = "src/main/resources/com/example/frontend_urzisoft/output/" + Integer.toString(i) + ".js";
                this.bytes = this.bytes + Files.size(Path.of(path));
                images[i] = ImageIO.read(new File(path));
                File file = Path.of(path).toFile();


            }

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
                && fileIndex <= maxIndex - minIndex && !canceled) {
            System.out.println("Buffer size: " + currentBufferSize + " bytes");
            fileName = filePrefix + fileIndex + fileSuffix;

            writeFile(fileName, currentBufferSize, fileSize, clean);
            // update buffer size

            currentBufferSize*=4;
            fileIndex++;
        }


    }

    private void writeFile(String fileName, int bufferSize,
                           long fileSize, boolean clean) throws IOException {

        String folderPath;
        int separatorIndex = fileName.lastIndexOf(File.separator);
        if (separatorIndex >= 0) {
            folderPath = fileName.substring(0, separatorIndex);
        } else {
            folderPath = "output"; // or provide a default folder path if needed
        }

        File folder = new File(folderPath);




        final File file = new File(fileName);
        if (file.exists())


        if (!folder.exists()) {
            folder.mkdirs();

        }

        // create stream writer with given buffer size
        final BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file), bufferSize);
        //System.out.println("Writing file: " + fileName + " with buffer size: " + bufferSize + " bytes");
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

//           if(clean)
//           file.delete();

    }

}
