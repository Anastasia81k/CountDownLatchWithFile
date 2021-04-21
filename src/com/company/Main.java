package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Main {

    public static void main(String[] args) throws IOException {
        final int timeToRead = 1000;
        final int countPrinter =3;
        CountDownLatch latch = new CountDownLatch(countPrinter);
        ArrayList<FileWorker> fileWorkers = new ArrayList<>(){
            {
                add(new FileWorker("printer1", timeToRead, latch,"sample.txt"));
                add(new FileWorker("printer2", timeToRead*2, latch,"sample1.txt"));
                add(new FileWorker("printer3", timeToRead*5, latch,"sample2.txt"));

            }
        };
        fileWorkers.forEach(Thread::start);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("work is done!!!");
    }
}
