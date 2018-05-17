package com.jim.videoplayerdemo.player1;

import android.util.Base64;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Jim on 2018/5/15 0015.
 */

public class FileCache implements Cache {

    public File file;
    private RandomAccessFile dataFile;

    private FileCache(String url){
        file=new File(cachePath+Base64.encodeToString(url.getBytes(),Base64.DEFAULT));
        if (!file.exists()){
            try {
                file.createNewFile();
                dataFile=new RandomAccessFile(file,"rw");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static FileCache open(String url){
        return new FileCache(url);
    }

    @Override
    public int read(byte[] b) throws IOException {
        return dataFile.read(b);
    }

    @Override
    public int read(byte[] b, long off, int len) throws IOException {
        dataFile.seek(off);
        return dataFile.read(b,0,len);
    }

    @Override
    public long available() throws IOException {
        return file.length();
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void append(byte[] data, int length) throws IOException {
        dataFile.seek(available());
        dataFile.write(data,0,length);
    }

    @Override
    public boolean isCompleted() {
        return false;
    }
}
