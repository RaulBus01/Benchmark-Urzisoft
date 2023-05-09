package CPU;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LZWEncoder {

    private static final int MAX_DICT_SIZE = 4096;
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    public static void compress(File input, File output) throws IOException, InterruptedException {
        // Create input and output streams
        FileInputStream inputStream = new FileInputStream(input);
        DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(output)));

        // Create thread pool
        ExecutorService threadPool = Executors.newFixedThreadPool(NUM_THREADS);

        // Create dictionary with initial 256 ASCII codes
        int dictSize = 256;
        byte[] dict = new byte[MAX_DICT_SIZE];
        for (int i = 0; i < 256; i++) {
            dict[i] = (byte) i;
        }

        // Process input data in chunks using multiple threads
        int chunkSize = (int) Math.ceil((double) input.length() / NUM_THREADS);
        for (int i = 0; i < NUM_THREADS; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, (int) input.length());
            threadPool.execute(new CompressWorker(inputStream, outputStream, dict, dictSize, start, end));
        }

        // Shutdown thread pool and wait for all threads to complete
        threadPool.shutdown();
        threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        // Close input and output streams
        inputStream.close();
        outputStream.close();
    }

    private static class CompressWorker implements Runnable {
        private final InputStream inputStream;
        private final DataOutputStream outputStream;
        private final byte[] dict;
        private final int dictSize;
        private final int start;
        private final int end;

        public CompressWorker(InputStream inputStream, DataOutputStream outputStream, byte[] dict, int dictSize, int start, int end) {
            this.inputStream = inputStream;
            this.outputStream = outputStream;
            this.dict = dict.clone();
            this.dictSize = dictSize;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            try {
                // Initialize dictionary for this thread
                int threadDictSize = dictSize;
                byte[] threadDict = dict.clone();

                // Read input data for this thread
                byte[] input = new byte[end - start];
                inputStream.skip(start);
                inputStream.read(input);

                // Compress input data using LZW algorithm
                int code = 0;
                StringBuilder buffer = new StringBuilder();
                for (byte b : input) {
                    buffer.append((char) (b & 0xff));
                    if (!contains(threadDict, buffer.toString())) {
                        // Output code for current sequence
                        writeCode(outputStream, code, threadDictSize);
                        // Add current sequence to dictionary
                        if (threadDictSize < MAX_DICT_SIZE) {
                            threadDict[threadDictSize++] = (byte) buffer.charAt(buffer.length() - 1);
                        }
                        // Start new sequence with current character
                        buffer.setLength(1);
                        code = buffer.charAt(0);
                    } else {
                        code = getCode(threadDict, buffer.toString());
                    }
                }
                // Output final code for last sequence
                writeCode(outputStream, code, threadDictSize);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private boolean contains(byte[] dict, String sequence) {
            for (int i = 0; i < dictSize; i++) {
                if (sequence.equals(new String(dict, 0, i + 1))) {
                    return true;
                }
            }
            return false;
        }

        private int getCode(byte[] dict, String sequence) {
            for (int i = 0; i < dictSize; i++) {
                if (sequence.equals(new String(dict, 0, i + 1))) {
                    return i;
                }
            }
            return -1;
        }

        private void writeCode(DataOutputStream outputStream, int code, int dictSize) throws IOException {
            int bits = (int) Math.ceil(Math.log(dictSize) / Math.log(2));
            outputStream.writeShort(code << (16 - bits));
        }
    }
}