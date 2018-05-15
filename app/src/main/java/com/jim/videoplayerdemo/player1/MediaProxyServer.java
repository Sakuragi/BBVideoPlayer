package com.jim.videoplayerdemo.player1;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jim on 2018/4/25 0025.
 */

public class MediaProxyServer {

    private final String TAG = MediaProxyServer.class.getSimpleName();
    private static final String PROXY_HOST = "127.0.0.1";
    private static final int STATE_INIT = 1;

    private ServerSocket mServerSocket;
    private final ExecutorService requestProcessPool = Executors.newFixedThreadPool(4);
    private int port;
    private Thread listenRequestsThread;
    private int currentServerState = -1;

    public MediaProxyServer() {
        init();
    }

    public void init() {
        try {
            InetAddress address = InetAddress.getByName(PROXY_HOST);
            mServerSocket = new ServerSocket(0, 4, address);
            port = mServerSocket.getLocalPort();
            CountDownLatch signal = new CountDownLatch(1);
            listenRequestsThread = new Thread(new ListenRequestRunnable(signal));
            listenRequestsThread.start();
            signal.await();
            Log.d(TAG,"listen request thread was started");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public String getProxyHostUrl(String url) {
        return String.format(Locale.US, "http://%s:%d/%s", PROXY_HOST, port, Util.encodeUrl(url));
    }

    private class ListenRequestRunnable implements Runnable {

        private CountDownLatch startSignal;

        public ListenRequestRunnable(CountDownLatch signal) {
            startSignal = signal;
        }

        @Override
        public void run() {
            startSignal.countDown();
            currentServerState = STATE_INIT;
            waittingForRequests();
        }
    }

    private void waittingForRequests() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Log.i(TAG, "=======waitting for client connect=======");
                Socket client = mServerSocket.accept();
                Log.i(TAG, "=======client connected=======");
                requestProcessPool.submit(new HandleRequestsRunnable(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class HandleRequestsRunnable implements Runnable {

        private Socket mClient;

        public HandleRequestsRunnable(Socket client) {
            mClient = client;
        }

        @Override
        public void run() {
            handleRequests(mClient);
        }
    }

    private void handleRequests(Socket client) {
        try {
            Request request = GetRequest(client);
            processRequest(request,client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processRequest(Request request, Socket client) {
        URLConnection connection= null;
        InputStream is=null;
        OutputStream os=null;
        try {
            connection = request.openConnection();
            is=connection.getInputStream();
            os=client.getOutputStream();
            byte[] buffer=new byte[1024*4];
            int readBytes=-1;
            while ((readBytes=is.read(buffer,0,buffer.length))!=-1){
                Log.d(TAG,"write to client: "+readBytes);
                os.write(buffer,0,readBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            Util.closeCloseableQuietly(is);
            Util.closeCloseableQuietly(os);
        }
    }

    private Request GetRequest(Socket client) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        StringBuilder request = new StringBuilder();
        String line = "";
        while (!TextUtils.isEmpty(line = reader.readLine())) {
            request.append(line).append("\n");
        }
//        Util.closeCloseableQuietly(reader);
        return new Request(request.toString());
    }

}
