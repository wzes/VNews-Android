package com.mobile.vnews.util;

import android.content.Context;
import android.content.res.AssetManager;

import net.jpountz.lz4.LZ4BlockInputStream;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileUtils {

    /**
     *
     * @param inputStream
     * @return
     */
    public static int getTotalSize(InputStream inputStream) {
        DataInputStream dataInputStream = null;
        int size = 0;
        try {
            dataInputStream = new DataInputStream(inputStream);
            size = dataInputStream.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] lz4Decompress(byte[] data) throws IOException {
        LZ4Factory factory = LZ4Factory.fastestInstance();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(8192);
        LZ4FastDecompressor decompresser = factory.fastDecompressor();
        LZ4BlockInputStream lzis = new LZ4BlockInputStream(new ByteArrayInputStream(data), decompresser);
        int count;
        byte[] buffer = new byte[8192];
        while ((count = lzis.read(buffer)) != -1) {
            baos.write(buffer, 0, count);
        }
        lzis.close();
        return baos.toByteArray();
    }

    /**
     *
     * @param filename
     * @param type
     * @return
     */
    public static BlockingQueue<Integer> getQueueFromFile(String filename, String type) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(8);
        BufferedReader writeReader = null;
        try {
            writeReader = new BufferedReader(
                    new FileReader(filename));
            String line;
            if (type.equals("write")) {
                writeReader.readLine();
            } else if (type.equals("read")) { }
            else {
                throw new IllegalArgumentException();
            }
            while ((line = writeReader.readLine()) != null) {
                Integer size = Integer.parseInt(line);
                queue.add(size);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return queue;
    }

    /**
     *
     * @param inputStream
     * @param type
     * @return
     */
    public static BlockingQueue<Integer> getQueueFromFile(InputStream inputStream, String type) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(8);
        DataInputStream dataInputStream = null;
        try {
            dataInputStream = new DataInputStream(inputStream);
            String line;
            if (type.equals("write")) {
                dataInputStream.readInt();
            } else if (type.equals("read")) { }
            else {
                throw new IllegalArgumentException();
            }
            int number = dataInputStream.available() / 4;
            for (int i = 0; i < number; i++) {
                queue.add(dataInputStream.readInt());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return queue;
    }

    /**
     *
     * @param source
     * @param des
     * @param totalSize
     * @param readQueue
     * @param writeQueue
     */
    public static void writeToFileBySlice(Context context, String source, String des, int totalSize, BlockingQueue<Integer> readQueue,
                                          BlockingQueue<Integer> writeQueue) {
        final int threadNumber = 1;
        int writeOff = 0;
        int number = readQueue.size();

        File file = new File(des);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ExecutorService executorService = Executors.newFixedThreadPool(threadNumber);
        // calculate time
        for(int index = 0; index < number; index++) {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = null;
            BufferedRandomAccessFile writeFile = null;
            try {
                inputStream = assetManager.open(source + index + ".lz4");
                writeFile = new BufferedRandomAccessFile(des, "rw", 10);
                writeFile.setLength(totalSize);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Integer readSize = readQueue.take();
                Integer writeSize = writeQueue.take();

                ReadSliceThread readSliceThread = new ReadSliceThread(inputStream, writeFile, readSize, writeOff);
                executorService.execute(readSliceThread);
                //readOff += readSize;
                writeOff += writeSize;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }

    /**
     * read slice thread
     */
    static class ReadSliceThread extends Thread {
        BufferedRandomAccessFile writeFile;
        InputStream readFile;
        int readOff, readSize;
        int writeOff;

//        final int READ_SIZE = 1024 * 128;

        ReadSliceThread(InputStream inputStream,  BufferedRandomAccessFile writeFile, int readSize, int writeOff) {
            this.readFile = inputStream;
            this.writeFile = writeFile;
            this.readSize = readSize;
            this.writeOff = writeOff;
        }

        @Override
        public void run() {
            try {
                // read data
                writeFile.seek(writeOff);
                // read data according to the file size
                // write to file
                byte[] bytes = new byte[readSize];
                readFile.read(bytes);
                byte[] uncompress = lz4Decompress(bytes);
                writeFile.write(uncompress, 0, uncompress.length);
                readFile.close();
                writeFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            super.run();
        }
    }
}
