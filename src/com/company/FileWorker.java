package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FileWorker extends Thread{
    private final String printer;
    private final int timeToRead;
    private final CountDownLatch latch;
    private final String nameFile;


    public FileWorker(String printer, int timeToRead, CountDownLatch latch, String nameFile) {
        this.printer = printer;
        this.timeToRead = timeToRead;
        this.latch = latch;
        this.nameFile = nameFile;
    }

    @Override
    public void run(){
        try {
            TimeUnit.MILLISECONDS.sleep(timeToRead);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String content = null;

        try {
            content = new String(Files.readAllBytes(Paths.get(nameFile)));
            System.out.printf("%s:%s:%s\n", Thread.currentThread().getName(), printer, content);
            //Files.writeString(Path.of("repository.txt"), content, StandardCharsets.UTF_8);
            try {
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("repository.txt", true)));
                out.println(content);
                out.close();
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        latch.countDown();

    }
}
