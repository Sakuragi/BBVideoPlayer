package com.jim.videoplayerdemo.player1;

import android.util.Base64;

import com.jim.videoplayerdemo.utils.LogUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Jim on 2018/5/15 0015.
 */

public class FileCache implements Cache {

    public static String TEMP=".temp";

    public File file;
    private RandomAccessFile dataFile;

    private FileCache(String url){
        file=new File(cachePath+Base64.encodeToString(url.getBytes(),Base64.DEFAULT));
        File tempFile=new File(cachePath+Base64.encodeToString(url.getBytes(),Base64.DEFAULT)+TEMP);
        if (!file.exists()&&tempFile.exists()){
           file=tempFile;
        }else if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        createDataFile();
    }

    private void createDataFile(){
        try {
            dataFile=new RandomAccessFile(file,"rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
        return dataFile.length();
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
        return !isTempFile(file);
    }

    @Override
    public void finishedCache() {
        if (!isCompleted()){
            LogUtil.d("is tempfile");
            return;
        }
        boolean renamed=file.renameTo(new File(file.getName().substring(file.getName().length()-TEMP.length())));
        if (!renamed){
            LogUtil.d("rename temp file failed");
        }
        try {
            dataFile=new RandomAccessFile(file,"r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean isTempFile(File file) {
        return file.getName().endsWith(TEMP);
    }
}
